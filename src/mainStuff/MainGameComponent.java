package mainStuff;
import java.awt.*;
import javax.swing.*;

import basicObjects.*;
import miscStuff.*;
import powerups.*;
import thingsInGame.*;

import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MainGameComponent extends JComponent implements KeyListener, MouseListener, Runnable {
    // private int chieuQuay = 0;
    // private int chieuQuay2 = 0;
    private int cntLeft = 149;
    private boolean isRunning = false;
    private boolean isCounting = false;
    private double SCRW = 1100;
    private double SCRH = 680;
    private double gapSize = 150;
    private double lineDis = 50;
    // private double lineDisY = 20;

    private double ballStartX = SCRW / 2;
    private double ballStartY = SCRH / 2;

    private int universalPWDURATION = 600;
    private int currentUPWD = 200;
    private int gameScore = 3;
    private int flip = 0;

    private int currentBallHit = 0;
    private int mouseLineDraw = 0;

    private int mouseReleaseFling = 0;
    private int maxLineLength = 6;
    private int maxPWLength = 3;

    private CustomString p1Score = new CustomString("0", SCRW / 2 - 45, 50, Color.YELLOW);
    private CustomString p2Score = new CustomString("0", SCRW / 2 + 20, 50, Color.YELLOW);

    private int cntP1 = 0;
    private int cntP2 = 0;

    private int gameType;

    private int phatbong = 0;
    private int playerHit[] = new int[2];
    {
        playerHit[0] = 0;
        playerHit[1] = 0;
    }
    private int moveX[] = new int[2];
    {
        moveX[0] = 0;
        moveX[1] = 0;
    }
    private int moveY[] = new int[2];
    {
        moveY[0] = 0;
        moveY[1] = 0;
    }
    private int dashMoveY[] = new int[2];
    {
        dashMoveY[0] = 0;
        dashMoveY[1] = 0;
    }
    private int dashMoveX[] = new int[2];
    {
        dashMoveX[0] = 0;
        dashMoveX[1] = 0;
    }
    private Ball ball1 = new Ball(ballStartX, ballStartY, 16, 1, Color.WHITE);
    private Powerup powerupl[] = new Powerup[5];
    {
        powerupl[0] = new Repel(200, 500, 25, Color.BLUE, 250, "assets/gravity.png");
        powerupl[1] = new Speedup(200, 500, 25, Color.RED, 500, "assets/speed.png");
        powerupl[2] = new MovementDebuff(200, 500, 25, Color.WHITE, 350, "assets/confuse.png");
        powerupl[3] = new Bigbat(200, 500, 30, Color.YELLOW, 500, "assets/bigbat.png");
        powerupl[4] = new Shield(0, 0, 25, Color.CYAN, 250, "assets/shield.png");
    }
    private int currentPW = 0;

    private Background background1 = new Background(0, 0, SCRW, SCRH, Color.BLACK);

    private Player playerl[] = new Player[2];
    {
        playerl[0] = new Player(200, 350, 15, 80, 1, "assets/cheems01.png", "assets/bat01.png", Color.GREEN, 1);
       // playerl[1] = new Player(SCRW - 200, 350, 15, 80, 1, "assets/cheems02.png", "assets/bat02.png", Color.GREEN, -1);
        playerl[1] = new ComputerPlayer(SCRW - 200, 350, 15, 80, 1, "assets/cheems02.png", "assets/bat02.png", Color.GREEN, -1);
   
    }
    private LineObject linel[] = new LineObject[6];
    {
        linel[5] = new LineObject(lineDis, 0, lineDis, (SCRH - gapSize) / 2, Color.RED);
        linel[1] = new LineObject(lineDis, (SCRH - gapSize) / 2 + gapSize, lineDis, SCRH, Color.RED);

        linel[2] = new LineObject((SCRW - lineDis), 0, (SCRW - lineDis), (SCRH - gapSize) / 2, Color.BLUE);
        linel[3] = new LineObject((SCRW - lineDis), (SCRH - gapSize) / 2 + gapSize, (SCRW - lineDis), SCRH, Color.BLUE);

        // linel[4] = new line(400, 350, 400, 150, Color.YELLOW);

        linel[4] = new LineObject(lineDis, 0, SCRW - lineDis, 0, Color.YELLOW);
        linel[0] = new LineObject(lineDis, SCRH, SCRW - lineDis, SCRH, Color.YELLOW);
    }
    private boolean keys[] = new boolean[100];
    private QuObject cntObject = new QuObject(SCRW /2 - 375, 20, Color.BLACK);
    private SoundEffect soundEffect = new SoundEffect();

    public ArrayList<Particle> particleList = new ArrayList<>();
    public ArrayList<AnimationSystem> animList = new ArrayList<>();
    public ArrayList<Powerup> pwList = new ArrayList<>();
    public ArrayList<LineObject> lineList = new ArrayList<>();
    private double mouseX;
    private double mouseY;

    private Play playGame;

    private Rec pauseThing = new Rec(SCRW / 2 - 100, 200, 216, 216, Color.GRAY, "assets/return.png");
    private Rec screenFilter = new Rec(0, 0, SCRW, SCRH, Color.BLACK);
    Config config = new Config(this);

    public Play getPlay() {
        return this.playGame;
    }

    public void setPlay(Play jp) {
        this.playGame = jp;
    }
    public void setGameType(int l){
        gameType = l;
    }

    public void setGameScore(int l){
        gameScore = l;
    }

    // private int startTime;
    // private int stopTime;

    public MainGameComponent() {
        Font font = new Font("Arial", Font.PLAIN, 50);
        setFont(font);
        addKeyListener(this);
        addMouseListener(this);
    }

    public void beginGame() {
        config.loadConfig();
        //System.out.println(gameType);
        
        Thread run = new Thread(this);
        run.start();
    }

    public synchronized void playSound(final String url) {
        try {
         Clip clip = AudioSystem.getClip();
         AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          getClass().getResourceAsStream("src/" + url));
         clip.open(inputStream);
         clip.start(); 
        } catch (Exception e) {
         System.err.println(e.getMessage());
        }
      }

    public void run() {
        cntP1 = cntP2 = 0;
        bigReset();
        while (true) {
            // startTime = (int) System.currentTimeMillis();
            try {
                Thread.sleep(20);
            } catch (Exception ex) {
            }
            moveX[0] = moveX[1] = moveY[0] = moveY[1] = 0;
            keyCodeUpdate();
            if(isCounting) countDown();
            else if(isRunning){
                requestFocusInWindow();
                update();
            }
            repaint();
            // stopTime = (int) System.currentTimeMillis();
            // System.out.println(stopTime - startTime);
        }
    }
    public void dashInputUpdate(int i){
        if (moveX[i] != 0 || moveY[i] != 0) {
            if(playerl[i].triggerDash()){
                dashMoveX[i] = moveX[i];
                dashMoveY[i] = moveY[i];
            }
        }
    }
    private void cPInput(int i){
        ((ComputerPlayer)playerl[i]).determineMove();
        moveX[i] = (int)playerl[i].getSpeedX();
        moveY[i] = (int)playerl[i].getSpeedY();
        if(((ComputerPlayer)playerl[i]).goodToDash()) dashInputUpdate(i);
        if(((ComputerPlayer)playerl[i]).isInRange())playerHit[i] = 1;
    }
    public void playerMovementUpdate(int i) {

        playerl[i].reduceCurrentCoolDown();
        double mX = moveX[i];
        double mY = moveY[i];
        if (playerl[i].isDashing()){
            mX = dashMoveX[i] * 4;
            mY = dashMoveY[i] * 4;
            playerl[i].createAnimation(this, 50, 1, "cheems" + ("" + i), playerl[i].getPlayerScale());
            setFadeCurrent();

        } 
        else if ((mX != 0 || mY != 0) && flip == 0) {
            if (playerl[i].isFast()) {
                playerl[i].createAnimation(this, 50, 1, "cheemsSpeed" + ("" + i), playerl[i].getPlayerScale());
                setFadeCurrent();
            }
            playerl[i].createParticle(this);

        }
        
        playerl[i].setSpeed(mX, mY);
        playerl[i].moveWithSpeed();
    }
    public void countDown(){
        cntLeft--;
        //isRunning = (cntLeft < 0);
        if(cntLeft < 0) isCounting = false;
    }
    public void playerHitUpdate(int i) {
        playerl[i].hitting = 0;
        if (playerHit[i] == 1) {
            playerl[i].hitActivate(i);
        }

        if (playerl[i].activeHitFrames > 0) {
            playerl[i].hitCheck(ball1);
            if (playerl[i].hitConnect == 1 && powerupl[currentPW].powerupCurrentDuration == -1) currentBallHit = i;
            playerl[i].activeHitFrames--;
        }
        playerHit[i] = 0;

        if (playerl[i].currentCooldown > 0)
            playerl[i].currentCooldown--;
    }
    public void setFadeCurrent(){
        if(animList.size() > 0) animList.get(animList.size() - 1).setFade();
    }
    public void bigReset(){
        ball1.reset(SCRW / 2, SCRH / 2);
        phatbong = 0;
        cntLeft = 149;
        isRunning = true;
        isCounting = true;
        cntObject.createAnimation(this, 3, 3, "Count", 1, 50);
        playerl[0] = new Player(200, 350, 15, 80, 1, "assets/cheems01.png", "assets/bat01.png", Color.GREEN, 1);
        if (gameType == 2) {
            ComputerPlayer e1 = new ComputerPlayer(200, 350, 15, 80, 1, "assets/cheems01.png", "assets/bat01.png", Color.GREEN, 1);
            e1.setTargetBall(ball1);
            playerl[0] = e1;
        }
        

        playerl[1] = new Player(SCRW - 200, 350, 15, 80, 1, "assets/cheems02.png", "assets/bat02.png", Color.GREEN, -1);
        if (gameType >= 1) {
            //System.out.println("test");
            ComputerPlayer e = new ComputerPlayer(SCRW - 200, 350, 15, 80, 1, "assets/cheems02.png", "assets/bat02.png",Color.GREEN, -1);
            e.setTargetBall(ball1);
            playerl[1] = e;
        }
        
        playerl[0].setGameComponent(this);
        playerl[1].setGameComponent(this);
        currentUPWD = 80;
        pwList.clear();
        lineList.clear();
        for (int i = 0; i < maxLineLength; i++){
            lineList.add(linel[i]);
        }
    }
    public void outOfBoundCheck() {
        if (ball1.getPosX() < 0 || ball1.getPosX() > SCRW) {
            if(ball1.getPosX() < 0) {
                cntP2++;
                p2Score.setString(cntP2);
            }
            else{
                cntP1++;
                p1Score.setString(cntP1);
            }
            try {
                repaint();
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(cntP1 == gameScore) {
                Play pl = getPlay();
                pl.returnToGame1();
            } 

            else if(cntP2 == gameScore) {
                Play pl = getPlay();
                pl.returnToGame2();
            } 
            bigReset();
        }
    }

    public void update() {
        // Hiệu ứng dash + hit
        if (Player.freezeFrame > 0) {
            Player.freezeFrame--;
            return;
        }
        // Vị trí chuột
        mouseX = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;

        // Xử lý di chuyển dash
        // Xử lý phát bóng
        // if(mouseLineDraw == 1){
        // ball1.addSpeed((mouseX - ball1.getPosX()) / 160, (mouseY - ball1.getPosY()) /
        // 160);

        // }
        // if (mouseReleaseFling == 1) {
        //     ball1.setForce((mouseX - ball1.getPosX()) / 10, (mouseY - ball1.getPosY()) / 10);
        //     ball1.forceToSpeed();
        //     mouseReleaseFling = 0;
        //     // phatbong = 1;
        // }

        // Xử lý phát bóng trong mỗi
        // set-----------------------------------------------------------------
        if (phatbong == 0) {
            double deviateX = ThreadLocalRandom.current().nextDouble(0, 24) - 12;
            double deviateY = ball1.reverseMagCal(ball1.defaultSpeed, deviateX);
            ball1.setSpeed(deviateX, deviateY);
            phatbong = 1;
        }
        // -----------------------------------------------------------------------------------------------

        // Áp dụng trọng lực (Chưa
        // dùng)------------------------------------------------------------
        // ball1.applyGravity();
        // --------------------------------------------//---------------------------------------------

        // Tính vị trí tiếp theo của bóng khi chưa xảy ra va chạm
        ball1.setPotenX(ball1.getPosX() + ball1.getSpeedX());
        ball1.setPotenY(ball1.getPosY() + ball1.getSpeedY());

        // Xử lý va chạm, điều chỉnh vị trí tiếp theo và hướng đi và tốc độ bóng
        for (int i = 0; i < lineList.size(); i++) {
            ball1.collDetect(lineList.get(i));
            if (ball1.isTouchingLine()) {
                for (int j = 0; j < 4; j++)
                    ball1.createParticle(this, ball1.getPotenX(), ball1.getPotenY(), lineList.get(i).getColor());
            }
        }

        // Xử lý di chuyển nhận lệnh đánh bóng của người chơi
        if(gameType >= 1) {
            cPInput(1);
            if (gameType == 2) cPInput(0);
        }

        for (int i = 0; i <= 1; i++) {
            playerMovementUpdate(i);
            playerHitUpdate(i);
        }
        flip = 1 - flip;
        for (int i = 0; i < lineList.size(); i++) {
            playerl[0].collDetect(lineList.get(i));
            playerl[1].collDetect(lineList.get(i));
        }
        for (int i = 0; i <= 1; i++){
            playerl[i].followPos();
        }
        // Xử lý bóng chạm vào powerup

        if (currentUPWD <= 0 && pwList.size() < 1) {
            currentUPWD = universalPWDURATION;
            currentPW = ThreadLocalRandom.current().nextInt(0, 5);
            Powerup e = powerupl[currentPW];

           e.setPosY(ThreadLocalRandom.current().nextDouble(SCRH * 0.2, SCRH * 0.8));
           e.setPosX(SCRW / 2 + (ThreadLocalRandom.current().nextDouble(0, 2) - 1) * 50);
           e.powerupCurrentDuration = -1;

            pwList.add(e);

           e.createParticle(this,e.getColor());
           e.createParticle(this,e.getColor());
           e.createParticle(this,e.getColor());

        }
        currentUPWD = Math.max(0, --currentUPWD);
        for (int i = 0; i < pwList.size(); i++) {
            pwList.get(i).pwHandling(ball1, playerl[currentBallHit]);
            if(pwList.get(i).powerupCurrentDuration == -2) pwList.remove(i);
        }

        // Tạo hiệu ứng
        if (ball1.getSpeedm2() > 1800)
            ball1.createParticle(this);
        // if (ball1.getSpeedm2() > 7200) ball1.createParticle(this, ball1.getPotenX(),
        // ball1.getPotenY(), Color.WHITE);

        // Dịch chuyển bóng sang vị trí tiếp theo
        ball1.setPos(ball1.getPotenX(), ball1.getPotenY());

        // reset khi bóng ra ngoài vùng chơi
        outOfBoundCheck();
    }
    public void keyCodeUpdate(){
        if(isCounting) return;

        if (!isRunning) return;
        
        if (keys[KeyEvent.VK_UP]) moveY[1] = -1;
        if (keys[KeyEvent.VK_DOWN]) moveY[1] = 1;
        if (keys[KeyEvent.VK_LEFT]) moveX[1] = -1;
        if (keys[KeyEvent.VK_RIGHT]) moveX[1] = 1;
        if (keys[KeyEvent.VK_P]) {
            if (playerHit[1] == 0) playerHit[1] = 1;
        }
        if (keys[KeyEvent.VK_L]) dashInputUpdate(1);


        if (keys[KeyEvent.VK_W]) moveY[0] = -1;
        if (keys[KeyEvent.VK_S]) moveY[0] = 1;
        if (keys[KeyEvent.VK_A]) moveX[0] = -1;
        if (keys[KeyEvent.VK_D]) moveX[0] = 1;
        if (keys[KeyEvent.VK_E]) dashInputUpdate(0);

        if (keys[KeyEvent.VK_SPACE]) {
            if (playerHit[0] == 0) playerHit[0] = 1;
        }
    }
    public void keyPressed(KeyEvent e) {
        if(isCounting) return;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) isRunning = !isRunning;
        if (!isRunning) return;
        keys[e.getKeyCode()] = true;
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    public void keyTyped(KeyEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        // if (!isRunning)
        //     return;
        // mouseLineDraw = 1;
        // mouseReleaseFling = 0;
    }

    public void mouseReleased(MouseEvent e) {
        // if (!isRunning)
        //     return;
        // mouseLineDraw = 0;
        // mouseReleaseFling = 1;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (!isRunning && x >= pauseThing.getPosX() && x <= pauseThing.getPosX() + pauseThing.width && 
        y >= pauseThing.getPosY() && y <= pauseThing.getPosY() + pauseThing.height){
            getPlay().returnToGameMain();
        }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void paint(Graphics g) {
        synchronized (g) {
            Graphics2D g2d = (Graphics2D) g;
            background1.draw(g2d);
            p1Score.draw(g);
            p2Score.draw(g);


            playerl[0].draw(g2d);
            playerl[1].draw(g2d);

            for (int i = 0; i < pwList.size(); i++){
                //setFont(pwList.get(i).font);
                pwList.get(i).draw(g2d);    
            }
            // if (mouseLineDraw == 1) {
            //     // Graphics2D g2d = (Graphics2D) g;
            //     Shape line = new Line2D.Double(ball1.getPosX(), ball1.getPosY(), mouseX, mouseY);
            //     g2d.draw(line);
            // }

            int width = 4;
            g2d.setStroke(new BasicStroke(width));
            // for (int i = 0; i < maxLineLength; i++)
            //     linel[i].draw(g2d);

            for (int i = 0; i < lineList.size(); i++)
                lineList.get(i).draw(g2d);

            // System.out.println(particleList.size());
            for (int i = 0; i < particleList.size(); i++) {

                particleList.get(i).draw(g);
                particleList.get(i).updateParticle();
                if (!particleList.get(i).isAlive())
                    particleList.remove(i);
            }
            for (int i = 0; i <= 1; i++) {
                playerl[i].draw(g2d);
            }
            ball1.draw(g2d);

            if (!isRunning) {
                float alpha = Math.max(0, 0.7f);
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                screenFilter.draw(g2d);;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                pauseThing.drawWithImages(g);
            }
            for (int i = 0; i < animList.size(); i++) {
                animList.get(i).draw(g);
                animList.get(i).updateAnimation();
                if (!animList.get(i).isAlive()){
                    animList.remove(i);
                }
            }

        }

    }
}
