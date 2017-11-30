package com.example.nacho.mannadrawe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.crud.CrudUsuarios;
import com.example.nacho.mannadrawe.fragment.VerUsuariosFragmentList;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.aplication.AppController;


public class VerUsuariosActivity extends AppCompatActivity {
    boolean permisoAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_ver_usuario);
        //ActionBar----------------------------
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_ver_empleados));
        //----------------------------
        VerUsuariosFragmentList fragment  = new VerUsuariosFragmentList();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragmeLayout_activity_ver_empleados,fragment);
        transaction.commit();


        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_activity_ver_empleados);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permisoAdministrador()) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.string_ir_generar_usuario),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CrearUsuarioActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    private boolean permisoAdministrador() {
        permisoAdministrador = false;
        AppController empleado = (AppController) getApplication();
        Usuario empleadoAdmin = CrudUsuarios.login(getContentResolver(),
                String.valueOf(empleado.getCodigo()), empleado.getNombre());
        String admin = empleadoAdmin.getAdmin();
        if (admin.equals(getResources().getString(R.string.string_si))) {
            permisoAdministrador = true;

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_permiso_admin),
                    Toast.LENGTH_SHORT).show();
            permisoAdministrador = false;

        }
        return permisoAdministrador;
    }

    //Menús-----------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE,R.integer.indice_icono_ayuda,Menu.NONE,R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


        menu.add(Menu.NONE,R.integer.indice_icono_crear_usuario,Menu.NONE,R.string.subtitulo_crear_empleados)
                .setIcon(R.drawable.ic_add_people)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.integer.indice_icono_ayuda:
                Toast.makeText (getApplicationContext (),
                        getResources ().getString (R.string.string_ayuda),
                        Toast.LENGTH_SHORT).show ();
                intent = new Intent (getApplicationContext (),AyudaActivity.class);
                startActivity (intent);

             case R.integer.indice_icono_crear_usuario:
                 if(permisoAdministrador()){
                Toast.makeText (getApplicationContext (),
                        getResources ().getString (R.string.string_ir_generar_usuario),
                        Toast.LENGTH_SHORT).show ();
                intent = new Intent (getApplicationContext (),CrearUsuarioActivity.class);
                startActivity (intent);
                 }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
