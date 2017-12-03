package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.sync.Sincronizacion;

public class CrearUsuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Activity contexto;
    EditText editTextNombreEmpleado;
    EditText editTextCodigoEmpleado;
    RadioGroup radioGroupAdmin;
    Usuario datosEmpleado;
    Boolean validadarOk;
    String admin = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        contexto = this;
        editTextNombreEmpleado = (EditText) findViewById(R.id.editTextCrearEmpleadoNombre);
        editTextCodigoEmpleado = (EditText) findViewById(R.id.editTextCrearEmpleadoCodigo);
        radioGroupAdmin = (RadioGroup) findViewById(R.id.radioGroupAdmin);

        //--ActionBar-----------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_crear_empleados));
        actionBar.setHomeButtonEnabled(true);
        //------------------------------------------------

        RadioButton radioButtonNo = (RadioButton) findViewById(R.id.radioButtonAdminNo);
        radioButtonNo.setChecked(true);

        radioGroupAdmin();

        //FloatingButton ---------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_crear_empleado);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
    }

    //---------------------------------------------------------------------------------------------

    private void radioGroupAdmin() {
        radioGroupAdmin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {

                switch (checkId) {
                    case R.id.radioButtonAdminSi:
                        admin = (String) getResources().getText(R.string.string_si);
                        break;

                    case R.id.radioButtonAdminNo:
                        admin = (String) getResources().getText(R.string.string_no);
                        break;
                }
            }
        });


    }

    private void guardar() {

        String nombreEmpleado = editTextNombreEmpleado.getText().toString();
        String codigoEmpleadoString = editTextCodigoEmpleado.getText().toString();

        if (validarCampos(nombreEmpleado, codigoEmpleadoString)) {
            int codigoEmpleadoInt = Integer.parseInt(codigoEmpleadoString);

            if (!comprobarUsuario(codigoEmpleadoString)) {
                Usuario empleado = new Usuario();
                empleado.setNombre(nombreEmpleado);
                empleado.setCodigo(codigoEmpleadoInt);
                empleado.setAdmin(admin);
                int numeroAleatorio = (int) (Math.random() * 99999999 + 1);
                empleado.setId(numeroAleatorio);
                CrudUsuarios.insertUsuarioConBitacora(getContentResolver(), empleado,contexto);
                Intent intent = new Intent(contexto, VerUsuariosActivity.class);
                intent.putExtra("datosDeEmpleado", datosEmpleado);
                startActivity(intent);
                finish();
            }
        }

    }

    private boolean comprobarUsuario(String codigo) {
        boolean siEsxisteUsuario = false;
        try {
            Usuario datEmp;
            datEmp = CrudUsuarios.buscar(getContentResolver(), codigo);
            if (String.valueOf(datEmp.getCodigo()).equals(codigo)) {
                siEsxisteUsuario = true;
                Toast.makeText(getApplicationContext()
                        , "El cógigo de usuario ya existe", Toast.LENGTH_SHORT).show();
                editTextCodigoEmpleado.setError("El cógigo de usuario ya existe");

            }
        } catch (Exception e) {
            siEsxisteUsuario = false;
        }
        return siEsxisteUsuario;
    }

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

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ir_atras, Menu.NONE, R.string.string_ir_atras)
                .setIcon(R.drawable.ic_ir_atras)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_guardar_usuario, Menu.NONE, R.string.string_guardar)
                .setIcon(R.drawable.ic_save)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.integer.indice_icono_ayuda:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ayuda),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AyudaActivity.class);
                startActivity(intent);
                break;
            case R.integer.indice_icono_ir_atras:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_atras),
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.integer.indice_icono_guardar_usuario:
                guardar();
                break;
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
