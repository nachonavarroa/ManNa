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
import android.util.Log;
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
import com.example.nacho.manna.activity.EditOrdenActivity;
import com.example.nacho.manna.activity.VerImagenActivity;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.sync.Sincronizacion;


public class VerOrdenesAdapter extends CursorAdapter {
    int color;
    ProgressBar progressBar;
    ImageView imageViewFoto;
    OrdenDeTrabajo orden;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public void bindView(View view, final Context context, Cursor cursor) {

        final long IdOrden = cursor.getLong(cursor.getColumnIndex(Contrato.Orden._ID));
        String fecha = cursor.getString(cursor.getColumnIndex(Contrato.Orden.FECHA));
        String prioridad = cursor.getString(cursor.getColumnIndex(Contrato.Orden.PRIORIDAD));
        String sintoma = cursor.getString(cursor.getColumnIndex(Contrato.Orden.SINTOMA));
        String ubicacion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.UBICACION));
        String descripcion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.DESCRIPCION));
        String estado = cursor.getString(cursor.getColumnIndex(Contrato.Orden.ESTADO));
        String referencia = Utilidades.referencia(IdOrden);
        String nombre = CrudUsuarios.buscarUsuarioEnOrden(context, context.getContentResolver(), IdOrden);


        TextView textViewRef = (TextView) view.findViewById(R.id.textView_item_orden_referencia);
        textViewRef.setText("Ref: " + referencia);

        TextView textViewFecha = (TextView) view.findViewById(R.id.textView_item_orden_fecha);
        textViewFecha.setText(Contrato.Orden._fecha + ": " + fecha);

        TextView textViewNombre = (TextView) view.findViewById(R.id.textView_item_orden_nombreEmpleado);
        textViewNombre.setText("Creado por: " + nombre);

        TextView textViewCodigo = (TextView) view.findViewById(R.id.textView_item_orden_codigo);
        textViewCodigo.setText(Contrato.Orden._codigoOrden + ": " + Long.toString(IdOrden));

        TextView textViewPrioridad = (TextView) view.findViewById(R.id.textView_item_orden_prioridad);
        textViewPrioridad.setText(Contrato.Orden._prioridad_ + ": " + prioridad);

        TextView textViewSintoma = (TextView) view.findViewById(R.id.textView_item_orden_sintoma);
        textViewSintoma.setText(Contrato.Orden._sintoma + ": " + sintoma);


        TextView textViewUbicacion = (TextView) view.findViewById(R.id.textView_item_orden_ubicacion);
        textViewUbicacion.setText(Contrato.Orden._ubicacion + ": " + ubicacion);

        TextView textViewDescripcion = (TextView) view.findViewById(R.id.textView_item_orden_descrripcion);
        textViewDescripcion.setText(Contrato.Orden._descripcion + ": " + descripcion);

        TextView textViewEstado = (TextView) view.findViewById(R.id.textView_item_orden_estado);
        textViewEstado.setText(Contrato.Orden._estado + ": " + estado);

        imageViewFoto = (ImageView) view.findViewById(R.id.imageView_item_orden_imgen);

        if (estado.equals("Pendiente")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorRojo));
            color = color = ContextCompat.getColor(context, R.color.colorRojo);
        }
        if (estado.equals("Proceso")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorAzul));
            color = ContextCompat.getColor(context, R.color.colorAzul);
        }

        if (estado.equals("Realizado")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorVerdeDark));
            color = ContextCompat.getColor(context, R.color.colorVerdeDark);
        }

//--------------------
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarImagenOrden);
        progressBar.setVisibility(View.GONE);

        orden = CrudOrdenes.readRecord(context.getContentResolver(), IdOrden);
      //  Log.i("Nachito","ID : "+IdOrden);

        int contieneImagen=0;
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
            Bitmap img =(Utilidades.loadImgeFromStorageReturnBitmap(context,"img_" + IdOrden + ".jpg"));
            if(img!=null) {
                progressBar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            imageViewFoto.setImageDrawable(null);
        }





//---------------------

        int lenghtRef = referencia.length();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(referencia.substring(0, lenghtRef), color);

        final ImageView image = (ImageView) view.findViewById(R.id.imageView_item_orden_abre_codigo);
        image.setImageDrawable(drawable);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sincronizacion.forzarSincronizacion(context);
                Intent intent = new Intent(context, EditOrdenActivity.class);
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


        view.setTag(IdOrden);

    }



    public static void recibirImagenesServidor() {


    }
}
