package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.sync.Sincronizacion;

public class EditUsuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Activity contexto;
    EditText editTextNombreEmpleado;
    EditText editTextCodigoEmpleado;
    RadioGroup radioGroupAdmin;
    Usuario datosEmpleado;
    Usuario datosEmpleadoEncontrado;
    Boolean validadarOk;
    String admin = "No";
    Intent intent;
    int empleadoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);


        contexto = this;
        editTextNombreEmpleado = (EditText) findViewById(R.id.editTextEditarEmpleadoNombre);
        editTextCodigoEmpleado = (EditText) findViewById(R.id.editTextEditarEmpleadCodigo);
        radioGroupAdmin = (RadioGroup) findViewById(R.id.radioGroupEditarAdmin);


        //--ActionBar-----------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_editar_empleado));
        actionBar.setHomeButtonEnabled(false);

        //------------------------------------------------
        empleadoId = this.getIntent().getExtras().getInt(Contrato.Usuario._ID);
        datosEmpleadoEncontrado = CrudUsuarios.readRecord(getContentResolver(), empleadoId);

        editTextNombreEmpleado.setText(datosEmpleadoEncontrado.getNombre());
        editTextCodigoEmpleado.setText(String.valueOf(datosEmpleadoEncontrado.getCodigo()));

        adminRadioButton(datosEmpleadoEncontrado);
        radioGroupAdmin();

        //FloatingButton ---------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_editar_empleado);
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
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonEditarAdminSi:
                        admin = (String) getResources().getText(R.string.string_si);

                        break;
                    case R.id.radioButtonEditarAdminNo:
                        admin = (String) getResources().getText(R.string.string_no);

                        break;
                }

            }
        });


    }

    private void adminRadioButton(Usuario empleado) {
        String adminExtraido = empleado.getAdmin();

        String adminSi = getResources().getString(R.string.string_si);
        String adminNo = getResources().getString(R.string.string_no);

        if (adminExtraido.equals(adminSi)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEditarAdminSi);
            radioButton.setChecked(true);
            admin = adminExtraido;

        } else if (adminExtraido.equals(adminNo)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEditarAdminNo);
            radioButton.setChecked(true);
            admin = adminExtraido;
        }

    }

    private void guardar() {
        String nombreEmpleado = editTextNombreEmpleado.getText().toString();
        String codigoEmpleadoString = editTextCodigoEmpleado.getText().toString();

        if (validarCampos(nombreEmpleado, codigoEmpleadoString)) {

            int codigoEmpleadoInt = Integer.parseInt(codigoEmpleadoString);
            Usuario empleado = new Usuario();
            empleado.setId(empleadoId);
            empleado.setNombre(nombreEmpleado);
            empleado.setCodigo(codigoEmpleadoInt);
            empleado.setAdmin(admin);

            AppController datosLogin = (AppController) getApplication();
            int codUsuarioLogin = CrudUsuarios.login(getContentResolver(),
                    String.valueOf(datosLogin.getCodigo()), datosLogin.getNombre()).getCodigo();
            int codUsuarioEditado = CrudUsuarios.readRecord(getContentResolver(),
                    empleadoId).getCodigo();

            if (codUsuarioLogin == codUsuarioEditado) {
                datosLogin.setCodigo(empleado.getCodigo());
                datosLogin.setNombre(nombreEmpleado);

            }
            if(datosEmpleadoEncontrado.getCodigo() == codigoEmpleadoInt){
                CrudUsuarios.updateUsuarioSincodigoConBitacora(getContentResolver(),
                        empleado,contexto);

                Intent intent = new Intent(contexto, MainActivityDrawer.class);
                intent.putExtra("datosDeEmpleado", datosEmpleado);
                startActivity(intent);
                finish();

            }
            else if (!comprobarUsuario(codigoEmpleadoString)) {
                CrudUsuarios.updateUsuarioConBitacora(getContentResolver(), empleado,contexto);
                Intent intent = new Intent(contexto, MainActivityDrawer.class);
                intent.putExtra("datosDeEmpleado", datosEmpleado);
                startActivity(intent);
                finish();
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

    //Menús-----------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ir_atras, Menu.NONE, R.string.string_ir_atras)
                .setIcon(R.drawable.ic_ir_atras)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_guardar_usuario_editado, Menu.NONE, R.string.string_guardar)
                .setIcon(R.drawable.ic_save)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.integer.indice_icono_ir_atras:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ir_atras),
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                startActivity(intent);
                finish();
                break;
            case R.integer.indice_icono_ayuda:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_ayuda),
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), AyudaActivity.class);
                startActivity(intent);
                break;
            case R.integer.indice_icono_guardar_usuario_editado:
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