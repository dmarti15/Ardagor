package com.dmarti15.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by d.martin on 02/03/2015.
 */
public class Escenario {

    private Bitmap background,scaled;
    private int x,y;

    public Escenario(GameView gameView, int tipoMapa, int w, int h) {
        switch(tipoMapa)
        {
            case(1):
                this.background = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pixel_art_bg);
                this.x = 0;
                this.y = 0;
                break;
            default:
                this.background = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bg_grass);
                this.x = 0;
                this.y = 0;
                break;
        }
        float scaleH = (float)background.getHeight()/(float)h;
        float scaleW = (float)background.getWidth()/(float)w;
        int newWidth = Math.round(background.getWidth()/scaleW);
        int newHeight = Math.round(background.getHeight()/scaleH);
        scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
    }

    public void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.GRAY);
        canvas.drawBitmap(scaled, 0, 0, null);
    }
}
