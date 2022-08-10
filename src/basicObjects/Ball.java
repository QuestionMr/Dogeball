package basicObjects;
import java.awt.*;
import java.awt.geom.*;

import mainStuff.MainGameComponent;
import miscStuff.Particle;

public class Ball extends QuCircle {

    public double db = 0;
    public double defaultSpeed = 20;
    public double defaultSpeedm2 =defaultSpeed * defaultSpeed;

    public double getDefaultSpeedm2() {
        return this.defaultSpeedm2;
    }

    public void setDefaultSpeedm2(double defaultSpeedm) {
		this.defaultSpeedm2 = defaultSpeedm;
	} 
    public double maxSpeedm2 = 9216;
    
    private boolean lineTouch = false;
    LineObject tempLine = new LineObject(0, 0, 0 , 0, Color.WHITE);
    // line crossLine;
    int crossYes = 0;
    public Ball(double x, double y, double rad, double we, Color colr){
        super(x, y,rad,we,colr);

        this.posX = x;
        this.posY = y;

        this.r = rad;
        this.color = colr;

        this.speedX = 0;
        this.speedY = 0;

        this.forceX = 0;
        this.forceY = 0;

        this.weight = we;
    }
    public void reset(double x, double y){
        this.posX = x;
        this.posY = y;

        this.speedX = 0;
        this.speedY = 0;

        this.forceX = 0;
        this.forceY = 0;

        this.defaultSpeed = 12;
    }
    public void setContactHit(boolean l){
    }
    public void move(double x, double y){
        this.posX += x;
        this.posY += y;
    }
    //circle rectangle collision detection
    public void collDetect(Rec temp){

        //Tìm điểm trên rectangle gần nhất với tọa độ tâm tròn
        double nearestX = Math.max(temp.posX, Math.min(temp.posX + temp.width, this.potenX));
        double nearestY = Math.max(temp.posY, Math.min(temp.posY + temp.height, this.potenY));

        //Khoảng cách tọa độ từ tâm tròn đến điểm đó
        double raytoX = nearestX - this.potenX;
        double raytoY = nearestY - this.potenY;

        //Độ dài khoảng cách vừa tính
        double raytoMag = magCal(raytoX, raytoY);
        //Tính overlap để xét nếu khoảng cách nhỏ hơn bán kính hình tròn
        double overlap = this.r - raytoMag;

        if (raytoMag == 0){
            raytoMag = 0.01;
        }
        if (overlap > 0){
            //Khi overlap, đẩy hình tròn theo lượng x và y tương ứng 
            this.potenX -= raytoX / raytoMag * overlap;
            this.potenY -= raytoY / raytoMag * overlap;
            System.out.println(overlap + " " + this.potenX + " " + this.potenY);

            //Đổi chiều chuyển động khi va chạm
            if (raytoX != 0)this.speedX *= -1;
            if (raytoY != 0)this.speedY *= -1;
        }
    }
    
