package main;

import Entity.Player;
import Obstacles.Bullet;
import Obstacles.fallingBullets;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 32; //sprite size
    final int scale = 2;
    public final int tileSize = originalTileSize*scale;

    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = tileSize*maxScreenCol;
    final int screenHeight=tileSize*maxScreenRow;

    int spawnDelay = 70; // number of frames to wait
    int spawnTimer = 0;  // will increment every frame
    boolean secondBulletSpawned = false;

    int FPS = 50;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    ArrayList<Bullet> bullets = new ArrayList<>();
    fallingBullets fb = new fallingBullets(this, screenWidth, screenHeight);


    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    boolean gameOver = false;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        for (int i = 0; i < 5; i++) {
            bullets.add(new Bullet(this, screenWidth, screenHeight));
        }

        // ✅ Initialize bullets list ONCE
        bullets = new ArrayList<>();

        // Add your original set of bullets
        bullets.add(new Bullet(this, screenWidth, screenHeight, 300, 0));
        bullets.add(new Bullet(this, screenWidth, screenHeight, 600, 0));
        bullets.add(new Bullet(this, screenWidth, screenHeight, 900, 0));

        // ✅ Spawn first bullet at fixed X for delayed spawn
        int spawnX = 200;
        Bullet first = new Bullet(this, screenWidth, screenHeight);
        first.x = spawnX;
        bullets.add(first);
    }

    public void starGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){

            long currentTime = System.nanoTime();

            update();

            for (Bullet b : bullets) {
                if (player.hasCollision(b)) {
                    gameOver = true;
                    break;
                }
            }


            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if (!gameOver) {
            player.update();
            for (Bullet b : bullets) {
                b.update();
            }
            fb.update();
        }
        if (!secondBulletSpawned) {
            spawnTimer++;

            if (spawnTimer >= spawnDelay) {
                Bullet staggered = new Bullet(this, screenWidth, screenHeight);
                staggered.x = 200; // same X as the first
                bullets.add(staggered);
                secondBulletSpawned = true;
            }
        }


    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);
        for (Bullet b : bullets) {
            b.draw(g2);
        }

        if (gameOver) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g2.getFont());
            g2.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over")) / 2, screenHeight / 2);
        }

        g2.dispose();

    }
    class delayBullets {
        int x;
        int delay;
        int timer = 0;
        boolean spawned = false;

        public delayBullets(int x, int delay) {
            this.x = x;
            this.delay = delay;
        }

        public boolean readyToSpawn() {
            timer++;
            return !spawned && timer >= delay;
        }
    }

}
