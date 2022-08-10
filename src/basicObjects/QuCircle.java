package basicObjects;
import java.awt.*;
import java.awt.geom.*;
public class QuCircle extends QuObject {
    public double r;

    public QuCircle(double x, double y, double rad, double we, Color colr){
        super(x, y, colr);
        this.r = rad;
        
        this.speedX = 0;
        this.speedY = 0;

        this.forceX = 0;
        this.forceY = 0;

        this.weight = we;
    }

    public boolean isColliding(QuCircle temp){
        return (magCalm2(this.posX - temp.potenX, this.posY - temp.potenY) <= (temp.r + this.r) * (temp.r + this.r)); 
    }
    public void draw(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape ballShape = new Ellipse2D.Double(this.posX - this.r, this.posY - this.r, this.r * 2, this.r * 2);
        g2d.draw(ballShape);
        g2d.fill(ballShape);
    }

    public void drawNoFill(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape ballShape = new Ellipse2D.Double(this.posX - this.r, this.posY - this.r, this.r * 2, this.r * 2);
        g2d.draw(ballShape);
    }

    public void drawYesFill(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape ballShape = new Ellipse2D.Double(this.posX - this.r, this.posY - this.r, this.r * 2, this.r * 2);
        g2d.draw(ballShape);
        g2d.fill(ballShape);
    }

    public double getR() {
        return this.r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
