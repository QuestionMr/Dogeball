package basicObjects;
import java.awt.*;
import java.awt.geom.*;

import mainStuff.MainGameComponent;
import miscStuff.*;

public class QuObject {
    public double posX;
    public double posY;

    public double speedX;
    public double speedY;

    public double movePosX;
    public double movePosY;

    public double forceX;
    public double forceY;

    public double potenX;
    public double potenY;

    public Color color;

    public double weight;

    private double ogPosX;
    private double ogPosY;

    private double movementSpeed;

    protected Image sprite;
    protected Image sprite2;
    public QuObject(double x, double y, Color colr){
        this.posX = x;
        this.posY = y;
        this.color = colr;

        this.ogPosX = x;
        this.ogPosY = y;
    }

    public double getMovementSpeed(){
        return this.movementSpeed;
    }
    public double getPotenX() {
        return this.potenX;
    }

    public void setPotenX(double potenX) {
        this.potenX = potenX;
    }

    public double getPotenY() {
        return this.potenY;
    }

    public void setPotenY(double potenY) {
        this.potenY = potenY;
    }


    public double getMovePosX() {
        return this.movePosX;
    }

    public void setMovePosX(double movePosX) {
        this.movePosX = movePosX;
    }

    public double getMovePosY() {
        return this.movePosY;
    }

    public void setMovePosY(double movePosY) {
        this.movePosY = movePosY;
    }

    public void setMovePos(double movePosX, double movePosY) {
        this.movePosY = movePosY;
        this.movePosX = movePosX;
    }

    public double getPosX() {
        return this.posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }


    public double getForceX() {
        return this.forceX;
    }

    public void setForceX(double forceX) {
        this.forceX = forceX;
    }

    public double getForceY() {
        return this.forceY;
    }

    public void setForceY(double forceY) {
        this.forceY = forceY;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPos(double x, double y){
        this.posX = x;
        this.posY = y;
    }
    public void moveWithSpeed(){
        this.posX += this.speedX;
        this.posY += this.speedY;
    }
    public void setForce(double forceX, double forceY) {
        this.forceX = forceX;
        this.forceY = forceY;
    }

    public void addForce(double forceX, double forceY) {
        this.forceX += forceX;
        this.forceY += forceY;
    }

    public void setSpeed(double speedX, double speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void addSpeed(double speedX, double speedY) {
        this.speedX += speedX;
        this.speedY += speedY;
    }

    public void forceToSpeed(){
        this.speedX = this.forceX / this.weight;
        this.speedY = this.forceY / this.weight;
    }

    double magCal(double x, double y){
        return Math.sqrt(x * x + y * y);
    }

    protected double magCalm2(double x, double y){
        return x * x + y * y;
    }

    public double reverseMagCal(double x, double y){
        return Math.sqrt(x * x - y * y);
    }

    protected double returnClamp(double x, double mini, double maxi){
        return Math.max(mini, Math.min(x, maxi));
    }

    protected int returnClamp(int x, int mini, int maxi){
        return Math.max(mini, Math.min(x, maxi));
    }

    public void move(double x, double y){
        this.posX += x;
        this.posY += y;
    }

    public double getSpeedm2(){
        return magCalm2(this.speedX, this.speedY);
    }

    public void resetPos(){
        this.posX = this.ogPosX;
        this.posY = this.ogPosY;
    }

    public void createParticle(MainGameComponent mg){
        double size = Math.random() * 3;
        Particle e = new Particle(posX + size, posY + size, size * size, size * size, Color.WHITE, 20);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }

    public void createParticle(MainGameComponent mg, Color colr){
        double size = Math.random() * 7;
        Particle e = new Particle(posX + size, posY + size, size * size, size * size, colr, 30);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }

    
    public void createAnimation(MainGameComponent mg, int totalFrames, int size, String scr, double scale){
        AnimationSystem e = new AnimationSystem(posX, posY, totalFrames, size, scr, scale);
        e.resetAnim();
        mg.animList.add(e);
    }

    public void createAnimation(MainGameComponent mg, int totalFrames, int size, String scr, double scale, int sp){
        AnimationSystem e = new AnimationSystem(posX, posY, totalFrames, size, scr, scale);
        e.resetAnim();
        e.setAnimationSpeed(sp);
        mg.animList.add(e);
    }

    public double getAngle(double x, double y, double dx, double dy){
        double angle = (double)Math.toDegrees(Math.atan2(dy - y, dx - x));
        return angle;
    }

    public void drawSprite(Graphics g, Image spr, double transX, double transY, double scale, double angl, double exPivX) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform tr = new AffineTransform();
        tr.translate(posX - transX, posY - transY);
        tr.scale(scale, scale);
        tr.rotate(angl, exPivX, 0);
        g2d.drawImage(spr, tr, null);
    }
    
}
