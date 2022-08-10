package miscStuff;
import java.awt.*;
import java.awt.geom.*;

import basicObjects.LineObject;
import basicObjects.QuCircle;
import basicObjects.Rec;
public class Background extends Rec{
    private final QuCircle midCirc;
    private final LineObject midLine;
    public Background(double x, double y, double w, double h, Color colr){
        super(x, y, w, h, colr);
        this.posX = x;
        this.posY = y;

        this.width = w;
        this.height = h;

        this.color = colr;

        midCirc = new QuCircle(x + w / 2, y + h / 2, h / 4, 1, Color.YELLOW);
        midLine = new LineObject(x + w / 2, y, x + w / 2, y + h,  Color.YELLOW);
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape recShape = new Rectangle2D.Double(this.posX, this.posY, this.width, this.height);
        g2d.draw(recShape);
        midCirc.drawNoFill(g2d);
        midLine.draw(g2d);
        
    }
    
}
