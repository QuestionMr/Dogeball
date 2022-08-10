package powerups;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.ImageIcon;

import basicObjects.Ball;
import basicObjects.QuCircle;
import miscStuff.CustomString;
import thingsInGame.Player;

public class Powerup extends QuCircle {
    public double powerupDuration = 0;
    public double powerupCurrentDuration = -2;
    public CustomString pwMessage; 
    Font font = new Font("Arial", Font.BOLD, 20);
    private static double scale = 0.20;

    public Powerup(double x, double y, double rad, Color colr, double pwd, String scr1) {

        super(x, y, rad, 0, colr);
        this.posX = x;
        this.posY = y;

        this.r = rad;
        this.color = colr;

        this.powerupDuration = pwd;
        this.sprite = new ImageIcon(scr1).getImage();
        //pwMessage = new CustomString(pwEf, 0, 0, colr);

    }

    public void pwCollDetect(Ball temp, Player temp2) {
        if (isColliding(temp) && powerupCurrentDuration == -1) {
            powerupCurrentDuration = powerupDuration;
            powerupEffect(temp2);
        }
    }
    public void continuousEffect(Player temp2){

    }
    public void pwHandling(Ball temp, Player temp2) {
        pwCollDetect(temp, temp2);
        if (powerupCurrentDuration > 0){
            powerupCurrentDuration--;
        }
        else if (powerupCurrentDuration == 0) {
            powerupReset(temp2);
            powerupCurrentDuration = -2;
        }
    }

    public void powerupEffect(Player temp) {
    }

    public void powerupReset(Player temp) {
    }

    public void drawSprite(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform tr = new AffineTransform();
        tr.translate(posX - r, posY - r);
        tr.scale(scale, scale);
        g2d.drawImage(sprite, tr, null);

    }

    public void draw(Graphics g) {
        if (powerupCurrentDuration == -1) {
            //drawYesFill(g);
            drawSprite(g);
        }
        // if(powerupCurrentDuration > 0) {
        //     pwMessage.draw(g);
        // }

    }

}
