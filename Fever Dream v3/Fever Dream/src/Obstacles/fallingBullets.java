package Obstacles;

import main.GamePanel;

import java.awt.*;

public class fallingBullets extends Bullet{
    public fallingBullets(GamePanel gp, int screenWidth, int screenHeight) {
        super(gp, screenWidth, screenHeight);
    }
    @Override
    public void setDefaultValues(){
        y=0;
    }

}
