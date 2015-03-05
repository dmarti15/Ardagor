package danimartin.com.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by d.martin on 08/01/2015.
 */
public class wizard extends Player {
    private double modSpeed=0.5;
    private boolean isDash=false;
    private GameView gameView;
    private String clase= "Wizard";
    public String getClase() {
        return clase;
    }


    public wizard(GameView gameView) {
        super(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.maga));
        this.gameView=gameView;
    }

    public void Attack(float x, float y){
        gameView.addTempSprite(new TempSprite(gameView, x, y, 1));
    }
}


