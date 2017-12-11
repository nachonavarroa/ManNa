package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNombreEmpleado;
    private EditText editTextCodigoEmpleado;
    private AppController appController;
    private Boolean validadarOk;
    private Intent intent;
    private String nombre;
    private String codigoEmpleadoString;
    private int codigoEmpleadoInt;
    private Usuario usuarioRegistrado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Utilidades.pedirPermisos(this);

        editTextNombreEmpleado = (EditText) findViewById(R.id.editText_nombre_empleado_login);
        editTextCodigoEmpleado = (EditText) findViewById(R.id.editText_codigo_empleado_login);

        //--ActionBar-----------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtititulo_login));
        actionBar.setHomeButtonEnabled(true);

        //FloatingButton----------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_datos_empleado_siguiente_login);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siguiente();
            }
        });
    }

    //Verificar permisos----------------------------------------------------------------------------


    private void siguiente() {
        nombre = editTextNombreEmpleado.getText().toString();
        codigoEmpleadoString = editTextCodigoEmpleado.getText().toString();
        codigoEmpleadoInt = Integer.parseInt(codigoEmpleadoString);
        validarEmpleado(nombre, codigoEmpleadoString);
    }

    private void controlSesion(String nombre, int codigoEmpleado) {
        appController = (AppController) getApplication();
        appController.setNombre(nombre);
        appController.setCodigo(codigoEmpleado);

    }


    private void validarEmpleado(String nombreEmpleado, String codigoEmpleadoString) {
        if (validarCampos(nombreEmpleado, codigoEmpleadoString)) {
            try {
                usuarioRegistrado = CrudUsuarios.login(getContentResolver()
                        , codigoEmpleadoString, nombreEmpleado);

                if (usuarioRegistrado.getCodigo() == codigoEmpleadoInt) {
                    controlSesion(nombre, codigoEmpleadoInt);
                    intent = new Intent(this, MainActivityDrawer.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                int xOffset = 75;
                int yOffset = 350;

                Toast toast = Toast.makeText(this, getResources().
                                getText(R.string.toast_error_login),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, xOffset, yOffset);
                toast.show();


            }
        }

    }

    //------------------------------------------------
    private boolean validarCampos(String nombreEmpleado, String codigoEmpleadoString) {
        validadarOk = true;
        editTextNombreEmpleado.setError(null);
        editTextCodigoEmpleado.setError(null);

        if (TextUtils.isEmpty(nombreEmpleado)) {
            editTextNombreEmpleado.setError(getString(R.string.campo_vacio));
            editTextNombreEmpleado.requestFocus();
            validadarOk = false;
            return validadarOk;
        }
        if (TextUtils.isEmpty(codigoEmpleadoString)) {
            editTextCodigoEmpleado.setError(getString(R.string.campo_vacio));
            editTextCodigoEmpleado.requestFocus();
            validadarOk = false;
            return validadarOk;
        }
        String codigoString = editTextCodigoEmpleado.getText().toString();
        int codigo = Integer.parseInt(codigoString);
        if (codigo == 0) {
            editTextCodigoEmpleado.setError(getString(R.string.valores_no_validos));
            editTextCodigoEmpleado.requestFocus();
            validadarOk = false;
            return validadarOk;

        }
        return validadarOk;
    }

    //Menús-----------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ir_siguiente,
                Menu.NONE, R.string.string_siguiente)
                .setIcon(R.drawable.ic_ok)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.integer.indice_icono_ir_siguiente:
                siguiente();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
