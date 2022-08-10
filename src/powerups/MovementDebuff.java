package powerups;

import java.awt.*;

import thingsInGame.Player;

public class MovementDebuff extends Powerup{
    public MovementDebuff(double x, double y, double rad, Color colr, double pwd, String scr1) {
        super(x, y, rad, colr, pwd, scr1); 
    }

    public void powerupEffect(Player temp){
        temp.setMovementSpeed(temp.getMovementSpeed() * -1);
    }

    public void powerupReset(Player temp){
        temp.setMovementSpeed(temp.getMovementSpeed() * -1);
    }

}
