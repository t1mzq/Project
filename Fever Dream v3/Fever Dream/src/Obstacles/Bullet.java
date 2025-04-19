package Obstacles;


import Entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entity {


    public final GamePanel gp;
    public final int screenWidth;
    public final int screenHeight;

    public Bullet(GamePanel gp, int screenWidth, int screenHeight){
        super(gp);

        this.gp = gp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        speed = 4;
        setDefaultValues();
    }

    public void setDefaultValues(){
        y=0;
    }

    public void update(){
        y += speed;

        if (y > screenHeight){
            setDefaultValues();
        }
    }
    public void draw(Graphics2D g2){

        g2.setColor(Color.red);
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);

    }
    public Bullet(GamePanel gp, int screenWidth, int screenHeight, int x, int y) {
        super(gp);
        this.gp = gp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = x;
        this.y = y;
        speed = 4;
    }

}
