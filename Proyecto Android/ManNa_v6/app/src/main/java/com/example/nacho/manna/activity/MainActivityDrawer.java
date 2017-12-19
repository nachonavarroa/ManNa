package com.example.nacho.manna.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.adapter.DrawerAdapter;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.sync.Sincronizacion;


public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int MY_PERMISSIONS_REQUEST = 1;
    boolean permisoAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.mipmap.ic_launcher);
        permisoAdministrador= Utilidades.permisoAdministrador(getApplicationContext());

        //FloatingButton----------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_app_bar_drawe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearOrdenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


            }
        });

        //DrawerLayout------------------------------------------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //View navHeaderDrawer ---------------------------------------------------------------------

        View navHeaderDrawer = navigationView.getHeaderView(0);
        TextView textViewUsuario = navHeaderDrawer.findViewById(R.id.textView_nav_header_drawer_usuario);

        TextView nammeApp = navHeaderDrawer.findViewById(R.id.textView_nav_header_drawer_app_name);

        nammeApp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlanco));
        textViewUsuario.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlanco));

        if (permisoAdministrador) {
            textViewUsuario.setText("Administrador: " + nombreUsuario());
            textViewUsuario.setTextColor(getResources().getColor(R.color.colorAzul));

        } else {
            textViewUsuario.setText(getResources().getString(R.string.string_usuario)
                    .substring(2, 10) + " " + nombreUsuario());


        }

        //------------------------------------------------------------------------------------------
        final DrawerAdapter drawerAdapter = new DrawerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_activity_drawer);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_layout_app_bar_drawer);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(15);
        if (permisoAdministrador) {

            ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle(Html.fromHtml("<font color='#0000ff'>Administrador</font>"));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorFondo));
        }


        viewPager.setAdapter(drawerAdapter);


    }

    //-Pide permisos--------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Menús-----------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_sincronizacion, Menu.NONE, R.string.string_sincronizacion)
                .setIcon(R.drawable.ic_actualizar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_oup_app, Menu.NONE, R.string.out_app)
                .setIcon(R.drawable.ic_boton_apagado)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.integer.indice_icono_ayuda:
                Toast.makeText(this, R.string.string_ayuda, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AyudaActivity.class);
                startActivity(intent);
                break;

            case R.integer.indice_icono_sincronizacion:
                Sincronizacion.forzarSincronizacion(getApplicationContext());

                break;

            case R.integer.indice_icono_oup_app:
                preguntarSailr();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //--------------------------------------------------------------------------------------------------
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_generar_orden) {

            Intent intent = new Intent(getApplicationContext(), CrearOrdenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_ver_ordenes) {

            Intent intent = new Intent(getApplicationContext(), VerOrdenesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == R.id.nav_ver_empleados) {

            Intent intent = new Intent(getApplicationContext(), VerUsuariosActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_autor) {

            Intent intent = new Intent(getApplicationContext(), AutorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_ayuda) {

            Intent intent = new Intent(getApplicationContext(), AyudaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_generar_empleado) {

            if (permisoAdministrador) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_generar_usuario),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CrearUsuarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }

        } else if (id == R.id.nav_salir){
            preguntarSailr();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public String nombreUsuario() {
               String nombreUsuario = AppController.getInstance().getNombre();
        return nombreUsuario;
    }
    private void preguntarSailr() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Salir");
        dialog.setMessage("¿ Desea salir de la aplicacion ?");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utilidades.deleteCache(getApplicationContext());
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        dialog.show();
    }
}
