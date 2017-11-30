package com.example.nacho.mannadrawe.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.fragment.VerOrdenesFragmentList;

public class VerOrdenesActivity extends AppCompatActivity {
    int RETARDO = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ordenes);

        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_ver));
        //----------------------------
        VerOrdenesFragmentList fragment = new VerOrdenesFragmentList();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragmeLayout_activity_ver_ordenes, fragment);
        transaction.commit();

        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_activity_ver_ordenes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_generar_ordenes),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CrearOrdenActivity.class);
                startActivity(intent);


            }
        });

    }


    //Menús-----------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ir_generar_orden, Menu.NONE, R.string.string_ir_generar_ordenes)
                .setIcon(R.drawable.ic_mantenimiento2)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.integer.indice_icono_ayuda:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ayuda),
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), AyudaActivity.class);
                startActivity(intent);

            case R.integer.indice_icono_ir_generar_orden:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_generar_ordenes),
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), CrearOrdenActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
