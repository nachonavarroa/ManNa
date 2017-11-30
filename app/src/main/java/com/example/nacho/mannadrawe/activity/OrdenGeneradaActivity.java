package com.example.nacho.mannadrawe.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.auxiliar.Utilidades;
import com.example.nacho.mannadrawe.crud.CrudOrdenes;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.pojos.OrdenDeTrabajo;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;

public class OrdenGeneradaActivity extends AppCompatActivity {

    final int SACAR_FOTO = 1;
    final int VER_GALRIA = 2;
    Bitmap foto = null;

    Activity contexto;
    TextView textViewFecha;
    TextView textViewCodigo;
    TextView textViewNombreEmpleado;
    TextView textViewPrioridad;
    TextView textViewEstado;
    TextView textViewDescripcion;
    TextView textViewUbicacion;
    CheckBox checkBoxMostrarPdf;
    ImageView imageViewFoto;
    FloatingActionButton fab;
    ImageButton imageButtonGaleria;
    ImageButton imageButtonCamara;
    Intent intent;
    String fecha;
    long id;
    String nombreOperario;
    String codigoOperarioString;
    String prioridad;
    String estado;
    String ubicacion;
    String descripcion;
    OrdenDeTrabajo ordenDeTrabajo;
    Usuario datosEmpleado;
    Boolean OrdenConfirmada = false;
    Boolean generarPdf = false;
    String nombreApp;
    File carpetaApp;
    File carpetaOrden;
    File carpetaFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_generada);

        contexto = this;
        intent = this.getIntent();
        nombreApp = getResources().getText(R.string.app_name).toString();

        //ActionBar------------------------ -------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));
        actionBar.setSubtitle(getResources().getText(R.string.subtitulo_orden_generada));
        //-----------------------------------------
        iniciarViews();
        introducirFechaId();
        extraeDatosEmpleado();
        extraeDatosOrdenDeTrabajo();
        generarOrden();
        checkBoxMostrarPdf();

        //FloatingButtons---------------------------------------------------------------------------
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar();
            }
        });

        //Botones laterales-------------------------------------------------------------------------
        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irGaleria();
            }
        });
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hacerFoto(ordenDeTrabajo);
            }
        });

        //Botón imagen------------------------------------------------------------------------------
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irGaleria();
            }
        });
    }

    private void iniciarViews() {
        checkBoxMostrarPdf = (CheckBox) findViewById(R.id.checkBoxMostrarPdf);
        textViewFecha = (TextView) findViewById(R.id.textViewFecha);
        textViewCodigo = (TextView) findViewById(R.id.textViewCodigo);
        textViewNombreEmpleado = (TextView) findViewById(R.id.textViewNombreEmpleado);
        textViewPrioridad = (TextView) findViewById(R.id.textViewPrioridad);
        textViewEstado = (TextView) findViewById(R.id.textViewEstado);
        textViewUbicacion = (TextView) findViewById(R.id.textViewUbicacion);
        textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcion);
        fab = (FloatingActionButton) findViewById(R.id.fab_de_orden_confirmada);

        imageViewFoto = (ImageView) findViewById(R.id.imageButton_content_orden_generada_foto);
        imageButtonGaleria = (ImageButton) findViewById(R.id.imageButton_galeria_content_orden_generada);
        imageButtonCamara = (ImageButton) findViewById(R.id.imageButton_camara_content_orden_generada);


    }

    //Menús-----------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.integer.indice_icono_camara, Menu.NONE, R.string.string_camara)
                .setIcon(R.drawable.ic_camera_primary_dark)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ayuda, Menu.NONE, R.string.string_ayuda)
                .setIcon(R.drawable.ic_help_outline_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, R.integer.indice_icono_ir_siguiente, Menu.NONE, R.string.boton_confirmar)
                .setIcon(R.drawable.ic_save)
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
            case R.integer.indice_icono_camara:
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.string_camara),
                        Toast.LENGTH_SHORT).show();
                hacerFoto(ordenDeTrabajo);
                break;
            case R.integer.indice_icono_ir_siguiente:
                confirmar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //----------------------------------------------------------------------------------------------

    private void checkBoxMostrarPdf() {
        checkBoxMostrarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxMostrarPdf.isChecked()) {
                    generarPdf = true;
                    Toast.makeText(contexto, getResources().getString(R.string.toast_mostrarPdf),
                            Toast.LENGTH_LONG).show();
                } else {
                    generarPdf = false;
                    Toast.makeText(contexto, getResources().getString(R.string.toast_no_mostrarPdf),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void confirmar() {
        if (generarPdf) {
            muestraPdf(crearPdf(generarTexto(), ordenDeTrabajo), contexto);
        }
        cambioColor();

        if (!OrdenConfirmada) {
            ordenDeTrabajo.setEstado(getResources().getString(R.string.estado_pendiente));
            ordenDeTrabajo.setCodigoEmpleado(datosEmpleado.getCodigo());

            if (foto != null) {
                ordenDeTrabajo.setImagen(foto);
            }

            CrudOrdenes.insertOrdenConBitacora(getContentResolver(), ordenDeTrabajo, this);

            Toast.makeText(contexto, getResources().
                    getText(R.string.orden_generada_correcta), Toast.LENGTH_SHORT).show();

            finish();
        }
        if (OrdenConfirmada) {
            Toast.makeText(contexto, getResources().
                    getText(R.string.orden_ya_generada), Toast.LENGTH_SHORT).show();
            //  Intent intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

        OrdenConfirmada = true;
    }


    private void introducirFechaId() {
        ordenDeTrabajo = intent.getParcelableExtra("ordenDeTrabajo");
        ordenDeTrabajo.setId(codigo());
        ordenDeTrabajo.setFecha(fecha());

    }

    private void extraeDatosOrdenDeTrabajo() {
        fecha = ordenDeTrabajo.getFecha();
        id = ordenDeTrabajo.getId();
        prioridad = ordenDeTrabajo.getPrioridad();
        estado = ordenDeTrabajo.getSintoma();
        ubicacion = ordenDeTrabajo.getUbicacion();
        descripcion = ordenDeTrabajo.getDescripcion();
    }

    private void extraeDatosEmpleado() {
        datosEmpleado = intent.getParcelableExtra("datosEmplea");
        nombreOperario = datosEmpleado.getNombre();
        int codigoOperario = datosEmpleado.getCodigo();
        codigoOperarioString = Integer.toString(codigoOperario);
    }

    private String fecha() {
        String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String hora = new SimpleDateFormat("HH:mm").format(new Date());

        String fechaHora = fecha + "  " + hora;
        return fechaHora;
    }

    private String fechaPdf() {
        String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        return fecha;
    }

    private long codigo() {
        long codigo = System.currentTimeMillis();
        return codigo;
    }

    private void generarOrden() {
        textViewFecha.setText(getResources().getText(R.string.titulo1) + " " + fecha);
        textViewCodigo.setText(getResources().getText(R.string.titulo2) + " " + id);
        textViewNombreEmpleado.setText(getResources().getText(R.string.titulo3) + " " + nombreOperario);
        //  textViewCodigoEmpleado.setText(getResources().getText(R.string.titulo4) + " " + "****");
        textViewPrioridad.setText(getResources().getText(R.string.titulo5) + " " + prioridad);
        textViewEstado.setText(getResources().getText(R.string.titulo6) + " " + estado);
        textViewUbicacion.setText(getResources().getText(R.string.titulo7) + " " + ubicacion);
        textViewDescripcion.setText(getResources().getText(R.string.titulo8) + "\n" + descripcion);
    }

    private void cambioColor() {
        textViewFecha.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewCodigo.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewNombreEmpleado.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        // textViewCodigoEmpleado.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewPrioridad.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewEstado.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewUbicacion.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
        textViewDescripcion.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzul));
    }


    //--Generar texto para PDF----------------------------------------------------------------------

    private String generarTexto() {

        String fecha = textViewFecha.getText().toString();
        String id = textViewCodigo.getText().toString();
        String nombre = textViewNombreEmpleado.getText().toString();
        // String codigoEmpl = textViewCodigoEmpleado.getText().toString();
        String prioridad = textViewPrioridad.getText().toString();
        String estado = textViewEstado.getText().toString();
        String ubicacion = textViewUbicacion.getText().toString();
        String descripcion = textViewDescripcion.getText().toString();
        String autor = (String) getResources().getText(R.string.copy).toString();

        String texto = nombreApp + "\n\n" + fecha + "\n" + id + "\n" + nombre + "\n"
                //  + codigoEmpl + "\n"
                + prioridad + "\n" + estado + "\n" + ubicacion + "\n" + descripcion + "\n\n\n\n" + autor;
        return texto;
    }


    //--Generar  PDF--------------------------------------------------------------------------------

    public String crearPdf(String texto, OrdenDeTrabajo ot) {
        boolean errorPdf;
        Document document = new Document();
        String path = Environment.getExternalStorageDirectory()
                + File.separator
                + nombreApp
                + File.separator
                + fechaPdf()
                + File.separator
                + ot.getId()
                + File.separator
                + nombreApp
                + ot.getId() + ".pdf";
        carpetaApp = Utilidades.crearCarpetaApp();
        carpetaFecha = Utilidades.crearCarpetaFecha();
        carpetaOrden = Utilidades.crearCarpetaOrden(ot);
        Log.i("nachito","path fichero pdf"+path);

        try {

            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph(texto));
            document.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getResources().getString(R.string.toas_error_crear_fichero) + path, Toast.LENGTH_LONG).show();
            errorPdf = true;

        } catch (DocumentException e) {
            Toast.makeText(this, getResources().getString(R.string.toas_error_crear_fichero) + path, Toast.LENGTH_LONG).show();
            errorPdf = true;
        }
        return path;
    }

    public void muestraPdf(String archivo, Context contexto) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            contexto.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(contexto, getResources().getString(R.string.no_aplicacion_pdf).toString()
                    , Toast.LENGTH_LONG).show();
        }


    }

//---Hacer foto-------------------------------------------------------------------------------------

    private void hacerFoto(OrdenDeTrabajo ot) {
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
                    if (foto != null) {
                        ordenDeTrabajo.setImagen(foto);
                    }


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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

