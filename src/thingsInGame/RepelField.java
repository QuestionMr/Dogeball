package thingsInGame;
import java.awt.*;

import basicObjects.Ball;
import basicObjects.QuCircle;
public class RepelField extends QuCircle{
    double repelForce;
    public RepelField(double x, double y, double rad, Color colr, double rp){
        super(x, y, rad, 0, colr);
        this.posX = x;
        this.posY = y;

        this.r = rad;
        this.color = colr;
        this.repelForce = rp;
    }

    public void repel(Ball temp){
        double diffX = temp.potenX - posX;
        double diffY = temp.potenY - posY;
        double dis = magCalm2(diffX, diffY);
        if (dis > magCalm2(this.r, temp.r)) return;
        //double proptionalForce = Math.max(magCalm2(temp.speedX, temp.speedY) * repelForce, temp.defaultSpeed);
        double lForce = magCalm2(temp.getSpeedX(), temp.getSpeedY());
        double constForce = Math.sqrt(lForce / dis) * 0.3;
        double repX = constForce * diffX;
        double repY = constForce * diffY;
        // double repX = 0.01 * diffX;
        // double repY = 0.01 * diffY;

        temp.addSpeed(repX, repY);
    }
    
}
