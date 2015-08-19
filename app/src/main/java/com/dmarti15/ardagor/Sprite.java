package com.dmarti15.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by d.martin on 10/12/2014.
 */
public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 4;
    private String clase= "";
    public String getClase() {
        return clase;
    }
    private int pts;
    public int getPts() {
        return pts;
    }


    //TODO Implementar estados
    // direction = 0 stop, 1 Move, 2 Atack, 3 special
    private int estado;

    public int getEstado() {
        return estado;
    }

    public void stop() {
        this.estado = 0;
        incrementaPos(0);
    }
    public void move() {
        this.estado = 1;
        incrementaPos(1);
    }
    public void atack() {
        this.estado = 2;
        incrementaPos(0);
    }
    public void special() {
        this.estado = 3;
        incrementaPos(0);
    }



    private int posX = 0;
    private int posY = 0;
    private double xSpeed;
    private double ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int Steps = 4;
    private int width;
    private int height;

    private boolean isDash=false;
    public boolean isDash() {
        return isDash;
    }
    public void setDash(boolean isDash) {
        this.isDash = isDash;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Sprite(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;
    }

    private void update() {
        if (posX >= gameView.getWidth() - width - xSpeed || posX + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        posX = posX + (int)xSpeed;
        if (posY >= gameView.getHeight() - height - ySpeed || posY + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        posY = posY + (int)ySpeed;
        if(Steps==4){
            currentFrame = ++currentFrame % BMP_COLUMNS;
            Steps=0;
        }
        Steps++;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(posX, posY, posX + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public int getposX() {
        return posX;
    }

    public void setposX(int x) {
        this.posX = x;
    }

    public int getposY() {
        return posY;
    }

    public void setposY(int y) {
        this.posY = y;
    }
    public int getAncho() {
        return width;
    }
    public int getAlto() {
        return height;
    }
    public double distancia(Sprite g) {
        return Math.hypot(posX-g.posX, posY-g.posY);
    }
    public double distancia(double x, double y) {
        return Math.hypot(posX-x, posY-y);
    }

    public void SetDir(Sprite g) {
        this.setxSpeed((g.posX - posX) / 100);
        this.setySpeed((g.posY - posY) / 100);
    }

    public int GetDir(Sprite g) {
        if(g.posX-posX>0)
        {
            return(1);
        }else{
            return(0);
        }
    }

    public void incrementaPos(double factor){

        posX+=xSpeed;

        if(posX<=-width) {posX=-height;}
        if(posX>gameView.getWidth()) {posX=gameView.getWidth();}

        posY+=ySpeed * factor;

        if(posY<=-width) {posY=-height;}
        if(posY>gameView.getHeight()) {posY=gameView.getHeight();}

    }

    public boolean isCollition(float x2, float y2) {
        return x2 > posX && x2 < posX + width && y2 > posY && y2 < posY + height;
    }

    public void Attack(float x, float y) {
    }

    public void MoveTo(float x, float y) {
        this.setxSpeed((x - posX) / 100);
        this.setySpeed((y - posY) / 100);

    }
}