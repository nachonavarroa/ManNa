package com.example.nacho.manna.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nacho.manna.R;
import com.example.nacho.manna.activity.TareaActivity;
import com.example.nacho.manna.activity.VerImagenActivity;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudTarea;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.sync.Sincronizacion;

import java.util.ArrayList;


public class VerOrdenesAdapter extends CursorAdapter {
    int color;
    long IdOrden;
    String fecha, prioridad, sintoma, ubicacion, descripcion, estado, referencia, nombre, fechaInicioTarea,
            fechaFinTarea, trabajoRealizado, nombresOperariosTotal;
    ProgressBar progressBar;
    ImageView imageViewFoto, imageRef;
    OrdenDeTrabajo orden;

    TextView textViewCodigo, textViewRef, textViewFecha, textViewNombre, textViewPrioridad,
            textViewSintoma, textViewUbicacion, textViewDescripcion, textViewEstado, textViewFechaInicioTarea,
            textViewFechaFinTarea, textViewTrabajoRealizado, textViewOperarios;
    LinearLayout linearLayoutTrabajoRealizado;


    public VerOrdenesAdapter(Context context) {
        super(context, null, false);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_orden, parent, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bindView(v, context, cursor);
        }

        return v;
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        iniciarVistas(view);
        colores(context,cursor);
        setVistas();
        imagen(context, view);

