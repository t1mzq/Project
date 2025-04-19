package Entity;

import Obstacles.Bullet;
import main.GamePanel;
import main.KeyHandler;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH=keyH;

        setDefaultValues();
    }
    public void setDefaultValues(){
        x=100;
        y=500;
        speed=4;
    }
    public void update(){

        if(keyH.upPressed == true){
            y -= speed;
        }
        else if (keyH.downPressed==true){
            y += speed;
        }
        else if(keyH.leftPressed==true){
            x -= speed;
        }
        else if(keyH.rightPressed==true){
            x += speed;
        }

    }
    public void draw(Graphics2D g2){

        g2.setColor(Color.white);
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);

    }

    public boolean hasCollision(Bullet bullet) {
        int bulletX = bullet.x;
        int bulletY = bullet.y;
        int bulletWidth = gp.tileSize;
        int bulletHeight = gp.tileSize;
        int playerX = x;
        int playerY = y;
        int playerWidth = gp.tileSize;
        int playerHeight = gp.tileSize;

        return bulletX < playerX + playerWidth && bulletX + bulletWidth > playerX && bulletY < playerY + playerHeight && bulletY + bulletHeight > playerY;
    }
}
