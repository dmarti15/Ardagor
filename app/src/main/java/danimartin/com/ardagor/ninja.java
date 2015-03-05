package danimartin.com.ardagor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by d.martin on 08/01/2015.
 */
public class ninja extends Player {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int posX = 0;
    private int posY = 0;
    private double xSpeed;
    private double ySpeed;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int Steps = 4;
    private int width;
    private int height;
    private double modSpeed=3;
    private boolean isDash=false;
    private GameView gameView;
    private String clase= "Ninja";
    public String getClase() {
        return clase;
    }




    public ninja(GameView gameView) {
        super(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ninjarojo));
        this.gameView=gameView;
    }

    public void Attack(float tX, float tY){

        float fX=this.getposX()+(this.width/2);
        float fY=this.getposY()+(this.height/2);
        gameView.addProjectile(new Shuriken(gameView, fX, fY, tX, tY, 2));
    }
}