package danimartin.com.ardagor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;



/**
 * Created by d.martin on 10/12/2014.
 */
public class GameView extends SurfaceView implements OnTouchListener {
    protected Bitmap bmp, scaled, background;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    public Player PJ;
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private List<Projectile> projetiles = new ArrayList<Projectile>();
    private Escenario stage;
    private Context context;

    private double nIncX = 0;
    private double nIncY = 0;

    float mX, mY;
    private long lastClick;
    boolean disparo = false;
    boolean move = false;
    private int puntuacion;

    private int screenWidth;
    private int screenHeight;
    private boolean oneTime = false;
    private boolean oneTimeSensor = false;

    private int WanderingMonster = 0;
    private int MinNumEnemigos = 1; // Enemigos
    private int MaxNumEnemigos = 5; // Enemigos


    public GameView(Context c, int tipoPJ) {
        super(c);
        this.context = c;
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                screenWidth = getWidth();
                screenHeight = getHeight();

                PJ.setposX(screenWidth / 2);
                PJ.setposY(screenHeight / 2);

                //enemies.add(CreateEnemigo(0));
                //enemies.add(CreateEnemigo(1));
                //enemies.add(CreateEnemigo(2));
                //enemies.add(CreateEnemigo(3));
                gameLoopThread.setRunning(true);
                gameLoopThread.start();


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                screenWidth = getWidth();
                screenHeight = getHeight();
            }
        });
        //CreatePJ((int)(Math.round(Math.random()*5)));
        CreatePJ(tipoPJ);
    }


    protected void CreateBackground(int mapa, int w, int h) {
        stage = new Escenario(this, mapa, w, h);
    }

    public void onDraw(Canvas canvas) {
        if (!oneTime) {
            oneTime = true;
            screenWidth = getWidth();
            screenHeight = getHeight();
            puntuacion = 0;

        }

        if (stage == null) CreateBackground(1, screenWidth, screenHeight);
        else stage.onDraw(canvas);


        PJ.onDraw(canvas);
        for (Enemy enemy : enemies) {
            enemy.SetDir(PJ);
            enemy.Actua(1);
            enemy.onDraw(canvas);
            if (enemy.distancia(PJ) < enemy.getAncho() / 2) salir();
        }

        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
            for (int e = enemies.size() - 1; e >= 0; e--) {
                Sprite sprite = enemies.get(e);
                if (sprite.distancia(temps.get(i).getX(), temps.get(i).getY()) < temps.get(i).getWidth() / 2 && temps.get(i).damaging) {
                    enemies.remove(sprite);
                    puntuacion = puntuacion + sprite.getPts();
                }
            }
            if (temps.get(i).getLife() < 1) {
                temps.remove(i);
            }
        }

        for (int i = projetiles.size() - 1; i >= 0; i--) {
            projetiles.get(i).onDraw(canvas);
            //Log.d("Flecha: "+i, "X: " + projetiles.get(i).getX() + " Y: " + projetiles.get(i).getY());
            for (int e = enemies.size() - 1; e >= 0; e--) {
                Sprite sprite = enemies.get(e);
                if (sprite.distancia(projetiles.get(i).getX(), projetiles.get(i).getY()) < projetiles.get(i).getWidth() / 2) {
                    enemies.remove(sprite);
                    projetiles.get(i).setLife(0);
                    puntuacion = puntuacion + sprite.getPts();
                }
            }
            if (projetiles.get(i).getLife() < 1) {
                projetiles.remove(i);
            }
        }

        if (enemies.size() < MinNumEnemigos) {
            enemies.add(CreateEnemigo(Math.round((float) Math.random() * 7)));
        }
        WanderingMonster += 1;
        if (Math.random() * 10000 < WanderingMonster && enemies.size() < MaxNumEnemigos) {
            enemies.add(CreateEnemigo(Math.round((float) Math.random() * 7)));
            WanderingMonster = 0;
        }
    }

    void CreatePJ(int tipo) {
        if (tipo == 7) {
            tipo = (int) Math.random() * 5;
        }
        switch (tipo) {
            case 1:
                PJ = new ninja(this);
                break;
            case 2:
                PJ = new warrior(this);
                break;
            case 3:
                PJ = new archer(this);
                break;
            case 4:
                PJ = new wizard(this);
                break;
            case 5:
                PJ = new vampire(this);
                break;
            default:
                PJ = new warrior(this);
        }

        PJ.setposX(screenWidth / 2);
        PJ.setposY(screenHeight / 2);
    }

    private Enemy CreateEnemigo(int tipo) {
        switch (tipo) {
            case 1:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pirate);
                break;
            case 2:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.knight);
                break;
            case 3:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.succub);
                break;
            case 4:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.witch);
                break;
            case 5:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.vampirelord);
                break;
            case 6:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.redninja);
                break;
            case 7:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.succub);
                break;
            default:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bersk);
        }
        Enemy bad = new Enemy(this, bmp);
        Random r = new Random();
        switch (Math.round((float) Math.random() * 4)) {
            case 0:
                do {
                    bad.setposX(0);
                    bad.setposY((int) Math.round(Math.random() * (screenHeight - bad.getAlto())));
                } while (bad.distancia(PJ) < (screenWidth + screenHeight) / 5);
                break;
            case 1:
                do {
                    bad.setposX(screenWidth - bad.getAncho());
                    bad.setposY((int) Math.round(Math.random() * (screenHeight - bad.getAlto())));
                } while (bad.distancia(PJ) < (screenWidth + screenHeight) / 5);
                break;
            case 2:
                do {
                    bad.setposX((int) Math.round(Math.random() * (screenWidth - bad.getAncho())));
                    bad.setposY(screenHeight - bad.getAlto());
                } while (bad.distancia(PJ) < (screenWidth + screenHeight) / 5);
                break;
            case 3:
                do {
                    bad.setposX((int) Math.round(Math.random() * (screenWidth - bad.getAncho())));
                    bad.setposY(0);
                } while (bad.distancia(PJ) < (screenWidth + screenHeight) / 5);
                break;
            default:
                do {
                    bad.setposX((int) Math.round(Math.random() * (screenWidth - bad.getAncho())));
                    bad.setposY((int) Math.round(Math.random() * (screenHeight - bad.getAlto())));
                } while (bad.distancia(PJ) < (screenWidth + screenHeight) / 5);
                break;
        }
        return (bad);
    }

    public void addTempSprite(TempSprite ts) {
        temps.add(ts);
    }

    public void addProjectile(Projectile proj) {
        projetiles.add(proj);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x, y;
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo = true;
                move = false;
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                disparo = false;
                move = true;
                if (Math.hypot(mX - x, mY - y) < 30) {
                    disparo = true;
                    move = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (disparo) {
                    PJ.Attack(x, y);
                }
                if (move) {
                    PJ.MoveTo(x, y);
                }
                break;
        }
        //return super.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private void salir() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Bundle bundle = new Bundle();
        bundle.putInt("puntuacion", puntuacion);
        bundle.putString("clase", PJ.getClase());
        bundle.putString("fecha", df3.format(c.getTime()));
        Intent intent = new Intent();
        intent.putExtras(bundle);
        //Main.almacen.guardarPuntuacion(puntuacion, PJ.getClase(), System.currentTimeMillis());
        //gameLoopThread.setRunning(false);
        ((Activity) context).setResult(Activity.RESULT_OK, intent);
        ((Activity) context).finish();
    }
}