    //cỉcle line collision detection
    public void collDetect(LineObject temp){
        lineTouch = false;
        //if (temp.noCollisionFrame == 5) {temp.noCollisionFrame = 0; return;}

        //Vị trí của hình tròn trong frame tiếp theo
        double ogPotenX = this.potenX;
        double ogPotenY = this.potenY;

        //Khoảng cách x và y giữa hai đầu đoạn thẳng
        double diX = temp.desPosX - temp.posX;
        double diY = temp.desPosY - temp.posY;

        //Nếu hình tròn di chuyển quá nhanh
        //------------------------------//TEST//-----------------------------------------------------------------//
        //Tính giao của vector chuyển động của hình tròn lên đoạn thẳng
        double tiLe = r / temp.lineLength;
        double extraEdgeX = temp.posX + (temp.posX - temp.desPosX) * tiLe;
        double extraEdgeY = temp.posY + (temp.posY - temp.desPosY) * tiLe;
        //tempLine = new line(extraEdgeX, extraEdgeY, extraEdgeDesX, extraEdgeDesY, Color.WHITE);
        double extraDIX = diX - (temp.posX - temp.desPosX) * tiLe * 2;
        double extraDIY = diY - (temp.posY - temp.desPosY) * tiLe * 2;

        double l = (-(extraEdgeY - this.potenY) * this.speedX + (extraEdgeX- this.potenX) * this.speedY) / -(this.speedY * extraDIX - this.speedX * extraDIY);
        //double l = (-(temp.posY - this.potenY) * this.speedX + (temp.posX - this.potenX) * this.speedY) / -(this.speedY * diX - this.speedX * diY);
        
        double clampedL = returnClamp(l, 0, 1);

        // double giaoX = temp.posX + clampedL * diX;
        // double giaoY = temp.posY + clampedL * diY;

        double giaoX = extraEdgeX + clampedL * extraDIX;
        double giaoY = extraEdgeY + clampedL * extraDIY;
        //crossLine = new line(this.potenX, this.potenY, giaoX, giaoY, Color.RED);
        if (clampedL == l){;

            //Trường hợp tọa độ ở frame tiếp theo của hình tròn nằm khác phía của đoạn thẳng với vị trí ban đầu
            if (((giaoX - ogPotenX) * (giaoX - this.posX) <= -0.001) || ((giaoY - ogPotenY) * (giaoY - this.posY) <= -0.001) ){
                double tL = r / magCal(this.speedX, this.speedY) * 0.1;
                this.potenX = giaoX - tL * this.speedX;
                this.potenY = giaoY - tL * this.speedY;
            }
        }
        //--------------------------------------//ENDTEST//----------------------------------------------------------------//
                
        //Tìm hình chiếu của tâm hình tròn lên đoạn thẳng
        double t = (diX * (this.potenX - temp.posX) + diY * (this.potenY - temp.posY)) / magCalm2(diX, diY);

        //Giới hạn điểm

        //#Cách 2(ngắn hơn)
        //System.out.println(t);
        t = returnClamp(t, 0, 1);
        double nearestX = temp.posX + diX * t;
        double nearestY = temp.posY + diY * t;
        //tempLine = new line(this.potenX, this.potenY, nearestX, nearestY, Color.WHITE);

        //Giới hạn điểm gần nhất giữa tọa độ hai đầu đoạn thẳng
        //#Cách 1(dài)
        //  nearestX = returnClamp(nearestX, Math.min(temp.desPosX, temp.posX), Math.max(temp.desPosX, temp.posX));
        //  nearestY = returnClamp(nearestY, Math.min(temp.desPosY, temp.posY), Math.max(temp.desPosY, temp.posY));
        
        
        //Khoảng cách từ tâm đến điểm gần nhất
        double raytoX = nearestX - this.potenX;
        double raytoY = nearestY - this.potenY;

        double raytoMag = magCal(raytoX, raytoY);
        double overlap = this.r - raytoMag;

        if (raytoMag == 0) raytoMag = 0.0001;


        if (overlap > 0){
            lineTouch = true;
            //temp.noCollisionFrame++;

            //Tính giao của vector chuyển động của hình tròn lên đoạn thẳng
            // double l = ((temp.posY - this.potenY) * diX - (temp.posX - this.potenX) * diY) / (this.speedY * diX - this.speedX * diY);
            // double clampedL = returnClamp(l, -1, 1);

            // double giaoX = this.potenX + clampedL * speedX;
            // double giaoY = this.potenY + clampedL * speedY;
            // crossLine = new line(this.potenX, this.potenY, giaoX, giaoY, Color.RED);
            
            //Sửa lại vị trí tiếp theo 
            this.potenX -= raytoX / raytoMag * overlap;
            this.potenY -= raytoY / raytoMag * overlap;


            //Trường hợp tọa độ ở frame tiếp theo của hình tròn nằm khác phía của đoạn thẳng với vị trí ban đầu
            // if (((giaoX - ogPotenX) * (giaoX - this.posX) <= -0.001) || ((giaoY - ogPotenY) * (giaoY - this.posY) <= -0.001) ){
            //     this.potenX = 2 * giaoX - this.potenX;
            //     this.potenY = 2 * giaoY - this.potenY;

            //     nearestX = 2 * giaoX - nearestX;
            //     nearestY = 2 * giaoY - nearestY;

            //     raytoX = nearestX - this.potenX;
            //     raytoY = nearestY - this.potenY;
             
            // }

            //Xử lý chiểu chuyển động
            double speedBoth = magCalm2(this.speedX, this.speedY);
            if (speedBoth == 0) return;
            double newDxX = giaoX * 2 - nearestX;
            double newDxY = giaoY * 2 - nearestY;

            double newLechX = newDxX - raytoX;
            double newLechY = newDxY - raytoY;

            double newspeedBoth = magCalm2(newLechX - giaoX, newLechY - giaoY);
            double ratio = Math.sqrt(speedBoth/newspeedBoth);

            this.speedX = (newLechX - giaoX) * ratio;
            this.speedY = (newLechY - giaoY) * ratio;

        }


    } 
   
    public void applyGravity(){
        addSpeed(0, 1);
    }
    public boolean isTouchingLine(){
        return lineTouch;
    }
    public void createParticle(MainGameComponent mg, double px, double py, Color color){
        double size = Math.random() * 2 + 3;
        Particle e = new Particle(px + size, py + size, size * size, size * size, color, 50);
        e.setSpeed((Math.random() * 2 - 1) * 2, (Math.random() * 2 - 1)* 2);
        mg.particleList.add(e);
    }
    
    public void draw(Graphics g){
        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D) g;
        Shape ballShape = new Ellipse2D.Double(this.posX - this.r, this.posY - this.r, this.r * 2, this.r * 2);
        g2d.draw(ballShape);
        g2d.fill(ballShape);
        //tempLine.draw(g);
        //crossLine.draw(g);
    }
    
}
