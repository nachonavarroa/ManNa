package com.example.nacho.mannadrawe.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.crud.CrudOrdenes;
import com.example.nacho.mannadrawe.sync.Sincronizacion;

public class SplashActivity extends AppCompatActivity {
    Activity contexto;


    private final int DURACION_SPLASH = 1600; // 1.6segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //-------------------------------------
        contexto = this;
                new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 2 segundos, pasamos a la actividad menú
                Intent intent = new Intent(contexto, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);
    }


}
