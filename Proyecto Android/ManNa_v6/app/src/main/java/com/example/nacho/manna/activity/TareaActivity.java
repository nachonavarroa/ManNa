package com.example.nacho.manna.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nacho.manna.R;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudOperarios;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudTarea;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.Operarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.pojos.Tarea;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.proveedorDeContenido.Contrato;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TareaActivity extends AppCompatActivity {

    private boolean existeTarea, existeOperarios, validadarOk;
    private long idOrden;
    private int color, colorRojo, colorVerde, colorAul, colorNegro;
    private FloatingActionButton fab;
    private OrdenDeTrabajo orden;
    private ImageView imageViewFoto, imageRef;
    private TextView textViewRef, textViewFecha, textViewNombre, textViewCodigo, textViewPrioridad,
            textViewSintoma, textViewUbicacion, textViewDescripcion;
    private String nombre, ref, estado = null, trabajoRealizado = null;
    private EditText editTextTrabajoRealizado;
    private RadioGroup radioGroupEstado;
    private RadioButton radioButtonPendiente, radioButtonProceso, radioButtonRealizado;
    private LinearLayout linearLayoutTrabajoRealizado;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);
        colores();
        cabecera();
        iniciarVistas();
        setVistas();
        imagenesCabecera();
        radioGroupEstadoExtraido(orden);
        radioGroupEstado();
        floatingActionButton();
        usuario();

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerImagenActivity.class);
                intent.putExtra(Contrato.Orden._ID, idOrden);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

            }
        });


    }
    //Fin onCreate----------------------------------------------------------------------------------

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

        menu.add(Menu.NONE, R.integer.indice_icono_guardar_orden, Menu.NONE, R.string.string_guardar)
                .setIcon(R.drawable.ic_save)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        OrdenDeTrabajo orden = CrudOrdenes.readRecord(getContentResolver(), idOrden);
        Intent intent;
        switch (id) {
            case R.integer.indice_icono_ir_atras:

                intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
            case R.integer.indice_icono_guardar_orden:
                guardar();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //---------------------------------------------------------------------------------------------
    private void update(){
        orden.setEstado(estado);
        CrudOrdenes.updateOrdenConBitacora(getContentResolver(), orden, this);
        imagenesCabecera();
        crearTarea();
        crearOperarios();

    }


    private void guardar() {
        String realizado = getResources().getText(R.string.estado_realizado).toString();
        String estadoExtraido = orden.getEstado();

        if (!estado.equals(realizado)) {
            update();
            validadarOk = true;
        }


        if (estado.equals(realizado) && existeTarea()) {
            validarCampo();
            if (validadarOk) {
                update();

            }
        }

        if (validadarOk) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                    startActivity(intent);
                    finish();
                }
            }, 400);
        }
    }


    private boolean validarCampo() {
        validadarOk = true;
        editTextTrabajoRealizado.setError(null);

        String trabajoRealizado = editTextTrabajoRealizado.getText().toString();


        if (TextUtils.isEmpty(trabajoRealizado)) {
            editTextTrabajoRealizado.setError(getString(R.string.campo_vacio));
            editTextTrabajoRealizado.requestFocus();
            validadarOk = false;
        }


        return validadarOk;

    }

    private Usuario usuario() {
        int codigoUsario = AppController.getInstance().getCodigo();

        usuario = CrudUsuarios.buscar(getContentResolver(), String.valueOf(codigoUsario));

        return usuario;
    }

    private void crearOperarios() {
        existeOperarios();
        if ((estado.equals(getResources().getText(R.string.estado_proceso))
                || estado.equals(getResources().getText(R.string.estado_realizado))) && !existeOperarios) {
            Operarios operarios = new Operarios();
            operarios.setId_tarea(idOrden);
            operarios.setId_usuario(usuario.getId());

            Log.i("nachito", "operarios id_tarea: " + String.valueOf(operarios.getId_tarea()));
            Log.i("nachito", "operarios id_usuario: " + String.valueOf(operarios.getId_usuario()));

            CrudOperarios.insertOperariosConBitacora(getContentResolver(),operarios,this);
        }

    }

    private boolean existeOperarios() {
        Operarios operariosExistente = CrudOperarios.buscar(getContentResolver(), idOrden, usuario.getId());
        try {

            if (operariosExistente.getId_tarea() > 0) {
                existeOperarios = true;
            }
        } catch (Exception e) {
            existeOperarios = false;
        }

        return existeOperarios;
    }

    private boolean existeTarea() {
        existeTarea = false;
        try {
            Tarea tareaExistente = CrudTarea.readRecord(getContentResolver(), idOrden);
            if (tareaExistente!= null) {
                existeTarea = true;
            }

        } catch (Exception e) {
            existeTarea = false;
            Log.i("nachito","1-No Existe tarea (Exception e)");
        }

        return existeTarea;
    }

    private void crearTarea() {
        String realizado = getResources().getText(R.string.estado_realizado).toString();
        String proceso = getResources().getText(R.string.estado_proceso).toString();
        existeTarea();

        if (!existeTarea) {
            long id = idOrden;
            Tarea tarea = new Tarea();
            Log.i("nachito","2-No Existe tarea y creo tarea");
            tarea.setId(id);
            tarea.setId_Orden(id);
            tarea.setFecha_inicio(fecha());
            tarea.setFecha_fin("En proceso");
            tarea.setDescripcion("-");

            Log.i("nachito","3-Tarea a insertar: \n"
                    +"Id: "+String.valueOf(tarea.getId())+"\n"
                    +"Id_orden : "+String.valueOf(tarea.getId_Orden())+"\n"
                    +"fecha_inicio : "+tarea.getFecha_inicio().toString()+"\n"
                    +"fecha_fin : "+tarea.getFecha_fin()+"\n"
                    +"descripcion : "+tarea.getDescripcion());



            CrudTarea.insertTareaConBitacora(getContentResolver(), tarea,this);
            Log.i("nachito","4-No Existe tarea he creado tarea e inserto tarea  "+tarea.getDescripcion());
            Log.i("nachito","5-No Existe tarea he creado tarea e inserto tarea");
        }
        if (existeTarea && estado.equals(realizado)) {
            trabajoRealizado = editTextTrabajoRealizado.getText().toString();
            Tarea tarea = CrudTarea.readRecord(getContentResolver(), idOrden);
            tarea.setFecha_fin(fecha());
            tarea.setDescripcion(trabajoRealizado);
            CrudTarea.updateTareaConBitacora(getContentResolver(), tarea,getApplicationContext());

        }
        if (existeTarea && estado.equals(proceso)){
            Tarea tarea = CrudTarea.readRecord(getContentResolver(), idOrden);
            tarea.setFecha_fin("En proceso");
            tarea.setDescripcion("-");
            CrudTarea.updateTareaConBitacora(getContentResolver(), tarea,getApplicationContext());

        }


    }

    private String fecha() {
        String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        String fechaHora = fecha + "  " + hora;
        return fechaHora;
    }

    private void colores() {
        colorRojo = Color.parseColor("#FF0000");
        colorVerde = Color.parseColor("#009846");
        colorAul = Color.parseColor("#0000FF");
        colorNegro = Color.parseColor("#000000");

    }

    private void radioGroupEstado() {
        radioGroupEstado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonEstado0Tarea:
                        estado = (String) getResources().getText(R.string.estado_pendiente);

                        linearLayoutTrabajoRealizado.setVisibility(View.GONE);

                        radioButtonPendiente.setBackgroundColor(colorRojo);
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonProceso.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));


                        break;

                    case R.id.radioButtonEstado1Tarea:
                        estado = (String) getResources().getText(R.string.estado_proceso);

                        linearLayoutTrabajoRealizado.setVisibility(View.GONE);

                        radioButtonProceso.setBackgroundColor(colorAul);
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonPendiente.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));


                        break;

                    case R.id.radioButtonEstado2Tarea:
                        estado = (String) getResources().getText(R.string.estado_realizado);

                        linearLayoutTrabajoRealizado.setVisibility(View.VISIBLE);

                        radioButtonRealizado.setBackgroundColor(colorVerde);
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonPendiente.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonProceso.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorNegro));


                        break;

                }
            }
        });


    }

    private void radioGroupEstadoExtraido(OrdenDeTrabajo orden) {
        String estadoExtraido = orden.getEstado();
        String estadoPendiente = getResources().getString(R.string.estado_pendiente);
        String estadoProceso = getResources().getString(R.string.estado_proceso);
        String estadoFinalizado = getResources().getString(R.string.estado_realizado);


        if (estadoExtraido.equals(estadoPendiente)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado0Tarea);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorRojo);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));
            linearLayoutTrabajoRealizado.setVisibility(View.GONE);


        } else if (estadoExtraido.equals(estadoProceso)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado1Tarea);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorAul);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));
            linearLayoutTrabajoRealizado.setVisibility(View.GONE);


        } else if (estadoExtraido.equals(estadoFinalizado)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado2Tarea);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorVerde);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));
            linearLayoutTrabajoRealizado.setVisibility(View.VISIBLE);
            Tarea tarea = CrudTarea.readRecord(getContentResolver(), idOrden);
            editTextTrabajoRealizado.setText(tarea.getDescripcion());


        }

    }

    //imagenes cabecera-----------------------------------------------------------------------------

    private void imagenesCabecera() {
        colorFondoRef();
        int lenghtRef = String.valueOf(ref).length();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(ref).substring(0, lenghtRef), color);

        imageRef.setImageDrawable(drawable);

        try {
            Utilidades.loadImageFromStorage(getApplicationContext(), "img_" + idOrden + ".jpg", imageViewFoto);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //color referencia----------------------------------------------------------------------------
    private void colorFondoRef() {
        if (orden.getEstado().equals("Pendiente")) {
            color = colorRojo;
        }
        if (orden.getEstado().equals("Proceso")) {
            color = colorAul;
        }

        if (orden.getEstado().equals("Realizado")) {
            color = colorVerde;
        }
    }


    //set vistas-----------------------------------------------------------------------------------

    private void setVistas() {
        textViewRef.setText("Ref: " + ref);
        textViewFecha.setText(Contrato.Orden._fecha + ": " + orden.getFecha().toString());
        textViewNombre.setText("Creado por: " + nombre);
        textViewCodigo.setText(Contrato.Orden._codigoOrden + ": " + Long.toString(idOrden));
        textViewPrioridad.setText(Contrato.Orden._prioridad_ + ": " + orden.getPrioridad().toString());
        textViewSintoma.setText(Contrato.Orden._sintoma + ": " + orden.getSintoma().toString());
        textViewUbicacion.setText(Contrato.Orden._ubicacion + ": " + orden.getUbicacion().toString());
        textViewDescripcion.setText(Contrato.Orden._descripcion + ": " + orden.getDescripcion().toString());


    }


    //Iniciar vistas------------------------------------------
    private void iniciarVistas() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        textViewRef = (TextView) findViewById(R.id.textView_referenciaTarea);
        textViewFecha = (TextView) findViewById(R.id.textView_fechaTarea);
        textViewNombre = (TextView) findViewById(R.id.textView_nombreEmpleadoTarea);
        textViewCodigo = (TextView) findViewById(R.id.textView_codigoTarea);
        textViewPrioridad = (TextView) findViewById(R.id.textView_prioridadTarea);
        textViewSintoma = (TextView) findViewById(R.id.textView_sintomaTarea);
        textViewUbicacion = (TextView) findViewById(R.id.textView_ubicacionTarea);
        textViewDescripcion = (TextView) findViewById(R.id.textView_descrripcionTarea);

        imageViewFoto = (ImageView) findViewById(R.id.imageView_Tarea);
        imageRef = (ImageView) findViewById(R.id.imageView_ref_Tarea);
        editTextTrabajoRealizado = (EditText) findViewById(R.id.editTextDescripcionTarea);

        radioGroupEstado = (RadioGroup) findViewById(R.id.radioGroupEstadoTarea);

        radioButtonPendiente = (RadioButton) findViewById(R.id.radioButtonEstado0Tarea);
        radioButtonProceso = (RadioButton) findViewById(R.id.radioButtonEstado1Tarea);
        radioButtonRealizado = (RadioButton) findViewById(R.id.radioButtonEstado2Tarea);

        linearLayoutTrabajoRealizado = (LinearLayout) findViewById(R.id.LLTrabajoRealizadoTarea);

    }

    //ActionBar------------------------------------------------------------------------------------
    private void cabecera() {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        idOrden = this.getIntent().getExtras().getLong(Contrato.Orden._ID);
        orden = CrudOrdenes.readRecord(getContentResolver(), idOrden);
        nombre = CrudUsuarios.buscarUsuarioEnOrden(getApplicationContext(), getContentResolver(), idOrden);
        ref = Utilidades.referencia(idOrden);
        actionBar.setSubtitle("Tarea Ref: " + ref);


    }

    //FloatingActionButton---------------------------------------------------------
    private void floatingActionButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardar();

            }
        });

    }

}
