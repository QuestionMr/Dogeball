package powerups;

import java.awt.*;

import basicObjects.Ball;
import thingsInGame.Player;
import thingsInGame.RepelField;

public class Repel extends Powerup {
    private RepelField rep = new RepelField(0, 0, 140, Color.WHITE, 2);
    public Repel(double x, double y, double rad, Color colr, double pwd, String scr1) {
        super(x, y, rad, colr, pwd, scr1);
    }

    public void powerupEffect(Player temp) {
        // repelIsOn = 1;
    }

    public void pwHandling(Ball temp, Player temp2) {
        pwCollDetect(temp, temp2);
        if (powerupCurrentDuration > 0) {
            powerupCurrentDuration--;
            rep.setPosX(temp2.getCollBoxX());
            rep.setPosY(temp2.getCollBoxY());
            rep.repel(temp);

        } else if (powerupCurrentDuration == 0) {
            powerupReset(temp2);
            powerupCurrentDuration = -2;
        }
    }

    public void powerupReset(Player temp) {
    }

    public void draw(Graphics g) {
        if (powerupCurrentDuration == -1) {
            //this.drawYesFill(g);
            this.drawSprite(g);
        }  
        if(powerupCurrentDuration > 0) {
            rep.drawNoFill(g);
        }

    }
}