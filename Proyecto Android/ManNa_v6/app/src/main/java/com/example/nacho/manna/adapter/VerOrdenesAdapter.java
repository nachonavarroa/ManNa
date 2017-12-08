package com.example.nacho.manna.adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nacho.manna.activity.CrearUsuarioActivity;
import com.example.nacho.manna.activity.EditOrdenActivity;
import com.example.nacho.manna.activity.EditUsuarioActivity;
import com.example.nacho.manna.activity.VerImagen;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudBitacoraOrden;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.R;
import com.example.nacho.manna.sync.Sincronizacion;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


public class VerOrdenesAdapter extends CursorAdapter {
    int color;
    PhotoViewAttacher mAttacher;

    public VerOrdenesAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_orden, parent, false);
        bindView(v, context, cursor);

        return v;
    }

    @Override

    public void bindView(View view, final Context context, Cursor cursor) {

        final long Id = cursor.getLong(cursor.getColumnIndex(Contrato.Orden._ID));
        String fecha = cursor.getString(cursor.getColumnIndex(Contrato.Orden.FECHA));
        String prioridad = cursor.getString(cursor.getColumnIndex(Contrato.Orden.PRIORIDAD));
        String sintoma = cursor.getString(cursor.getColumnIndex(Contrato.Orden.SINTOMA));
        String ubicacion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.UBICACION));
        String descripcion = cursor.getString(cursor.getColumnIndex(Contrato.Orden.DESCRIPCION));
        String estado = cursor.getString(cursor.getColumnIndex(Contrato.Orden.ESTADO));
        String referencia =String.valueOf ((int) (Id % 9999));


        TextView textViewRef = (TextView) view.findViewById(R.id.textView_item_orden_referencia);
        textViewRef.setText("Ref: " + referencia);

        TextView textViewFecha = (TextView) view.findViewById(R.id.textView_item_orden_fecha);
        textViewFecha.setText(Contrato.Orden._fecha + ": " + fecha);

        TextView textViewCodigo = (TextView) view.findViewById(R.id.textView_item_orden_codigo);
        textViewCodigo.setText(Contrato.Orden._codigoOrden + ": " + Long.toString(Id));

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


        if (estado.equals("Pendiente")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorRojo));
            color =  color = ContextCompat.getColor(context, R.color.colorRojo);
        }
        if (estado.equals("Proceso")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorAzul));
            color = ContextCompat.getColor(context, R.color.colorAzul);
        }

        if (estado.equals("Realizado")) {
            textViewEstado.setTextColor(ContextCompat.getColor(context, R.color.colorVerdeDark));
            color =ContextCompat.getColor(context, R.color.colorVerdeDark);
        }



        ImageView imageViewfoto = (ImageView) view.findViewById(R.id.imageView_item_orden_imgen);

        try {
          //  Utilidades.loadImageFromStorage(context, "img_" + Id + ".jpg", imageViewfoto);
              Utilidades.loadDesdeServidor(context,imageViewfoto,Id);

        } catch (Exception e) {
            imageViewfoto.setImageDrawable(null);

        }

       int lenghtRef = referencia.length();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(referencia .substring(0, lenghtRef), color);

        final ImageView image = (ImageView) view.findViewById(R.id.imageView_item_orden_abre_codigo);
        image.setImageDrawable(drawable);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sincronizacion.forzarSincronizacion(context);
                Intent intent = new Intent(context, EditOrdenActivity.class);
                intent.putExtra(Contrato.Orden._ID,Id);
                context.startActivity(intent);
              }
        });

        mAttacher = new PhotoViewAttacher(imageViewfoto);

//        imageViewfoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, VerImagen.class);
//                intent.putExtra(Contrato.Orden._ID,Id);
//                context.startActivity(intent);
//
//            }
//        });


        view.setTag(Id);

    }
}
