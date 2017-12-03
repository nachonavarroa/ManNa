package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.nacho.manna.R;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.aplication.AppController;

public class CrearOrdenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Activity contexto;
    String prioridad = "";
    String sintoma0 = "";
    String sintoma1 = "";
    String sintoma2 = "";
    Boolean siPrioridad = false;
    Boolean siSintoma0 = false;
    Boolean siSintoma1 = false;
    Boolean siSintoma2 = false;
    Usuario datosEmpleado;
    AppController datosLogin;
    RadioGroup radioGroupTipo;
    CheckBox checkBoxSintoma0;
    CheckBox checkBoxSintoma1;
    CheckBox checkBoxSintoma2;
    EditText editTextSintoma2;
    Boolean validadarOk;
    Intent intent;
    int xOffset;
    int yOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_de_orden);

        xOffset = 150;
        yOffset = 660;

        contexto = this;
        intent = this.getIntent();
        radioGroupTipo = (RadioGroup) findViewById(R.id.radioGroupPrioridad);
        checkBoxSintoma0 = (CheckBox) findViewById(R.id.checkBoxSintoma0);
        checkBoxSintoma1 = (CheckBox) findViewById(R.id.checkBoxSintoma1);
        checkBoxSintoma2 = (CheckBox) findViewById(R.id.checkBoxSintoma2);
        editTextSintoma2 = (EditText) findViewById(R.id.editTextSintoma2);

        //----ActionBar---------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_titulo_crear_orden
        ));
        //---------------------------------------------------

        radioGroupPrioridad();
        checkBoxSintoma();

        //FloatingButton----------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_tipo_de_orden_siguiente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irSiguiente();
            }
        });

    }
    //----------------------------------------------------------------------------------------------

    private void datosLogin(){
        datosLogin = (AppController) getApplication();
        datosEmpleado  = new Usuario();
        datosEmpleado.setNombre(datosLogin.getNombre());
        datosEmpleado.setCodigo(datosLogin.getCodigo());


    }
    private void irSiguiente() {

        if (siPrioridad && (siSintoma0 || siSintoma1 || siSintoma2)&& validarEditTextSintoma2()) {
            sintoma2 = editTextSintoma2.getText().toString();

            String estado = estadoElegido();
            datosLogin();
            Intent intent = new Intent(contexto, DescripcionOrdenActivity.class);

            intent.putExtra("datosEmpleado", datosEmpleado);

            intent.putExtra("ordenDeTrabajo", setPrioridadEstadoOrdenDeTrabajo
                    (prioridad, estado));

            if(!TextUtils.isEmpty(editTextSintoma2.getText().toString())&& !siSintoma2){
                Toast.makeText(contexto,"Sintoma otros no check",
                        Toast.LENGTH_SHORT).show();
                checkBoxSintoma2.setError("");
                editTextSintoma2.setTextColor(ContextCompat.getColor(contexto, R.color.colorRojo));
                return;
            }

            startActivity(intent);
            finish();

        } else {
          Toast.makeText(contexto, getResources().getText(R.string.elegir_tipo_ciudad),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarEditTextSintoma2() {
        validadarOk = true;
        editTextSintoma2.setError(null);
        String sintoma2 =  editTextSintoma2.getText().toString();
        if (TextUtils.isEmpty(sintoma2) && siSintoma2) {
            editTextSintoma2.setError(getString(R.string.campo_vacio));
            editTextSintoma2.requestFocus();
            validadarOk = false;
            return validadarOk;
        }
         return validadarOk;
    }

    private void checkBoxSintoma() {
        checkBoxSintoma0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sintoma = (String) getResources().getText(R.string.sintoma_parado);
                siSintoma0 = false;
                if (checkBoxSintoma0.isChecked()) {
                    siSintoma0 = true;
                    sintoma0 = sintoma;
                    mensajeSeleccionado(sintoma);
                } else {
                    siSintoma0 = false;
                    sintoma0 = "";
                    mensajeDeseleccionado(sintoma);
                }
            }
        });

        checkBoxSintoma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siSintoma1 = false;
                String sintoma = (String) getResources().getText(R.string.sintoma_rotura);
                if (checkBoxSintoma1.isChecked()) {
                    siSintoma1 = true;
                    sintoma1 = sintoma;
                    mensajeSeleccionado(sintoma);
                } else {
                    siSintoma1 = false;
                    sintoma1 = "";
                    mensajeDeseleccionado(sintoma);
                }

            }
        });

        checkBoxSintoma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siSintoma2 = false;
                editTextSintoma2.setError(null);
                checkBoxSintoma2.setError(null);
                editTextSintoma2.setTextColor(ContextCompat.getColor(contexto, R.color.colorNegro));
                String sintoma = editTextSintoma2.getText().toString() ;
                if (checkBoxSintoma2.isChecked()) {
                    sintoma2 = sintoma;
                    siSintoma2 = true;
                    mensajeSeleccionado(sintoma);
                } else {
                    siSintoma2 = false;
                    sintoma2 = "";
                    mensajeDeseleccionado(sintoma);
                }
            }
        });
    }

    private void radioGroupPrioridad() {
        radioGroupTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {

                switch (checkId) {
                    case R.id.radioButtonPrioridad0:
                        prioridad = (String) getResources().getText(R.string.prioridad_muy_alta);
                        siPrioridad = true;
                        break;
                    case R.id.radioButtonPrioridad1:
                        prioridad = (String) getResources().getText(R.string.prioridad_alta);
                        siPrioridad = true;
                        break;
                    case R.id.radioButtonPrioridad2:
                        prioridad = (String) getResources().getText(R.string.prioridad_media);
                        siPrioridad = true;
                        break;
                    case R.id.radioButtonPrioridad3:
                        prioridad = (String) getResources().getText(R.string.prioridad_baja);
                        siPrioridad = true;
                        break;
                }
                mensajeSeleccionado(prioridad);

            }
        });


    }

    private OrdenDeTrabajo setPrioridadEstadoOrdenDeTrabajo(String prioridad, String estado) {
        OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
        ordenDeTrabajo.setPrioridad(prioridad);
        ordenDeTrabajo.setSintoma(estado);

        return ordenDeTrabajo;
    }

    private String estadoElegido() {
        String estadoElegido = null;
        String coma1 = ", ";
        String coma2 = ", ";
        if (sintoma0.isEmpty()) coma1 = "";
        if (sintoma1.isEmpty()) coma1 = "";
        if (sintoma1.isEmpty()) coma2 = "";
        if (sintoma2.isEmpty()) coma2 = "";
        if (!sintoma0.isEmpty() && !sintoma2.isEmpty()) coma2 = ", ";
        estadoElegido = sintoma0 + coma1 + sintoma1 + coma2 + sintoma2;
        return estadoElegido;
    }

    private void mensajeSeleccionado(String estado) {
        Toast toast = Toast.makeText(contexto, estado + " " + getText(R.string.seleccionado),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, xOffset, yOffset);
        toast.show();
    }

    private void mensajeDeseleccionado(String estado) {
        Toast toast = Toast.makeText(contexto, estado + " " + getText(R.string.deseleccionado),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, xOffset, yOffset);
        toast.show();
    }

    //Menús-----------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ir_siguiente, Menu.NONE, R.string.string_siguiente)
                .setIcon(R.drawable.ic_siguiente)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

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
            case R.integer.indice_icono_ir_siguiente:
                irSiguiente();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
