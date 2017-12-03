package com.example.nacho.mannadrawe.activity;

import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.auxiliar.Utilidades;
import com.example.nacho.mannadrawe.crud.CrudOrdenes;
import com.example.nacho.mannadrawe.crud.CrudUsuarios;
import com.example.nacho.mannadrawe.pojos.OrdenDeTrabajo;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.proveedorDeContenido.Contrato;
import com.example.nacho.mannadrawe.aplication.AppController;
import com.example.nacho.mannadrawe.sync.Sincronizacion;

import java.io.FileNotFoundException;

public class EditOrdenActivity extends AppCompatActivity {

    final int SACAR_FOTO = 1;
    final int VER_GALRIA = 2;
    Bitmap foto = null;

    Activity contexto;
    Intent intent;
    RadioGroup radioGroupPrioridad;
    RadioGroup radioGroupEstado;

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
        editTextUbicacion = (EditText) findViewById(R.id.editTextUbicacionEdit);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcionEdit);
        editTextSintoma = (EditText) findViewById(R.id.editTextSintoma2EditOrden);

        imageViewFoto = (ImageView) findViewById(R.id.imageView_foto_conten_edit_orden);
        imageButtonGaleria = (ImageButton) findViewById(R.id.imageButton_galeria_content_edit_orden);
        imageButtonCamara = (ImageButton) findViewById(R.id.imageButton_camara_content_edit_orden);

        ordenId = this.getIntent().getExtras().getLong(Contrato.Orden._ID);
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

            radioGroupPrioridad.setVisibility(View.INVISIBLE);
            TextView textViewPrioridad = (TextView) findViewById(R.id.textViewPrioridadEditOrden);
            textViewPrioridad.setText(getResources().getString(R.string.prioridad)+": "+orden.getPrioridad());

            editTextSintoma.setVisibility(View.INVISIBLE);
            TextView textViewSintoma = (TextView) findViewById(R.id.textViewSintomadEditOrden);
            textViewSintoma.setText(getResources().getString(R.string.sintoma)+": "+orden.getSintoma());

            editTextUbicacion.setVisibility(View.INVISIBLE);
            TextView textViewUbicacion = (TextView) findViewById(R.id.textViewUbicacionEditOrden);
            textViewUbicacion.setText(getResources().getString(R.string.ubicacion)+": "+orden.getUbicacion());

            editTextDescripcion.setVisibility(View.INVISIBLE);
            TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcionEditOrden);
            textViewDescripcion.setText(getResources().getString(R.string.descripcion)+": "+orden.getDescripcion());


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
                mensajeSeleccionado(prioridad);

            }
        });


        radioGroupEstado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {


                switch (checkId) {

                    case R.id.radioButtonEstado0Edit:
                        estado = (String) getResources().getText(R.string.estado_pendiente);
                        textViewEstado.setTextColor(colorRojo);


                        break;
                    case R.id.radioButtonEstado1Edit:
                        estado = (String) getResources().getText(R.string.estado_proceso);
                        textViewEstado.setTextColor(colorAul);


                        break;
                    case R.id.radioButtonEstado2Edit:
                        estado = (String) getResources().getText(R.string.estado_realizado);
                        textViewEstado.setTextColor(colorVerde);

                        break;

                }
                mensajeSeleccionado(estado);

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



        } else if (estadoExtraido.equals(estadoProceso)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado1Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            textViewEstado.setTextColor(colorAul);



        } else if (estadoExtraido.equals(estadoFinalizado)) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonEstado2Edit);
            radioButton.setChecked(true);
            estado = estadoExtraido;
            textViewEstado.setTextColor(colorVerde);


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
            CrudOrdenes.updateOrdenConBitacora(getContentResolver(), orden,contexto);
            intent = new Intent(contexto, MainActivityDrawer.class);
            Sincronizacion sin = new Sincronizacion(getApplicationContext());
            sin.sincronizar();
            Sincronizacion.recibirActualizacionesDelServidor();
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
