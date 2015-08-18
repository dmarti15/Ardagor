package com.dmarti15.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by d.martin on 15/12/2014.
 */
public class TempSprite {


    private float x;
    private float y;
    private int angulo;
    private Bitmap bmp;
    private int life = 15;
    private int currentFrame=0;
    private int Steps=0;
    private int BMP_COLUMNS;
    boolean damaging;

    private int width;
    private int height;
    private List<TempSprite> temps;

    public TempSprite(GameView gameView, float x,float y,int tipoSprite) {
        switch(tipoSprite)
        {
            case(1):
                this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.lux);
                life = 48;
                BMP_COLUMNS=12;
                this.width = bmp.getWidth() / BMP_COLUMNS;
                this.height = bmp.getHeight();
                this.x = (int)x;
                this.y = (int)y;
                break;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = (currentFrame-1) * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect((int)x-this.width/2, (int)y-this.height/2, (int)x+this.width/2 , (int)y+this.height/2);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isDamaging() {
        return damaging;
    }

    public int getLife() {
        return life;
    }

    private void update() {
        if (currentFrame == 5) {
            damaging = true;
        } else {
            damaging = false;
        }
        life--;
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

    public int getWidth() {
        return width;
    }

    private void setAngulo(int toX, int fromX, int toY ,int fromY){
        if(toX-fromX>0 && toY-fromY<0) this.angulo=360-(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
        if(toX-fromX<0 && toY-fromY>0) this.angulo=180+(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
        if(toX-fromX<0 && toY-fromY<0) this.angulo=180+(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
        if(toX-fromX>0 && toY-fromY>0) this.angulo=360-(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
    }
}