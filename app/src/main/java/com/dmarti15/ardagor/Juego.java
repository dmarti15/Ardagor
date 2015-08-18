package com.dmarti15.ardagor;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by d.martin on 17/12/2014.
 */
public class Juego extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this, bundle.getInt("tipoPJ")));
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    }
}
