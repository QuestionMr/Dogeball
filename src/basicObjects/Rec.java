package basicObjects;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.ImageIcon;

public class Rec extends QuObject{
    public double width;
    public double height;

    public Rec(double x, double y, double w, double h, Color colr){
        super(x, y, colr);
        this.width = w;
        this.height = h;
    }

    public Rec(double x, double y, double w, double h, Color colr, String scr1){
        super(x, y, colr);
        this.width = w;
        this.height = h;
        this.sprite = new ImageIcon(scr1).getImage();

    }

    public void draw(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape recShape = new Rectangle2D.Double(this.posX, this.posY, this.width, this.height);
        g2d.draw(recShape);
        g2d.fill(recShape);
    }

    public void drawWithImages(Graphics g){
        draw(g);
        drawSprite(g, sprite, 0, 0, 1, 0, 0);
    }

    public void collDetect(LineObject temp){
        double midX = posX + width / 2;
        double midY = posY + height / 2;

        double clX = Math.abs(returnClamp(midX, temp.getPosX(), temp.desPosX) - midX);
        double clY = Math.abs(returnClamp(midY, temp.getPosX(), temp.desPosY) - midY);

        if (clX < width / 2) posX -= (width / 2 - clX) * speedX;
        if (clY < height / 2) posX -= (height / 2 - clX) * speedY;
    }
}
