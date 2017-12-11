package com.example.nacho.manna.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.adapter.AyudaAdapter;


public class AyudaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        //ActionBar---------------------------------------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_ayuda));
      //  actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        //------------------------------------------------------------------------------------------

        //--Pestañas--------------------------------------------------------------------------------
        AyudaAdapter ayudaAdapter = new AyudaAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_activity_ayuda);
        viewPager.setAdapter(ayudaAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_app_bar_ayuda);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(15);


        //------------------------------------------------------------------------------------------
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.fab_ayuda);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_atras),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    //Este metodo crea los menús
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ir_atras, Menu.NONE, R.string.string_ir_atras)
                .setIcon(R.drawable.ic_ir_atras)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.integer.indice_icono_ir_atras) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.string_ir_atras),
                    Toast.LENGTH_SHORT).show();
            finish();
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
