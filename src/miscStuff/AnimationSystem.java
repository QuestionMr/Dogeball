package miscStuff;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.ImageIcon;
import basicObjects.QuObject;

public class AnimationSystem extends QuObject{
    List<Image>  frame = new ArrayList<>();
    Image firstFrame;
    private boolean isVisible = false;
    private boolean isFade= false;

    private int animSpeed = 1;
    private int currStep = 0;
    private double scale;
    private int totalFrames;

    private int currFrame;

    private QuObject host = null;
    public void setAnimationSpeed(int animSp){
        animSpeed = animSp;
    }

    public void setFade(){
        isFade = true;
    }
    public AnimationSystem(double x, double y, int TotalFrames, int siz, String animSource, double scaling){
        super(x, y, null);
        for (int i = 0; i < siz; i++){
            String sc = "assets/anim/" + animSource + ("" + i) + ".png";
            //System.out.println(sc);
            Image imgPush = new ImageIcon(sc).getImage();
            frame.add(imgPush);
        }
        totalFrames = TotalFrames;
        frame.get(0).getWidth(null);
        frame.get(0).getHeight(null);
        scale = scaling;
        firstFrame = frame.get(0);

    }

    public AnimationSystem(double x, double y, int TotalFrames, int siz, String animSource, double scaling, QuObject h){
        super(x, y, null);
        for (int i = 0; i < siz; i++){
            String sc = "assets/anim/" + animSource + ("" + i) + ".png";
            //System.out.println(sc);
            Image imgPush = new ImageIcon(sc).getImage();
            frame.add(imgPush);
        }
        totalFrames = TotalFrames;
        frame.get(0).getWidth(null);
        frame.get(0).getHeight(null);
        scale = scaling;
        firstFrame = frame.get(0);
        host = h;
    }

    public void flipVisible(){
        isVisible = !isVisible;
    }
    public void resetAnim(){
        currFrame = 0;
        isVisible = true;
    }
    public boolean isAlive(){
        return (currFrame < totalFrames);
    }

    public void updateAnimation(){
        currStep++;
        if(currStep == animSpeed){
            currFrame++;
            currStep = 0;
        }
        if (host != null){
            double x = host.getSpeedX();
            double y = host.getSpeedY();
            double m = host.getMovementSpeed();
            this.move(x * m, y * m);
        }
    }

    public void draw(Graphics g){
        if (!isVisible)return;
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform tr = new AffineTransform();
        tr.translate(posX, posY);
        tr.scale(scale, scale);
        if(isFade){
            float alpha = Math.max(0, 1 - (float)currFrame * 0.05f);
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    
            //Fix later
            g2d.setComposite(ac);
            g2d.drawImage(firstFrame, tr, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        }   
        else g2d.drawImage(frame.get(currFrame), tr, null);
    }    

    // public void drawFadeEffect(Graphics g){
    //     if (!isVisible)return;
    //     Graphics2D g2d = (Graphics2D) g;
    //     AffineTransform tr = new AffineTransform();
    //     float alpha = Math.max(0, 1 - (float)currFrame * 0.05f);
    //     AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

    //     g2d.setComposite(ac);
    //     tr.translate(posX, posY);
    //     tr.scale(scale, scale);
    //     g2d.drawImage(firstFrame, tr, null);
    //     currFrame++;
    // }   
}
