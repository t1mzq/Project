package Obstacles;

import main.GamePanel;

import java.awt.*;

public class fallingBullets extends Bullet{
    public fallingBullets(GamePanel gp, int screenWidth, int screenHeight) {
        super(gp, screenWidth, screenHeight);
    }
    @Override
    public void setDefaultValues(){
        x=500;
        y=0;
    }

}
