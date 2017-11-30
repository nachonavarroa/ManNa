package com.example.nacho.mannadrawe.activity;

import android.app.Activity;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.pojos.OrdenDeTrabajo;


public class DescripcionOrdenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Activity contexto;
    EditText editTextUbicacion;
    EditText editTextDescripcion;
    TextView textViewNombreEmpleado;
   // TextView textViewCodigoEmpleado;
    TextView textViewPrioridadSeleccionado;
    TextView textViewEstadoselec;
    Usuario datosEmpleados;
    OrdenDeTrabajo ordenDeTrabajo;
    Boolean validadarOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_orden);
        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_descripcion_orden));
        //----------------------------

        contexto = this;
        Intent intent = this.getIntent();
        datosEmpleados = intent.getParcelableExtra("datosEmpleado");
        ordenDeTrabajo = intent.getParcelableExtra("ordenDeTrabajo");

        iciciarTextAndButton();
        verDatos();

        //FloatingButton----------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab_descripcion_de_orden_siguiente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irSiguiente();
            }
        });


    }

    //----------------------------------------------------------------------------------------------
    private void irSiguiente(){

        String ubicacion = editTextUbicacion.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();

        if (validarCampo()) {
            ordenDeTrabajo.setUbicacion(ubicacion);
            ordenDeTrabajo.setDescripcion(descripcion);
            Intent intent = new Intent(contexto, OrdenGeneradaActivity.class);
            intent.putExtra("datosEmplea", datosEmpleados);
            intent.putExtra("ordenDeTrabajo", ordenDeTrabajo);
            startActivity(intent);
            finish();
        }


    }

    private void iciciarTextAndButton() {
       // textViewCodigoEmpleado = (TextView) findViewById(R.id.textViewCodigoEmpleadoDEscripcionOrden);
        textViewNombreEmpleado = (TextView) findViewById(R.id.textViewNombreEmpleadoDescripcionOrden);
        textViewPrioridadSeleccionado = (TextView) findViewById(R.id.textViePrioridadSelec);
        textViewEstadoselec = (TextView) findViewById(R.id.textViewEstadoSelec);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        editTextUbicacion = (EditText) findViewById(R.id.editTextUbicacion);

    }

    private void verDatos() {
       // Log.i("nachito","llego aqui");
        String nombEmpleado = getResources().getText(R.string.titulo3)
                + "  " + datosEmpleados.getNombre();

       // String codigoEmpleado = getResources().getText(R.string.titulo4) + "  " + "****";
        String prioridadSeleccionado = getResources().getText(R.string.titulo5) + " " + ordenDeTrabajo.getPrioridad();
        String estadoSelecionada = getResources().getText(R.string.titulo6) + " " + ordenDeTrabajo.getSintoma();

       // textViewCodigoEmpleado.setText(codigoEmpleado);
        textViewNombreEmpleado.setText(nombEmpleado);
        textViewEstadoselec.setText(estadoSelecionada);
        textViewPrioridadSeleccionado.setText(prioridadSeleccionado);

    }

    private boolean validarCampo() {
        validadarOk = true;
        editTextUbicacion.setError(null);
        editTextDescripcion.setError(null);
        String ubicacion = editTextUbicacion.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();

        if (TextUtils.isEmpty(ubicacion)) {
            editTextUbicacion.setError(getString(R.string.campo_vacio));
            editTextUbicacion.requestFocus();
            validadarOk = false;
            return validadarOk;

        }
        if (TextUtils.isEmpty(descripcion)) {
            editTextDescripcion.setError(getString(R.string.campo_vacio));
            editTextDescripcion.requestFocus();
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

        menu.add(Menu.NONE, R.integer.indice_icono_ir_siguiente, Menu.NONE, R.string.string_siguiente)
                .setIcon(R.drawable.ic_siguiente)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

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
                break;

            case R.integer.indice_icono_ir_siguiente:
                irSiguiente();
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
