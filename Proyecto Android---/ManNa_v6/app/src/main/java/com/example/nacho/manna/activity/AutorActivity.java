package com.example.nacho.manna.activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nacho.manna.R;

public class AutorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor);
        //ActionBar----------------------------
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_autor));
        //----------------------------

      }    //Este metodo crea los menús
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE,R.integer.indice_icono_ayuda,Menu.NONE,R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE,R.integer.indice_icono_ir_atras,Menu.NONE,R.string.string_ir_atras)
                .setIcon(R.drawable.ic_ir_atras)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.integer.indice_icono_ir_atras:
                Toast.makeText (getApplicationContext (),
                        getResources ().getString (R.string.string_ir_atras),
                        Toast.LENGTH_LONG).show ();
                finish ();
                break;
            case R.integer.indice_icono_ayuda:
                Toast.makeText (getApplicationContext (),
                        getResources ().getString (R.string.string_ayuda),
                        Toast.LENGTH_SHORT).show ();
                Intent intent = new Intent (getApplicationContext (),AyudaActivity.class);
                startActivity (intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
