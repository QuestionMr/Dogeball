package powerups;

import java.awt.*;
import thingsInGame.Player;


public class Speedup extends Powerup{
    private double speedBoost = 1.5;
    public Speedup(double x, double y, double rad, Color colr, double pwd, String scr1) {
        super(x, y, rad, colr, pwd, scr1); 
    }

    public void powerupEffect(Player temp){
        temp.setMovementSpeed(temp.getMovementSpeed() * speedBoost);
    }

    public void powerupReset(Player temp){
        temp.setMovementSpeed(temp.getMovementSpeed() / speedBoost);
    }

}
