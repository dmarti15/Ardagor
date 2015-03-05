package danimartin.com.ardagor;

import android.graphics.Bitmap;

/**
 * Created by d.martin on 12/12/2014.
 */
public class Player extends Sprite{
    private double modSpeed=0.5;
    private boolean isDash=false;
    private String clase= "";
    public String getClase() {
        return clase;
    }


    public Player(GameView gameView, Bitmap bmp) {
        super(gameView, bmp);
    }


    public double getModSpeed() {
        return modSpeed;
    }
    public void setModSpeed(double modSpeed) {
        this.modSpeed = modSpeed;
    }

    public void Atack(float x, float y){
        super.Attack(x,y);

    }


    public void MoveTo(float x, float y) {
        super.MoveTo(x,y);
    }
}
