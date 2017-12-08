package com.example.nacho.manna.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.sync.Sincronizacion;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditOrdenActivity extends AppCompatActivity {

    final int SACAR_FOTO = 1;
    final int VER_GALRIA = 2;
    Bitmap foto = null;

    Activity contexto;
    Intent intent;

    RadioGroup radioGroupPrioridad;
    RadioGroup radioGroupEstado;

    RadioButton radioButtonPendiente;
    RadioButton radioButtonProceso;
    RadioButton radioButtonRealizado;

    TextView textViewEstado;

    EditText editTextUbicacion;
    EditText editTextDescripcion;
    EditText editTextSintoma;

    ImageView imageViewFoto;
    ImageButton imageButtonGaleria;
    ImageButton imageButtonCamara;

    String prioridad = "";
    String estado = null;
    Boolean validadarOk;

    int xOffset;
    int yOffset;
    long ordenId;
    int colorRojo = 0;
    int colorMarron ;
    int colorVerde ;
    int colorNegro ;
    int colorAul;
    ScrollView scrollViewPather;
    LinearLayout linearLayoutPather;
    boolean permisoAdministrador;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contexto = this;
        xOffset = 100;
        yOffset = 660;

        textViewEstado = (TextView) findViewById(R.id.textViewEstadoEditOrden) ;

        colorRojo = Color.parseColor("#FF0000");
        colorMarron = Color.parseColor("#763C28");
        colorVerde = Color.parseColor("#009846");
        colorAul = Color.parseColor("#0000FF");
        colorNegro = Color.parseColor("#000000");
        ordenId = this.getIntent().getExtras().getLong(Contrato.Orden._ID);


        //ActionBar----------------------------
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_editar_orden));

        //----------------------------

        //Floating Button-----------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_activity_edit_orden);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
        //-----------------------

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


        OrdenDeTrabajo orden = CrudOrdenes.readRecord(getContentResolver(), ordenId);

        try {

            Utilidades.loadImageFromStorage(this,"img_"+ ordenId +".jpg"
                    ,imageViewFoto);
        } catch (FileNotFoundException e) {
            //no existe imagen

        }


        editTextUbicacion.setText(orden.getUbicacion());
        editTextDescripcion.setText(orden.getDescripcion());
        editTextSintoma.setText(orden.getSintoma());

        prioridadRadioButton(orden);
        estadoRadioButton(orden);

        if(!permisoAdministrador()){
            radioGroupPrioridad.setVisibility(View.GONE);

            View viewDivisionCodigo =(View) findViewById(R.id.viewDivisionCodigo);
            viewDivisionCodigo.setVisibility(View.GONE);
            View viewDivisionPrioridad =(View) findViewById(R.id.viewDivisionEditOrdenPrioridad);
            viewDivisionPrioridad.setVisibility(View.GONE);
            View viewDivisionDescripcion =(View) findViewById(R.id.viewDivisionDescripcionEditOrden);
            viewDivisionDescripcion.setVisibility(View.GONE);

            TextView textViewPrioridad = (TextView) findViewById(R.id.textViewPrioridadEditOrden);
            textViewPrioridad.setText(getResources().getString(R.string.prioridad)+": "+orden.getPrioridad());

           // editTextSintoma.setVisibility(View.INVISIBLE);
            TextInputLayout textInputLayoutSintoma =(TextInputLayout)
                    findViewById(R.id.textInputLayoutSintoma2EditOrden);
            textInputLayoutSintoma.removeAllViews();
            TextView textViewSintoma = (TextView) findViewById(R.id.textViewSintomadEditOrden);
            textViewSintoma.setText(getResources().getString(R.string.sintoma)+": "+orden.getSintoma());

            TextInputLayout textInputLayoutUbicacion =(TextInputLayout)
                    findViewById(R.id.textInputLayoutUbicacionEdit);
            textInputLayoutUbicacion.removeAllViews();

            //editTextUbicacion.setVisibility(View.GONE);
            TextView textViewUbicacion = (TextView) findViewById(R.id.textViewUbicacionEditOrden);
            textViewUbicacion.setText(getResources().getString(R.string.ubicacion)+": "+orden.getUbicacion());


            TextInputLayout textInputLayoutDescripcion =(TextInputLayout)
                    findViewById(R.id.textInputLayoutDescripcionEdit);
            textInputLayoutDescripcion.removeAllViews();

           // editTextDescripcion.setVisibility(View.GONE);
            TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcionEditOrden);
            textViewDescripcion.setText(getResources().getString(R.string.descripcion)+": "+orden.getDescripcion());

            linearLayoutPather = (LinearLayout) findViewById(R.id.LLPrincipalEditOrden);
            linearLayoutPather.setScaleY(0.95f);
            linearLayoutPather.setScaleX(0.95f);

        }


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
              //  mensajeSeleccionado(prioridad);

            }
        });


        radioGroupEstado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {


                switch (checkId) {

                    case R.id.radioButtonEstado0Edit:
                        estado = (String) getResources().getText(R.string.estado_pendiente);
                        textViewEstado.setTextColor(colorRojo);

                        radioButtonPendiente.setBackgroundColor(colorRojo);
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorFondo));


                        radioButtonProceso.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));

                        break;

                    case R.id.radioButtonEstado1Edit:
                        estado = (String) getResources().getText(R.string.estado_proceso);
                        textViewEstado.setTextColor(colorAul);

                        radioButtonProceso.setBackgroundColor(colorAul);
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonPendiente.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonRealizado.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorNegro));

                        break;

                    case R.id.radioButtonEstado2Edit:
                        estado = (String) getResources().getText(R.string.estado_realizado);
                        textViewEstado.setTextColor(colorVerde);

                        radioButtonRealizado.setBackgroundColor(colorVerde);
                        radioButtonRealizado.setTextColor(getResources().getColor(R.color.colorFondo));

                        radioButtonPendiente.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonPendiente.setTextColor(getResources().getColor(R.color.colorNegro));

                        radioButtonProceso.setBackgroundColor(getResources().getColor(R.color.colorFondo));
                        radioButtonProceso.setTextColor(getResources().getColor(R.color.colorNegro));

                        break;

                }
               // mensajeSeleccionado(estado);

            }
        });

        //-foto---------------------------------------------------------------
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irGaleria();
            }
        });
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

        Utilidades.loadDesdeServidor(contexto,imageViewFoto,ordenId);
        cabecera(orden);
    }



    private boolean permisoAdministrador() {
        permisoAdministrador = false;
        AppController empleado = (AppController) this.getApplication();
        Usuario empleadoAdmin = CrudUsuarios.login(this.getContentResolver(),
                String.valueOf(empleado.getCodigo()), empleado.getNombre());
        String admin = empleadoAdmin.getAdmin();
        if (admin.equals(getResources().getString(R.string.string_si))) {
            permisoAdministrador = true;

        } else {

            permisoAdministrador = false;

        }
        return permisoAdministrador;
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

        menu.add(Menu.NONE, R.integer.indice_icono_guardar_orden, Menu.NONE, R.string.string_guardar)
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
                intent=  new Intent(getApplicationContext(), MainActivityDrawer.class);
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

    //Metodos actulizan estados de checkBox y Radiobutton con respecto a la orden editada-----------


    private void cabecera(OrdenDeTrabajo orden){
        TextView textViewCodigo;
        TextView textViewRef;
        TextView textViewFecha;

        textViewCodigo =(TextView) findViewById(R.id.textViewCodigoEditOrden);
        textViewRef =(TextView) findViewById(R.id.textViewReferenciaEditOrden);
        textViewFecha =(TextView) findViewById(R.id.textViewFechaEditOrden);

        textViewCodigo.setText("Código: "+String.valueOf(orden.getId()));
        textViewRef.setText("Referencia: "+String.valueOf(orden.getId()%9999));
        textViewFecha.setText("Fecha: "+orden.getFecha());

    }

    private void prioridadRadioButton(OrdenDeTrabajo orden) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void estadoRadioButton(OrdenDeTrabajo orden) {
        String estadoExtraido = orden.getEstado();
        String estadoPendiente = getResources().getString(R.string.estado_pendiente);
        String estadoProceso = getResources().getString(R.string.estado_proceso);
        String estadoFinalizado = getResources().getString(R.string.estado_realizado);


        if (estadoExtraido.equals(estadoPendiente)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado0Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            textViewEstado.setTextColor(colorRojo);
            radioButton.setBackgroundColor(colorRojo);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));




        } else if (estadoExtraido.equals(estadoProceso)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado1Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            textViewEstado.setTextColor(colorAul);
            radioButton.setBackgroundColor(colorAul);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));



        } else if (estadoExtraido.equals(estadoFinalizado)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado2Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            textViewEstado.setTextColor(colorVerde);
            radioButton.setBackgroundColor(colorVerde);
            radioButton.setTextColor(getResources().getColor(R.color.colorFondo));


        }

    }

    //----------------------------------------------------------------------------------------------
    private void mensajeSeleccionado(String estado) {
        Toast toast = Toast.makeText(contexto, estado + " " + getText(R.string.seleccionado), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, xOffset, yOffset);
        toast.show();
    }

    private void mensajeDeseleccionado(String estado) {
        Toast toast = Toast.makeText(contexto, estado + " " + getText(R.string.deseleccionado), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, xOffset, yOffset);
        toast.show();
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
            try{
                CrudOrdenes.updateOrdenConBitacora(getContentResolver(), orden,contexto);
            }catch(Exception e){
                Toast.makeText(contexto,"No existe la orden seleccionada",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contexto,MainActivityDrawer.class);
                startActivity(intent);
                finish();
            }


            intent = new Intent(contexto, MainActivityDrawer.class);

            startActivity(intent);
            finish();
        }

    }

    //Foto------------------------------------------

    private void hacerFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SACAR_FOTO);

    }

    private void irGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, VER_GALRIA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case SACAR_FOTO:
                if (resultCode == RESULT_OK) {
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewFoto.setImageBitmap(foto);


                } else {
                    //"Usuario cancela"
                }

                break;
            case VER_GALRIA:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    imageViewFoto.setImageURI(uri);
                    foto = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();


                } else {
                    //"Usuario cancela"
                }
                break;


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
