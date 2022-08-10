package basicObjects;

import java.awt.*;
import java.awt.geom.*;

import mainStuff.*;
import miscStuff.*;
public class LineObject extends QuObject{
    public double desPosX;
    public double desPosY;

    public double midX;
    public double midY;
    
    public double angl;

    public static double rotSpeed = 6;
    public static double moveSpeed = 8;

    public double lineLength;
    public int noCollisionFrame = 0;

    public LineObject(double x, double y, double dx, double dy, Color colr){
       super(x, y, colr);

        this.desPosX = dx;
        this.desPosY = dy;

        this.lineLength = magCal((x - dx), (y - dy));
        midX = (x + dx) / 2;
        midY = (y + dy) / 2;

        this.angl = -Math.toDegrees(Math.asin((y - midY) / this.lineLength * 2));
    }

    public void createParticle(MainGameComponent mg){
        double size = Math.random() * 5;
        Particle e = new Particle(posX + size, posY + size, size * size, size * size, this.color, 30);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }

    public void move(double x, double y){
        this.posX += x * moveSpeed;
        this.desPosX += x * moveSpeed;

        this.posY += y * moveSpeed;
        this.desPosY += y * moveSpeed;

        midX = (this.posX + this.desPosX) / 2;
        midY = (this.posY + this.desPosY) / 2;
        
    }

    public void rotate(int chieuQuay){

        this.angl += (double)chieuQuay * rotSpeed;
        if (this.angl > 135) this.angl = 135;
        if (this.angl < 45) this.angl = 45;

        double truX = Math.cos(Math.toRadians(this.angl)) * this.lineLength / 2;
        double truY = Math.sin(Math.toRadians(this.angl)) * this.lineLength / 2;
        //double truY = reverseMagCal(this.lineLength / 2, truX);

        this.posX = midX + truX; 
        this.posY = midY + truY;

        this.desPosX = midX - truX;
        this.desPosY = midY - truY;
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape lineShape = new Line2D.Double(this.posX, this.posY, this.desPosX, this.desPosY);
        g2d.draw(lineShape);
    }

}
