package powerups;
import java.awt.*;

import thingsInGame.Player;

public class Bigbat extends Powerup{
    public Bigbat(double x, double y, double rad, Color colr, double pwd, String scr1) {
        super(x, y, rad, colr, pwd, scr1); 
    }

    public void powerupEffect(Player temp){
        temp.setHitRad(temp.getHitRad() * 2);
        temp.setBatScale(temp.getBatScale() * 2);
    }

    public void powerupReset(Player temp){
        temp.setHitRad(temp.getHitRad() / 2);
        temp.setBatScale(temp.getBatScale() / 2);
    }
}
