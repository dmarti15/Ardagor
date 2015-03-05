package danimartin.com.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by d.martin on 08/01/2015.
 */
public class warrior extends Player {
    private double modSpeed=1;
    private boolean isDash=false;
    private String clase= "Warrior";
    public String getClase() {
        return clase;
    }

    public warrior(GameView gameView) {
        super(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.warrior));
    }
}
