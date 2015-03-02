package danimartin.com.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

/**
 * Created by d.martin on 16/02/2015.
 */
public class Projectile {


    private float x;
    private float y;
    private float angulo;
    private float ladoX, ladoY;
    private double ladoH;
    private double xSpeed;
    private double ySpeed;
    private double projSpeed=8;
    private Bitmap bmp;
    private GameView gameView;
    private int life = 15;
    private int currentFrame=0;
    private int Steps=0;
    private int BMP_COLUMNS;
    boolean damaging;

    private int width;
    private int height;
    private List<TempSprite> temps;
    private List<Projectile> Projectiles;

    public Projectile(GameView gameView, Sprite sp, float tX,float tY,int tipoSprite) {
        this.gameView = gameView;
        switch(tipoSprite)
        {
            case(1):
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.arrow);
                life = 12;
                BMP_COLUMNS=12;
                this.width = bmp.getWidth() / BMP_COLUMNS;
                this.height = bmp.getHeight();

                break;
        }
        this.x = sp.getposX();
        this.y = sp.getposY();
        setAngulo(tX, x, tY, y);
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = (1) * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect((int)x-this.width/2, (int)y-this.height/2, (int)x+this.width/2 , (int)y+this.height/2);
        canvas.rotate(angulo-45);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isDamaging() {
        return damaging;
    }

    public int getLife() {
        return life;
    }

    private void update() {

        this.x = this.x + (int)xSpeed;
        this.y = this.y + (int)ySpeed;

        if (this.x >= gameView.getWidth() - width - xSpeed || this.x + xSpeed <= 0) {
            life=0;
        }
        if (this.y >= gameView.getHeight() - height - ySpeed || this.y + ySpeed <= 0) {
            life=0;
        }

        if(Steps==4){
            currentFrame = ++currentFrame % BMP_COLUMNS;
            Steps=0;
        }
        Steps++;
    }



    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public double getXs() {
        return xSpeed;
    }
    public double getYs() {
        return ySpeed;
    }

    public int getWidth() {
        return width;
    }

    private void setAngulo(float toX, float fromX, float toY ,float fromY){
        Log.d("Flecha", "fromX: " + fromX + " fromY: " + fromY + " toX: " + toX + " toY "+toY);
        ladoX=Math.abs(fromX-toX);
        ladoY=Math.abs(fromY-toY);
        ladoH=Math.hypot(ladoX, ladoY);
        if(toX-fromX>0 && toY-fromY<0)
        {
            this.angulo=(int) Math.toDegrees(Math.asin(ladoY/ladoH));
            this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
            this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
            //this.angulo=360-this.angulo;
            Log.d("Cuadrante1", "ladoX: " + ladoX + " ladoY: " + ladoY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
        }
        if(toX-fromX<0 && toY-fromY>0)
        {
            this.angulo=180+(int) Math.toDegrees(Math.asin(ladoY/ladoH));
            this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
            this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
            //this.angulo=180+this.angulo;
            Log.d("Cuadrante2", "ladoX: " + ladoX + " ladoY: " + ladoY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
        }
        if(toX-fromX<0 && toY-fromY<0)
        {
            this.angulo=180-(int) Math.toDegrees(Math.asin(ladoY/ladoH));
            this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
            this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
            //this.angulo=180-this.angulo;
            Log.d("Cuadrante3","ladoX: " + ladoX + " ladoY: " + ladoY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
        }
        if(toX-fromX>0 && toY-fromY>0)
        {
            this.angulo=360-(int) Math.toDegrees(Math.asin(ladoY/ladoH));
            this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
            this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
            //this.angulo=360-this.angulo;
            Log.d("Cuadrante4","ladoX: " + ladoX + " ladoY: " + ladoY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
        }
    }
}
