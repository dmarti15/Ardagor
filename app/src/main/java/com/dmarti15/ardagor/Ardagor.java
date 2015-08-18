package com.dmarti15.ardagor;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class Ardagor extends Activity {
    /** Called when the activity is first created. */
    private Button b1, b2, b3, b4, b5, b6, b7;
    public static ScoreSQLite almacen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        almacen=new ScoreSQLite(this);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 4);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 5);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null, 6);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarPuntuaciones(null);
            }
        });
    }

    public void lanzarJuego(View view, int tipoPJ)
    {
        Intent i = new Intent(this, Juego.class);
        i.putExtra("tipoPJ", tipoPJ);
        startActivityForResult(i, 1234);
    }

    public void lanzarPuntuaciones(View view)
    {
        Intent i = new Intent(this, Puntuaciones.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onActivityResult (int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1234 && resultCode==RESULT_OK && data!=null) {
            int puntuacion = data.getExtras().getInt("puntuacion");
            String clase = data.getExtras().getString("clase");
            // Mejor leerlo desde un Dialog o una nueva actividad AlertDialog.Builder
            almacen.guardarPuntuacion(puntuacion, clase, System.currentTimeMillis());
            //lanzarPuntuaciones(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}