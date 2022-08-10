package miscStuff;
import java.awt.*;

import basicObjects.Rec;

public class Particle extends Rec{
    private double lifeTime;
    public Particle(double x, double y, double w, double h, Color colr, double lt){
        super(x, y, w, h, colr);
        this.lifeTime = lt;
    };

    public boolean isAlive(){
        return (lifeTime > 0 && this.width > 0);
    }
    public void updateParticle(){
        this.lifeTime--;
        this.width-= 0.5;
        this.height-= 0.5;
        this.move(speedX, speedY);
    }

}
