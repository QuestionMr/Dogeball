package miscStuff;

import java.awt.*;

import basicObjects.QuObject;
public class CustomString extends QuObject{
    String inStr = "";

    public CustomString(String inp, double x, double y, Color colr){
        super(x, y, colr);
        inStr = inp;
    }

    public void setString(int g){
        inStr = "" + g;
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(inStr, (int)this.posX, (int)this.posY);
    }
}
