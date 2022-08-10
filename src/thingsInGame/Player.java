package thingsInGame;
import java.awt.*;

import javax.swing.ImageIcon;

import basicObjects.Ball;
import basicObjects.LineObject;
import basicObjects.QuCircle;
import mainStuff.MainGameComponent;
import miscStuff.AnimationSystem;
import miscStuff.Particle;

import java.awt.Graphics;
import java.awt.geom.*;
public class Player extends QuCircle{
    private double hitRad;

    public double hitCooldown = 30;
    public double currentCooldown = 0;
    public double forceCalPoint = 0;
    public int hitting = 0;
    public int hitConnect = 0;
    public int activeHitFrames = 0;

    private double dashSpeedX = 0;
    private double dashCooldown = 150;
    private double currentDashCooldown = 0;
    private double dashDuration = 3;
    public double currentDashDuration = 0;
    public double dashSpeed = 30;

    public double imageWidth;
    public double imageHeight;
    public double batWidth;

    public int dashTrigger = 0;
    public static int freezeFrame = 0;
    private MainGameComponent mg;

    private double defaulMovementSpeed = 15;
    double movementSpeed = defaulMovementSpeed;
    double movementSpeedm2 = defaulMovementSpeed * defaulMovementSpeed;
    private double batScale = 0.5;
    private static double playerScale = 0.1;
    private double angle = 0;
    private double pivotPoint = 0;
    double reduceYPoint = 0;
    double offsetX2;


    QuCircle hitBox;
    Ball collBox;

    private LineObject templ = new LineObject(0,0,0,0,Color.GREEN);
    double startPointX;
    public void setGameComponent(MainGameComponent mgg){
        mg = mgg;
    }

    public MainGameComponent getGameComponent(){
        return mg;
    }
    public boolean isFast(){
        return (movementSpeed > defaulMovementSpeed);
    }
    public boolean canAttack(){
        return this.currentCooldown == 0;
    }
    public double getPlayerScale() {
        return Player.playerScale;
    }
    public double getBatScale() {
        return this.batScale;
    }
    public void setBatScale(double batScale) {
        this.batScale = batScale;
    }

    public double getHitRad() {
        return this.hitBox.r;
    }
    public double getCollBoxX(){
        return collBox.getPosX();
    }
    public double getCollBoxY(){
        return collBox.getPosY();
    }

    public void reduceCurrentCoolDown(){
        if (currentDashCooldown > 0) currentDashCooldown--;
    }
    
    public boolean isDashing(){
        return (0 < currentDashDuration--);
    }

    public boolean triggerDash(){
        if (currentDashCooldown == 0){
            currentDashCooldown = dashCooldown;
            currentDashDuration = dashDuration;
            return true;
        }
        return false;
    }
    public void setHitRad(double hitR) {
        this.hitBox.r = hitR;
        this.hitBox.setPosX(startPointX + hitR * this.forceCalPoint);
        this.hitBox.setPosY(posY  - hitR + this.hitRad);
        offsetX2 =  -hitR* (forceCalPoint - 1);
    }

    public double getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setMovementSpeed(double ms) {
		this.movementSpeed = ms;
	}
    
    public void followPos(){
        collBox.setSpeed(0, 0);

        posX = collBox.getPotenX() - imageWidth / 2;
        posY = collBox.getPotenY() - imageHeight / 2;
        collBox.setPos(collBox.getPotenX(), collBox.getPotenY());


        startPointX = posX + imageWidth * (forceCalPoint + 1) * 0.5;
        hitBox.setPos(startPointX +  hitBox.getR() * forceCalPoint, posY);
    }
    public void moveWithSpeed(){
        // this.posX += speedX * movementSpeed;
        // this.posY += speedY * movementSpeed;
        collBox.setPotenX(collBox.getPosX() + speedX * movementSpeed);
        collBox.setPotenY(collBox.getPosY() + speedY * movementSpeed);
        collBox.setSpeed(speedX * movementSpeed, speedY * movementSpeed);

        //followPos();
        // this.startPointX += speedX * movementSpeed;
        // hitBox.move(speedX * movementSpeed, speedY * movementSpeed);
    }
    public Player (double x, double y, double rad, double hr, double we, String scr1, String scr2, Color colr, double fCP){
        super(x, y,rad,we, colr);
        this.hitRad = hr;

        this.speedX = 0;
        this.speedY = 0;

        this.forceX = 0;
        this.forceY = 0;

        this.sprite = new ImageIcon(scr1).getImage();
        this.sprite2 = new ImageIcon(scr2).getImage();

        this.forceCalPoint = fCP;

        imageWidth = this.sprite.getWidth(null) * playerScale;
        imageHeight = this.sprite.getHeight(null) * playerScale;
        batWidth = this.sprite2.getWidth(null);

        startPointX = x + imageWidth * (fCP + 1) * 0.5; 
        hitBox = new QuCircle(startPointX + hr * fCP, y, hr, 0, Color.GREEN);
        pivotPoint = -2 * hitRad * (forceCalPoint - 1);
        reduceYPoint = -imageHeight;
        offsetX2 =  -hitBox.getR() * (forceCalPoint - 1);

        collBox = new Ball(posX + imageWidth / 2, posY + imageHeight / 2, imageHeight / 2, 0, Color.GREEN);
    }
  
