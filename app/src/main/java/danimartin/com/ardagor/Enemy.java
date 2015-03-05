package danimartin.com.ardagor;

import android.graphics.Bitmap;

/**
 * Created by d.martin on 12/12/2014.
 */


public class Enemy extends Sprite{
    int BMP_ROWS=4;

    private GameView gameView;

    private int pts;
    public int getPts() {
        return pts;
    }

    public Enemy(GameView gameView, Bitmap bmp) {
        super(gameView, bmp);
        this.gameView=gameView;
        pts=1;
    }

    public void Actua(int accion){
        switch(accion) {
            case 1:
                //Moverse
                incrementaPos(1);
                break;
            case 2:
                //Atacar
                incrementaPos(1);
                break;
            case 3:
                //Defenderse
                incrementaPos(1);
                break;
            default:
                incrementaPos(1);
        }
    }
    public void Attack(float x, float y){
        gameView.addTempSprite(new TempSprite(gameView, x, y, 1));
    }
    public void MoveTo(float x, float y) {
        this.setxSpeed((x - gameView.PJ.getposX()) / 100);
        this.setySpeed((y - gameView.PJ.getposY()) / 100);
    }
}