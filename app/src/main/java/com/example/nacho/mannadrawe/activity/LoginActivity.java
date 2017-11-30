package com.example.nacho.mannadrawe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.crud.CrudUsuarios;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.aplication.AppController;

public class LoginActivity extends AppCompatActivity {
    Activity contexto;
    EditText editTextNombreEmpleado;
    EditText editTextCodigoEmpleado;
    Usuario datosEmpleado;
    AppController datosLogin;
    Boolean validadarOk;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificar permisos-----------------------------------------------------------------------------------
        int permissionCheck = ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        }

        contexto = this;
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

    //-----------------

    private void siguiente(){
        String nombreEmpleado = editTextNombreEmpleado.getText().toString();
        String codigoEmpleadoString = editTextCodigoEmpleado.getText().toString();
        validarEmpleado(nombreEmpleado,codigoEmpleadoString);
    }

    private void controlSesion(){
        String nombreEmpleado = editTextNombreEmpleado.getText().toString();
        String codigoEmpleadoString = editTextCodigoEmpleado.getText().toString();
        datosLogin = (AppController) getApplication();
        datosLogin.setNombre(nombreEmpleado);
        datosLogin.setCodigo(Integer.parseInt(codigoEmpleadoString));
        datosLogin.setLoginOk(1);
          }

    private  void validarEmpleado(String nombreEmpleado,String codigoEmpleadoString){
        if (validarCampos(nombreEmpleado, codigoEmpleadoString)) {
            try {
                int codigoEmpleadoInt = Integer.parseInt(codigoEmpleadoString);
                Usuario existeEmpleado = new Usuario();

                existeEmpleado = CrudUsuarios.login(getContentResolver()
                        ,codigoEmpleadoString,nombreEmpleado);

                if ( existeEmpleado.getCodigo() == codigoEmpleadoInt) {

                    controlSesion();
                    intent = new Intent(contexto, MainActivityDrawer.class);
                    startActivity(intent);
                    finish();
                }
            }catch(Exception e){
                int xOffset=75;
                int yOffset=350;

                Toast toast = Toast.makeText(contexto,getResources().
                                getText(R.string.toast_error_login),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, xOffset, yOffset);
                toast.show();


            }
        }


    }
    //---------------------------------------------------------------------------------------------

    private Usuario crearDatosEmpleado
            (String nombreEmpleado, String codigoEmpleadoString) {

        int codigoEmpleado = Integer.parseInt(codigoEmpleadoString);
        datosEmpleado = new Usuario(nombreEmpleado, codigoEmpleado);

        return datosEmpleado;
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
