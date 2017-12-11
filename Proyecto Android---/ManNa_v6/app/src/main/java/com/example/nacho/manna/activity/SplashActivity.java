package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;

import com.example.nacho.manna.R;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.sync.Sincronizacion;

public class SplashActivity extends AppCompatActivity {
    Activity contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //-------------------------------------
        contexto = this;

        Sincronizacion.forzarSincronizacion(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los  1.6segundos, pasamos a la actividad menú
                //con los datos cargados
                Intent intent = new Intent(contexto, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constantes.DURACION_SPLASH );
    }


}