        estadoPendiente(estado);
        estadoProceso(estado);
        view.setTag(IdOrden);
        listener(context,cursor);


    }

    //----------------------------------------------------------------------------------------------
    private void estadoPendiente(String estado) {
        if (estado.equals("Pendiente")) {
            linearLayoutTrabajoRealizado.setVisibility(View.GONE);
        }
    }

    private void estadoProceso(String estado) {
        if (estado.equals("Proceso")) {
            textViewFechaFinTarea.setVisibility(View.GONE);
            textViewTrabajoRealizado.setVisibility(View.GONE);
        }
    }


    private void listener(final Context context,Cursor cursor) {
        final long IdOrden = cursor.getLong(cursor.getColumnIndex(Contrato.Orden._ID));
        imageRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sincronizacion.forzarSincronizacion(context);
                Intent intent = new Intent(context, TareaActivity.class);
                intent.putExtra(Contrato.Orden._ID, IdOrden);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });


        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VerImagenActivity.class);
                intent.putExtra(Contrato.Orden._ID, IdOrden);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });


    }

    private void imagen(Context context, View view) {

        progressBar.setVisibility(View.GONE);

        orden = CrudOrdenes.readRecord(context.getContentResolver(), IdOrden);
        //  Log.i("Nachito","ID : "+IdOrden);

        int contieneImagen = 0;
        int imagenEnStorage = 0;

        try {
            contieneImagen = orden.getContieneImagen();
            Utilidades.loadImageFromStorage(context, "img_" + IdOrden + ".jpg", imageViewFoto);
            Bitmap imagenEnImageView = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();

            if (imagenEnImageView.getByteCount() > 0) {
                imagenEnStorage = 1;
            }

        } catch (Exception e) {
            imagenEnStorage = -1;

        }

        try {
            if (contieneImagen == Constantes.SI_CONTIENE_IMAGEN && imagenEnStorage == -1) {
                progressBar.setVisibility(View.VISIBLE);
                Utilidades.loadImageDesdeServidor(context, imageViewFoto, IdOrden);
                Bitmap bitmap = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();
                Utilidades.storeImage(bitmap, context, "img_" + IdOrden + ".jpg");
            }
            Bitmap img = (Utilidades.loadImgeFromStorageReturnBitmap(context, "img_" + IdOrden + ".jpg"));
            if (img != null) {
                progressBar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            imageViewFoto.setImageDrawable(null);
        }


    }

    private void colores(Context context,Cursor cursor) {

        iniciarString(context, cursor);

        if (estado.equals("Pendiente")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorRojo));
            color = ContextCompat.getColor(context, R.color.colorRojo);

        }
        if (estado.equals("Proceso")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorAzul));
            color = ContextCompat.getColor(context, R.color.colorAzul);

        }

        if (estado.equals("Realizado")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorVerdeDark));
            color = ContextCompat.getColor(context, R.color.colorVerdeDark);
        }


    }

    private void iniciarString(Context context, Cursor cursor) {
        IdOrden = cursor.getLong(cursor.getColumnIndex(Contrato.Orden._ID));
        fecha = cursor.getString(cursor.getColumnIndex(Contrato.Orden.FECHA));
        prioridad = cursor.getString(cursor.getColumnIndex(Contrato.Orden.PRIORIDAD));
        sintoma = cursor.getString(cursor.getColumnIndex(Contrato.Orden.SINTOMA));
        ubicacion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.UBICACION));
        descripcion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.DESCRIPCION));
        estado = cursor.getString(cursor.getColumnIndex(Contrato.Orden.ESTADO));
        referencia = Utilidades.referencia(IdOrden);
        nombre = CrudUsuarios.buscarUsuarioEnOrden(context, context.getContentResolver(), IdOrden);

        fechaInicioTarea = "Fecha de inicio: " + CrudUsuarios
                .buscarFechaInicioTarea(context, context.getContentResolver(), IdOrden);
        fechaFinTarea = "Fecha de finalización: " + CrudUsuarios
                .buscarFechaFinTarea(context, context.getContentResolver(), IdOrden);
        if (estado.equals("Realizado")) {
            trabajoRealizado = CrudTarea.readRecord(context.getContentResolver(), IdOrden).getDescripcion();
        }

        try {
            ArrayList<String> operarios = CrudUsuarios.buscarOperariosTarea(context, context.getContentResolver(), IdOrden);
            String nombreOperario;
            nombresOperariosTotal = "";
            for (int i = 0; i < operarios.size(); i++) {
                nombreOperario = operarios.get(i);
                nombresOperariosTotal = nombreOperario + ". " + nombresOperariosTotal;
            }
            nombresOperariosTotal = nombresOperariosTotal;


        } catch (Exception e) {
            //
        }

    }

    private void iniciarVistas(View view) {

        imageViewFoto = (ImageView) view.findViewById(R.id.imageView_item_orden_imgen);
        imageRef = (ImageView) view.findViewById(R.id.imageView_item_orden_abre_codigo);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarImagenOrden);

        textViewCodigo = (TextView) view.findViewById(R.id.textView_item_orden_codigo);
        textViewRef = (TextView) view.findViewById(R.id.textView_item_orden_referencia);
        textViewFecha = (TextView) view.findViewById(R.id.textView_item_orden_fecha);
        textViewNombre = (TextView) view.findViewById(R.id.textView_item_orden_nombreEmpleado);

        textViewPrioridad = (TextView) view.findViewById(R.id.textView_item_orden_prioridad);
        textViewSintoma = (TextView) view.findViewById(R.id.textView_item_orden_sintoma);
        textViewEstado = (TextView) view.findViewById(R.id.textView_item_orden_estado);
        textViewUbicacion = (TextView) view.findViewById(R.id.textView_item_orden_ubicacion);
        textViewDescripcion = (TextView) view.findViewById(R.id.textView_item_orden_descrripcion);

        textViewFechaInicioTarea = (TextView) view.findViewById(R.id.textView_item_orden_fecha_inicio);
        textViewFechaFinTarea = (TextView) view.findViewById(R.id.textView_item_orden_fecha_fin);
        textViewTrabajoRealizado = (TextView) view.findViewById(R.id.textView_item_orden_trabajoRealizado);
        textViewOperarios = (TextView) view.findViewById(R.id.textView_item_orden_operarios);

        linearLayoutTrabajoRealizado = (LinearLayout) view.findViewById(R.id.LL_item_orden_TrabajoRealizadoTarea);


    }

    private void setVistas() {
        int lenghtRef = referencia.length();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(referencia.substring(0, lenghtRef), color);

        imageRef.setImageDrawable(drawable);

        textViewCodigo.setText(Contrato.Orden._codigoOrden + ": " + Long.toString(IdOrden));
        textViewRef.setText("Ref: " + referencia);
        textViewFecha.setText(Contrato.Orden._fecha + ": " + fecha);
        textViewNombre.setText("Creado por: " + nombre);

        textViewPrioridad.setText(Contrato.Orden._prioridad_ + ": " + prioridad);
        textViewSintoma.setText(Contrato.Orden._sintoma + ": " + sintoma);
        textViewEstado.setText(Contrato.Orden._estado + ": " + estado);
        textViewUbicacion.setText(Contrato.Orden._ubicacion + ": " + ubicacion);
        textViewDescripcion.setText(Contrato.Orden._descripcion + ": " + descripcion);

        textViewFechaInicioTarea.setText(fechaInicioTarea);
        textViewFechaFinTarea.setText(fechaFinTarea);
        textViewTrabajoRealizado.setText("Trabajo realizado" + ": " + trabajoRealizado);

        textViewOperarios.setText("Operarios: " + nombresOperariosTotal);


    }


}
