package danimartin.com.ardagor;

/**
 * Created by d.martin on 04/03/2015.
 */


    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Matrix;
    import android.graphics.Rect;
    import android.util.Log;
    import java.util.List;


    public class Arrow extends Projectile{


        private float x;
        private float y;
        private float angulo, anguloFix;
        private float ladoX, ladoY;
        private double ladoH;
        private double xSpeed;
        private double ySpeed;
        private double projSpeed;
        private Bitmap bmp;
        private GameView gameView;
        private int life = 15;
        private int currentFrame=0;
        private int Steps=0;
        private int BMP_COLUMNS;
        boolean damaging=true;
        Matrix matrix = new Matrix();

        private int width;
        private int height;
        private List<TempSprite> temps;
        private List<Projectile> Projectiles;


        public Arrow(GameView gameView, float fX, float fY, float tX,float tY,int tipoSprite) {
            super(gameView, fX, fY, tX, tY, 2);
            this.gameView = gameView;
            this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.arrow);
            BMP_COLUMNS=1;
            projSpeed=25;
            anguloFix=45;
            this.width = bmp.getWidth() / BMP_COLUMNS;
            this.height = bmp.getHeight();
            this.x = fX;
            this.y = fY;
            setAngulo(tX, fX, tY, fY);
        }


        public void onDraw(Canvas canvas) {
            update();


            int srcX = currentFrame * width;
            int srcY = 0;
            int px = this.width/2;
            int py = this.height/2;
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect((int)x-px, (int)y-py, (int)x+px , (int)y+py);
            //canvas.rotate(angulo-anguloFix);
            //canvas.drawBitmap(bmp, src, dst, null);
            matrix.reset();
            matrix.postTranslate(-bmp.getWidth()/2, -bmp.getHeight()/2);
            matrix.postRotate(angulo+anguloFix);
            Bitmap frame= Bitmap.createBitmap(bmp,srcX, srcY, srcX + width, srcY + height);
            //bmp, src, dst, null);
            matrix.postTranslate(x+px, y+py);
            canvas.drawBitmap(frame, matrix, null);
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
        public void setX(float x) {
            this.x = x;
        }
        public void setY(float y) {
            this.y = y;
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

    /*
        if(toX-fromX>0 && toY-fromY<0) this.angulo=360-(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
    	if(toX-fromX<0 && toY-fromY>0) this.angulo=180+(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
    	if(toX-fromX<0 && toY-fromY<0) this.angulo=180+(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
    	if(toX-fromX>0 && toY-fromY>0) this.angulo=360-(int) Math.toDegrees(Math.asin((fromY-toY)/Math.hypot(fromX-toX, fromY-toY)));
     */

        public void setAngulo(float toX, float fromX, float toY ,float fromY){
            //Log.d("Flecha", "fromX: " + fromX + " fromY: " + fromY + " toX: " + toX + " toY "+toY);
            ladoX=Math.abs(fromX-toX);
            ladoY=Math.abs(fromY-toY);
            ladoH=Math.hypot(ladoX, ladoY);
            if(toX-fromX>0 && toY-fromY<0)
            {
                this.angulo=360-(int) Math.toDegrees(Math.asin(ladoY/ladoH));
                this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
                this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
                //this.angulo=360-this.angulo;
                Log.d("Cuadrante1", "fromX: " + fromX + " fromY: " + fromY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
            }
            if(toX-fromX<0 && toY-fromY>0)
            {
                this.angulo=180-(int) Math.toDegrees(Math.asin(ladoY/ladoH));
                this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
                this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
                //this.angulo=180+this.angulo;
                Log.d("Cuadrante2", "fromX: " + fromX + " fromY: " + fromY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
            }
            if(toX-fromX<0 && toY-fromY<0)
            {
                this.angulo=180+(int) Math.toDegrees(Math.asin(ladoY/ladoH));
                this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
                this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
                //this.angulo=180-this.angulo;
                Log.d("Cuadrante3","fromX: " + fromX + " fromY: " + fromY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
            }
            if(toX-fromX>0 && toY-fromY>0)
            {
                this.angulo=(int) Math.toDegrees(Math.asin(ladoY/ladoH));
                this.xSpeed=(Math.cos(Math.toRadians(this.angulo)))*projSpeed ;
                this.ySpeed=(Math.sin(Math.toRadians(this.angulo)))*projSpeed ;
                //this.angulo=360-this.angulo;
                Log.d("Cuadrante4","fromX: " + fromX + " fromY: " + fromY + " AnguloFinal "+angulo + " Xspeed: " + xSpeed + " Yspeed: " + ySpeed );
            }
        }
    }

