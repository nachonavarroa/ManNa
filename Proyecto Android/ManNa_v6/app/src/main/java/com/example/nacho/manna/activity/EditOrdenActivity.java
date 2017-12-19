package com.example.nacho.manna.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.volley.ImagenVoley;

import java.io.FileNotFoundException;

public class EditOrdenActivity extends AppCompatActivity {

    Bitmap foto = null;
    Intent intent;
    RadioGroup radioGroupPrioridad, radioGroupEstado;
    RadioButton radioButtonPendiente, radioButtonProceso, radioButtonRealizado;
    TextView textViewEstado, textViewCodigo, textViewRef, textViewFecha, textViewCreadoPor;
    EditText editTextUbicacion, editTextDescripcion, editTextSintoma;
    ImageView imageViewFoto;
    ImageButton imageButtonGaleria, imageButtonCamara;
    View view1, view2, view3, view4, view5, view6;
    Toolbar toolbar;
    String prioridad = null, estado = null;
    Boolean validadarOk,elegirEnGaleria=false;
    Bitmap imagenCargada= null;
    long ordenId;
    int colorRojo , colorVerde, colorNegro, colorAul,contieneImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orden);
        iniciarVistas();
        setSupportActionBar(toolbar);


        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_editar_orden));

        //Floating Button-----------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_activity_edit_orden);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
        //-----------------------

        ordenId = this.getIntent().getExtras().getLong(Contrato.Orden._ID);
        OrdenDeTrabajo orden = CrudOrdenes.readRecord(getContentResolver(), ordenId);
        extraerDatos(orden);
        radioGruopPrioridadExtraida(orden);
        colores();
        radioGroupEstadoExtraido(orden);
        radioGroupPrioridad();
        radioGroupEstado();
        sinPermisoAdministrador(orden, Utilidades.permisoAdministrador(this));
        imagen();



    }

    //fin onCreate----------------------------------------------------------------------------------
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
        OrdenDeTrabajo orden = CrudOrdenes.readRecord(getContentResolver(), ordenId);
        switch (id) {
            case R.integer.indice_icono_ir_atras:
                if (cambioEnOrden(orden)) {
                    preguntarGuardar(this.getResources().getText(R.string.string_si).toString(),
                            this.getResources().getText(R.string.string_no).toString());
                } else {
                    intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }

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

    //----------------------------------------------------------------------------------------------
    private void radioGroupEstado() {

        radioGroupEstado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {
                switch (checkId) {
                    case R.id.radioButtonEstado0Edit:
                        estado = (String) getResources().getText(R.string.estado_pendiente);
                        radioButtonPendiente.setBackgroundColor(colorRojo);
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonProceso.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));


                        break;

                    case R.id.radioButtonEstado1Edit:
                        estado = (String) getResources().getText(R.string.estado_proceso);

                        radioButtonProceso.setBackgroundColor(colorAul);
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonPendiente.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));


                        break;

                    case R.id.radioButtonEstado2Edit:
                        estado = (String) getResources().getText(R.string.estado_realizado);
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


    private void radioGroupPrioridad() {
        radioGroupPrioridad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {


                switch (checkId) {
                    case R.id.radioButtonPrioridad0Edit:
                        prioridad = (String) getResources().getText(R.string.prioridad_muy_alta);

                        break;
                    case R.id.radioButtonPrioridad1Edit:
                        prioridad = (String) getResources().getText(R.string.prioridad_alta);

                        break;
                    case R.id.radioButtonPrioridad2Edit:
                        prioridad = (String) getResources().getText(R.string.prioridad_media);

                        break;
                    case R.id.radioButtonPrioridad3Edit:
                        prioridad = (String) getResources().getText(R.string.prioridad_baja);

                        break;
                }

            }
        });


    }


    private void sinPermisoAdministrador(OrdenDeTrabajo orden, boolean pernisoAdministrador) {
        if (!pernisoAdministrador) {
            radioGroupPrioridad.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
            view5.setVisibility(View.GONE);
            view6.setVisibility(View.GONE);

            LinearLayout linearLayoutPrincipal = (LinearLayout) findViewById(R.id.LLPrincipalEditOrden);
            LinearLayout linearLayoutEstado = (LinearLayout) findViewById(R.id.LLEstadoEditOrden);

            linearLayoutPrincipal.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
            linearLayoutEstado.setBackgroundColor(getResources().getColor(R.color.colorFondo));

            TextView textViewPrioridad = (TextView) findViewById(R.id.textViewPrioridadEditOrden);
            TextView textViewUbicacion = (TextView) findViewById(R.id.textViewUbicacionEditOrden);
            TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcionEditOrden);
            TextView textViewSintoma = (TextView) findViewById(R.id.textViewSintomadEditOrden);

            textViewPrioridad.setText(getResources().getString(R.string.prioridad) + ": " + orden.getPrioridad());
            textViewUbicacion.setText(getResources().getString(R.string.ubicacion) + ": " + orden.getUbicacion());
            textViewDescripcion.setText(getResources().getString(R.string.descripcion) + ": " + orden.getDescripcion());
            textViewDescripcion.setTextColor(ContextCompat.getColor(this, R.color.colorAzul));
            textViewSintoma.setText(getResources().getString(R.string.sintoma) + ": " + orden.getSintoma());

            TextInputLayout textInputLayoutSintoma = (TextInputLayout)
                    findViewById(R.id.textInputLayoutSintoma2EditOrden);
            TextInputLayout textInputLayoutUbicacion = (TextInputLayout)
                    findViewById(R.id.textInputLayoutUbicacionEdit);
            TextInputLayout textInputLayoutDescripcion = (TextInputLayout)
                    findViewById(R.id.textInputLayoutDescripcionEdit);


            textInputLayoutSintoma.removeAllViews();
            textInputLayoutUbicacion.removeAllViews();
            textInputLayoutDescripcion.removeAllViews();

            imageButtonCamara.setVisibility(View.GONE);
            imageButtonGaleria.setVisibility(View.GONE);

        }


    }

    private void imagen() {

        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hacerFoto();
            }
        });
        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irGaleria();
            }

        });

        try {
            Utilidades.loadImageFromStorage(this, "img_" + ordenId + ".jpg"
                    , imageViewFoto);

            imagenCargada = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            //no existe imagen
        }


    }

    //----------------------------------------------------------------------------------------------
    private void iniciarVistas() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        textViewEstado = (TextView) findViewById(R.id.textViewEstadoEditOrden);
        textViewCodigo = (TextView) findViewById(R.id.textViewCodigoEditOrden);
        textViewRef = (TextView) findViewById(R.id.textViewReferenciaEditOrden);
        textViewFecha = (TextView) findViewById(R.id.textViewFechaEditOrden);
        textViewCreadoPor = (TextView) findViewById(R.id.textViewFechaEditCreadoPor);

        radioGroupPrioridad = (RadioGroup) findViewById(R.id.radioGroupPrioridadEdit);
        radioGroupEstado = (RadioGroup) findViewById(R.id.radioGroupEstadoEdit);

        radioButtonPendiente = (RadioButton) findViewById(R.id.radioButtonEstado0Edit);
        radioButtonProceso = (RadioButton) findViewById(R.id.radioButtonEstado1Edit);
        radioButtonRealizado = (RadioButton) findViewById(R.id.radioButtonEstado2Edit);


        editTextUbicacion = (EditText) findViewById(R.id.editTextUbicacionEdit);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcionEdit);
        editTextSintoma = (EditText) findViewById(R.id.editTextSintoma2EditOrden);

        imageViewFoto = (ImageView) findViewById(R.id.imageView_foto_conten_edit_orden);
        imageButtonGaleria = (ImageButton) findViewById(R.id.imageButton_galeria_content_edit_orden);
        imageButtonCamara = (ImageButton) findViewById(R.id.imageButton_camara_content_edit_orden);

        view1 = (View) findViewById(R.id.viewDivisionEditOrden1);
        view2 = (View) findViewById(R.id.viewDivisionEditOrden2);
        view3 = (View) findViewById(R.id.viewDivisionEditOrden3);
        view4 = (View) findViewById(R.id.viewDivisionEditOrden4);
        view5 = (View) findViewById(R.id.viewDivisionEditOrden5);
        view6 = (View) findViewById(R.id.viewDivisionEditOrden6);


    }

    private void colores() {
        colorRojo = Color.parseColor("#FF0000");
        colorVerde = Color.parseColor("#009846");
        colorAul = Color.parseColor("#0000FF");
        colorNegro = Color.parseColor("#000000");

    }

    private boolean cambioEnOrden(OrdenDeTrabajo orden) {
        boolean cambioEnOrden = false;

        if (!orden.getEstado().equals(estado)) {
            cambioEnOrden = true;
        }
        if(!orden.getPrioridad().equals(prioridad)){
            cambioEnOrden = true;
        }
        if(!orden.getSintoma().equals(editTextSintoma.getText().toString()) ){
            cambioEnOrden = true;
        }
        if(!orden.getUbicacion().equals(editTextUbicacion.getText().toString()) ){
            cambioEnOrden = true;
        }
        if(!orden.getDescripcion().equals(editTextDescripcion.getText().toString()) ){
            cambioEnOrden = true;
        }
        try {
            if (!imagenCargada.equals(((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap()) ) {
                cambioEnOrden = true;
            }
        }catch (Exception e) {

        }
        if(elegirEnGaleria){
            cambioEnOrden = true;
        }
        return cambioEnOrden;
    }


    //Metodos actulizan estados de checkBox y Radiobutton con respecto a la orden editada-----------


    private void extraerDatos(OrdenDeTrabajo orden) {

        textViewCodigo.setText("Código: " + String.valueOf(orden.getId()));
        textViewRef.setText("Referencia: " + Utilidades.referencia(ordenId));
        textViewFecha.setText("Fecha: " + orden.getFecha());
        textViewCreadoPor.setText("Creado por: " + CrudUsuarios.buscarUsuarioEnOrden
                (this, this.getContentResolver(), orden.getId()));
        editTextUbicacion.setText(orden.getUbicacion());
        editTextDescripcion.setText(orden.getDescripcion());
        editTextSintoma.setText(orden.getSintoma());
        contieneImagen=orden.getContieneImagen();

    }

    private void radioGruopPrioridadExtraida(OrdenDeTrabajo orden) {
        String prioridadExtraida = orden.getPrioridad();
        String prioridadMuyalta = getResources().getString(R.string.prioridad_muy_alta);
        String prioridadAlta = getResources().getString(R.string.prioridad_alta);
        String prioridadMadia = getResources().getString(R.string.prioridad_media);
        String prioridadBaja = getResources().getString(R.string.prioridad_baja);

        if (prioridadExtraida.equals(prioridadMuyalta)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonPrioridad0Edit);
            radioButton.setChecked(true);
            prioridad = prioridadExtraida;


        } else if (prioridadExtraida.equals(prioridadAlta)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonPrioridad1Edit);
            radioButton.setChecked(true);
            prioridad = prioridadExtraida;


        } else if (prioridadExtraida.equals(prioridadMadia)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonPrioridad2Edit);
            radioButton.setChecked(true);
            prioridad = prioridadExtraida;


        } else if (prioridadExtraida.equals(prioridadBaja)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonPrioridad3Edit);
            radioButton.setChecked(true);
            prioridad = prioridadExtraida;

        }

    }

    private void radioGroupEstadoExtraido(OrdenDeTrabajo orden) {
        String estadoExtraido = orden.getEstado();
        String estadoPendiente = getResources().getString(R.string.estado_pendiente);
        String estadoProceso = getResources().getString(R.string.estado_proceso);
        String estadoFinalizado = getResources().getString(R.string.estado_realizado);


        if (estadoExtraido.equals(estadoPendiente)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado0Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorRojo);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));


        } else if (estadoExtraido.equals(estadoProceso)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado1Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorAul);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));


        } else if (estadoExtraido.equals(estadoFinalizado)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado2Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            radioButton.setBackgroundColor(colorVerde);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));


        }

    }

    //--------------------------------------------------------------------------------------------------
    private boolean validarCampo() {
        validadarOk = true;
        editTextUbicacion.setError(null);
        editTextDescripcion.setError(null);
        String ubicacion = editTextUbicacion.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String sintoma = editTextSintoma.getText().toString();

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

        if (TextUtils.isEmpty(sintoma)) {
            editTextSintoma.setError(getString(R.string.campo_vacio));
            editTextSintoma.requestFocus();
            validadarOk = false;
            return validadarOk;
        }

        return validadarOk;

    }

    private void guardar() {
        if (validarCampo()) {
            OrdenDeTrabajo orden = new OrdenDeTrabajo();
            orden.setId(ordenId);
            orden.setSintoma(editTextSintoma.getText().toString());
            orden.setPrioridad(prioridad);
            orden.setUbicacion(editTextUbicacion.getText().toString());
            orden.setDescripcion(editTextDescripcion.getText().toString());
            orden.setEstado(estado);
            orden.setImagen(foto);
            orden.setContieneImagen(contieneImagen);

            try {
                CrudOrdenes.updateOrdenConBitacora(getContentResolver(), orden, this);
                ImagenVoley.subirImagenServidor(getApplicationContext(),orden.getId());

            } catch (Exception e) {
                Toast.makeText(this, "No existe la orden seleccionada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivityDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
            intent = new Intent(this, MainActivityDrawer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

    }

    //Foto------------------------------------------

    private void hacerFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constantes.SACAR_FOTO);

    }

    private void irGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, Constantes.VER_GALRIA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case Constantes.SACAR_FOTO:
                if (resultCode == RESULT_OK) {
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewFoto.setImageBitmap(foto);

                } else {
                    //"Usuario cancela"
                }

                break;
            case Constantes.VER_GALRIA:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    imageViewFoto.setImageURI(uri);
                    foto = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();
                    elegirEnGaleria= true;

                } else {
                    //"Usuario cancela"
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void preguntarGuardar(String si, String no) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Guardar");
        dialog.setMessage("¿ Desea guardar los cambios ?");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setPositiveButton(si, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardar();
            }
        });

        dialog.setNegativeButton(no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                dialog.cancel();
            }
        });
        dialog.show();

    }

}
