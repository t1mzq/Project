package main;

import Entity.Player;
import Obstacles.Bullet;
import Obstacles.fallingBullets;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 32; //sprite size
    final int scale = 2;
    public final int tileSize = originalTileSize*scale;

    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = tileSize*maxScreenCol;
    final int screenHeight=tileSize*maxScreenRow;

    int FPS = 12;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    Bullet bullet = new Bullet(this, screenWidth, screenHeight);
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

            if (player.hasCollision(bullet)){
                gameOver = true;
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
            bullet.update();
            fb.update();
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);
        bullet.draw(g2);
        fb.draw(g2);

        if (gameOver) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g2.getFont());
            g2.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over")) / 2, screenHeight / 2);
        }

        g2.dispose();

    }
}