    public Image returnSprite(){
        return this.sprite;
    }
    public Image returnSprite2(){
        return this.sprite2;
    }

    public void hitCheck(Ball temp){
        if (hitConnect == 1) return;
        double tPotenX = temp.getPotenX();
        double tPotenY = temp.getPotenY();

        if (hitBox.isColliding(temp)){
            double offCal = magCalm2(tPotenX - startPointX, tPotenY - hitBox.getPosY());  

            double tempSpeed = Math.max(temp.getSpeedm2(), temp.defaultSpeedm2);
            angle = 0;
            if(currentDashDuration > 0 && tempSpeed < temp.maxSpeedm2){
                double offsetX2 =  0;
                angle = 90 * (forceCalPoint - 1) + forceCalPoint * getAngle(startPointX - offsetX2, posY - this.hitRad + batWidth * batScale, tPotenX, tPotenY);
                freezeFrame = 10; 
                tempSpeed *= 16;
            } 
            //templ = new LineObject(startPointX, hitBox.getPosY(), tPotenX, tPotenY, Color.GREEN);
            double ratio = Math.sqrt(tempSpeed / offCal);

            double tSpX= (tPotenX - startPointX) * ratio;
            double tSpY = (tPotenY - hitBox.getPosY()) * ratio;
            temp.setSpeed(tSpX, tSpY);

            hitConnect = 1;
        } 
        hitting = 1;
    }

    public void collDetect(LineObject temp){

        collBox.collDetect(temp);
        //followPos();

    }

    public void hitActivate(int i){
        if (this.currentCooldown == 0) {
            createAnimationWithHost(getGameComponent(), 3, 3, "batSwing" + ("" + i), batScale);
            this.activeHitFrames = 10;
            this.currentCooldown = this.hitCooldown;
            this.hitConnect = 0;
        }
    }

    public void createParticle(MainGameComponent mg){
        double size = Math.random() * 4;
        Particle e = new Particle(posX + size, posY + imageHeight, size * size, size * size, Color.WHITE, 30);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }

    public void createParticle(MainGameComponent mg, Color colr){
        double size = Math.random() * 6;
        Particle e = new Particle(posX + size, posY + imageHeight, size * size, size * size, colr, 40);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }

    public void createAnimationWithHost(MainGameComponent mg, int totalFrames, int size, String scr, double scale){
        AnimationSystem e = new AnimationSystem(startPointX - offsetX2, posY - batWidth * batScale + hitRad + 8, totalFrames, size, scr, scale, this);
        e.setAnimationSpeed(2);
        e.resetAnim();
        mg.animList.add(e);
    }

    public void createAnimation(MainGameComponent mg, int totalFrames, int size, String scr, double scale){
        AnimationSystem e = new AnimationSystem(posX, posY, totalFrames, size, scr, scale);
        e.resetAnim();
        mg.animList.add(e);
    }

    public void drawG(Graphics g){
        //g.setColor(Color.GREEN);
        Graphics2D g2d = (Graphics2D) g;
        //templ.draw(g);
        //collBox.draw(g);
        //hitBox.draw(g2d);

        drawSprite(g2d, sprite, 0, 0, playerScale, 0, 0);
    
        double angl = Math.toRadians(-90 * forceCalPoint);
        if (activeHitFrames != 0) {
            angl = Math.toRadians(angle * forceCalPoint);
        }
        drawSprite(g2d, sprite2, posX - (startPointX - offsetX2), reduceYPoint, batScale, angl, pivotPoint);  
    }

    public void draw(Graphics g){
        drawG(g);
    }
}
