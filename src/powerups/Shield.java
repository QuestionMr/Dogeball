package powerups;

import java.awt.Color;

import basicObjects.LineObject;
import mainStuff.MainGameComponent;
import thingsInGame.Player;

public class Shield extends Powerup{
    LineObject shieldLine;
    public Shield(double x, double y, double rad, Color colr, double pwd, String scr1) {
        super(x, y, rad, colr, pwd, scr1); 
    }

    public void powerupEffect(Player temp){
        MainGameComponent mg = temp.getGameComponent();
        shieldLine = new LineObject(1050 - 500 * (temp.forceCalPoint + 1), 265, 1050 - 500 * (temp.forceCalPoint + 1), 415, Color.CYAN);
        for (int i = 0; i < 5; i++)temp.createParticle(mg, Color.CYAN);
        mg.lineList.add(shieldLine);
    }

    public void powerupReset(Player temp){
        MainGameComponent mg = temp.getGameComponent();
        for (int i = 0; i < 4; i++)temp.createParticle(mg, Color.CYAN);
        mg.lineList.remove(mg.lineList.size() - 1);
    }
}
