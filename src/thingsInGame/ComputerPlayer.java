package thingsInGame;

import java.awt.*;

import basicObjects.Ball;
import basicObjects.LineObject;

import java.awt.Graphics;
import java.awt.geom.*;
import java.util.concurrent.ThreadLocalRandom;
public class ComputerPlayer extends Player{
    private Ball targetBall;
    private int dirX;
    private int dirY;
    private int reactionTime = 1;
    private int detReactionTime = 1;
    private int currentRT = 0;
    private int currentDRT = 0;

    private double targetX = 0;
    private double targetY = 300;
    private double expectedX = 0;
    private double expectedY = 0;
    private double newBallX = 0;
    private double newBallY = 0;

    private int trigger = 0;
    private LineObject vLine  = new LineObject(0, 0, 0, 0, color);
    private LineObject vLine2  = new LineObject(0, 0, 0, 0, color);

    public ComputerPlayer (double x, double y, double rad, double hr, double we, String scr1, String scr2, Color colr, double fCP){
        super(x, y, rad, hr, we, scr1, scr2, colr, fCP);
    }
    public void setTargetBall(Ball b){
        targetBall = b;
    }
   
    private void determineBallDir() {
        if (isBehind() && targetBall.getSpeedX() * forceCalPoint <  0){
            expectedX = targetBall.getPotenX() - 200 * forceCalPoint;
            expectedY = targetBall.getPotenY() + targetBall.getSpeedY() * 0.2;
            trigger = 2;
            return;
        }
        if (!(((targetBall.getPosX() - 550) * forceCalPoint < 0) && targetBall.getSpeedX() * forceCalPoint < 0)) {
            trigger = 0;
            return;
        } 

        else if (trigger == 0 || (targetBall.getPosX() - posX) * forceCalPoint < 0) {
            double randT = ThreadLocalRandom.current().nextInt(1, 2) * 10;
            newBallX = targetBall.getPosX() + randT * targetBall.getSpeedX();
            newBallY = targetBall.getPosY() + randT * targetBall.getSpeedY();

            double vecX = targetX - newBallX;
            double vecY = targetY - newBallY;

            double randHR = ThreadLocalRandom.current().nextDouble(0.05, 0.1) * forceCalPoint;
            expectedX = newBallX - randHR * vecX;
            expectedY = newBallY - randHR * vecY;
            //vLine = new LineObject(posX, posY, expectedX, expectedY, Color.YELLOW);
            //vLine2 = new LineObject(newBallX, newBallY, targetX, targetY, Color.RED);
            trigger = 1;
        }
    }
    private void determineDir(){
        dirX = returnClamp((int)(targetBall.getPosX() - posX), -1, 1);
        dirY = returnClamp((int)(targetBall.getPosY() - posY), -1, 1);
    }
    private void determineDirNew(){
        //determineBallDir();
        if (trigger > 0){
            dirX = returnClamp((int)((expectedX - startPointX) / movementSpeed), -1, 1);
            dirY = returnClamp((int)((expectedY - hitBox.getPosY()) / movementSpeed), -1, 1);
            //System.out.println(dirX + " " + dirY + " " + (expectedX - posX) / movementSpeed);
        }
        else{
            dirX = dirY = 0;
        }
    }
    private void moveBoth(){
        speedX = dirX;
        speedY = dirY;
    }

    private void moveY(){
        speedY = dirY;
    }

    private void moveX(){
        speedX = dirX;
    }

    public void determineMove(){
        if(currentDRT == 0){
            determineBallDir();
            currentDRT = detReactionTime;
        }
        if(currentRT == 0 )
        {
            determineDirNew();
            currentRT = reactionTime;
        }
        currentRT--;
        currentDRT--;
        moveBoth();
        //moveY();
        //speedX = ThreadLocalRandom.current().nextInt(0, 1) * dirX;
    }
    public boolean isBehind(){
        // double timeToEx = magCalm2(posX - expectedX, posY - expectedY) / (2 * movementSpeedm2);
        // double timeToBall = magCalm2(targetBall.getPotenX() - newBallX, targetBall.getPotenY() - newBallY) / targetBall.getDefaultSpeedm2();
        return ((targetBall.getPotenX() - posX)  * forceCalPoint < 0);
    }

    public boolean goodToDash(){
        return (isBehind() && speedX * targetBall.getSpeedX() > 0);
    }
    public boolean isInRange(){
        //return false;
        //System.out.println(targetBall.getPosX() + " " + newBallX);
        if (!hitBox.isColliding(targetBall)) return false; 
        if(trigger == 2 || targetBall.getSpeedX() * forceCalPoint > 0 || (targetBall.getPotenX() < 100 * (forceCalPoint + 1) && targetBall.getPotenX() > -400 * (forceCalPoint - 1))) return true;
        return (Math.abs(targetBall.getPotenX() - newBallX) < 0.001 && Math.abs(targetBall.getPotenY() - newBallY) < 0.001);
    }

    public void draw(Graphics g){
        //vLine.draw(g);
        //vLine2.draw(g);
        //hitBox.draw(g2d);
        drawG(g);
    }
}
