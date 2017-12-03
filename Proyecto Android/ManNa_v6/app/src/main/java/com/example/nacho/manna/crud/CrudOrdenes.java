package com.example.nacho.manna.crud;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.pojos.BitacoraOrden;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.sync.Sincronizacion;

import java.io.IOException;
import java.util.ArrayList;


public class CrudOrdenes {

    static public Uri insert(ContentResolver resolvedor
            , OrdenDeTrabajo orden, Context context) {

        Uri uri = Contrato.Orden.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Contrato.Orden._ID,orden.getId());
        values.put(Contrato.Orden.CODIGO_EMPLEADO, orden.getCodigoEmpleado());
        values.put(Contrato.Orden.FECHA, orden.getFecha());
        values.put(Contrato.Orden.PRIORIDAD, orden.getPrioridad());
        values.put(Contrato.Orden.SINTOMA, orden.getSintoma());
        values.put(Contrato.Orden.UBICACION, orden.getUbicacion());
        values.put(Contrato.Orden.DESCRIPCION, orden.getDescripcion());
        values.put(Contrato.Orden.ESTADO, orden.getEstado());

        Uri uriResult = resolvedor.insert(uri, values);
       // String ordenId = uriResult.getLastPathSegment();

         String ordenId = String.valueOf(orden.getId());
        Log.i("Nachito","ordenId :"+ordenId);
        if (orden.getImagen() != null) {
            try {

                Utilidades.storeImage(orden.getImagen(), context, "img_" + ordenId + ".jpg");

            } catch (IOException e) {
                Toast.makeText(context, "No se puede guardar imagen", Toast.LENGTH_SHORT).show();
            }
        }
     return uriResult;
    }

    static public void insertOrdenConBitacora(ContentResolver resolvedor
            , OrdenDeTrabajo orden, Context contexto) {

        Uri uri = insert(resolvedor, orden,contexto);

        BitacoraOrden bitacora = new BitacoraOrden();
        bitacora.setID_Orden(orden.getId());
        bitacora.setOperacion(Constantes.OPERACION_INSERTAR);

        CrudBitacoraOrden.insert(resolvedor,bitacora);
      //  Log.i("Nachito","Uri :"+uri);

        Sincronizacion.forzarSincronizacion(contexto);

    }

    static public void delete(ContentResolver resolver, long ordenId) {

        Uri uri = Uri.parse(Contrato.Orden.CONTENT_URI + "/" + ordenId);

        resolver.delete(uri, null, null);

    }

    static public void truncateTable(ContentResolver resolver) {

        Uri uri = Uri.parse(String.valueOf(Contrato.Orden.CONTENT_URI));

        resolver.delete(uri, null, null);
    }


    static public void deleteOrdenConBitacora(ContentResolver resolvedor, long ordenId,
                                              Context contexto) {

        delete(resolvedor, ordenId);
        BitacoraOrden bitacora = new BitacoraOrden();

        bitacora.setID_Orden(ordenId);
        bitacora.setOperacion(Constantes.OPERACION_BORRAR);

        CrudBitacoraOrden.insert(resolvedor,bitacora);

        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public void update(ContentResolver resolver, OrdenDeTrabajo orden,Context context) {

        Uri uri = Uri.parse(Contrato.Orden.CONTENT_URI + "/" + orden.getId());

        ContentValues values = new ContentValues();

        values.put(Contrato.Orden.PRIORIDAD, orden.getPrioridad());
        values.put(Contrato.Orden.SINTOMA, orden.getSintoma());
        values.put(Contrato.Orden.UBICACION, orden.getUbicacion());
        values.put(Contrato.Orden.DESCRIPCION, orden.getDescripcion());
        values.put(Contrato.Orden.ESTADO, orden.getEstado());


        resolver.update(uri, values, null, null);

        if(orden.getImagen()!=null){
            try {
                Utilidades.storeImage(orden.getImagen(),context,"img_"+orden.getId()+".jpg");
            } catch (IOException e) {
                Toast.makeText(context, "No se puede guardar imagen", Toast.LENGTH_SHORT).show();
            }
        }


    }

    static public void updateOrdenConBitacora(ContentResolver resolvedor,
                                              OrdenDeTrabajo orden, Context contexto) {
        update( resolvedor, orden, contexto);

        BitacoraOrden bitacora = new BitacoraOrden();
        bitacora.setID_Orden(orden.getId());
        bitacora.setOperacion(Constantes.OPERACION_MODIFICAR);

        CrudBitacoraOrden.insert(resolvedor,bitacora);
        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public OrdenDeTrabajo readRecord(ContentResolver resolver, long ordenId) {

        Uri uri = Uri.parse(Contrato.Orden.CONTENT_URI + "/" + ordenId);

        String[] projection = {
                Contrato.Orden._ID,
                Contrato.Orden.CODIGO_EMPLEADO,
                Contrato.Orden.FECHA,
                Contrato.Orden.PRIORIDAD,
                Contrato.Orden.SINTOMA,
                Contrato.Orden.UBICACION,
                Contrato.Orden.DESCRIPCION,
                Contrato.Orden.ESTADO
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            OrdenDeTrabajo orden = new OrdenDeTrabajo();

            orden.setId(ordenId);
            orden.setCodigoEmpleado(cursor.getInt(cursor.getColumnIndex(Contrato.Orden.CODIGO_EMPLEADO)));
            orden.setFecha(cursor.getString(cursor.getColumnIndex(Contrato.Orden.FECHA)));
            orden.setPrioridad(cursor.getString(cursor.getColumnIndex(Contrato.Orden.PRIORIDAD)));
            orden.setSintoma(cursor.getString(cursor.getColumnIndex(Contrato.Orden.SINTOMA)));
            orden.setUbicacion(cursor.getString(cursor.getColumnIndex(Contrato.Orden.UBICACION)));
            orden.setDescripcion(cursor.getString(cursor.getColumnIndex(Contrato.Orden.DESCRIPCION)));
            orden.setEstado(cursor.getString(cursor.getColumnIndex(Contrato.Orden.ESTADO)));


            return orden;
        }
        return null;
    }

    static public ArrayList<OrdenDeTrabajo> readAll(ContentResolver resolver)  throws Exception{
        Uri uri = Contrato.Orden.CONTENT_URI;


        String[] projection = {
                Contrato.Orden._ID,
                Contrato.Orden.CODIGO_EMPLEADO,
                Contrato.Orden.FECHA,
                Contrato.Orden.PRIORIDAD,
                Contrato.Orden.SINTOMA,
                Contrato.Orden.UBICACION,
                Contrato.Orden.DESCRIPCION,
                Contrato.Orden.ESTADO

        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<OrdenDeTrabajo> registros = new ArrayList<>();

        while (cursor.moveToNext()){
            OrdenDeTrabajo orden = new OrdenDeTrabajo();

            orden.setId(cursor.getLong(cursor.getColumnIndex(Contrato.Orden._ID)));
            orden.setCodigoEmpleado(cursor.getInt(cursor.getColumnIndex(Contrato.Orden.CODIGO_EMPLEADO)));
            orden.setFecha(cursor.getString(cursor.getColumnIndex(Contrato.Orden.FECHA)));
            orden.setPrioridad(cursor.getString(cursor.getColumnIndex(Contrato.Orden.PRIORIDAD)));
            orden.setSintoma(cursor.getString(cursor.getColumnIndex(Contrato.Orden.SINTOMA)));
            orden.setUbicacion(cursor.getString(cursor.getColumnIndex(Contrato.Orden.UBICACION)));
            orden.setDescripcion(cursor.getString(cursor.getColumnIndex(Contrato.Orden.DESCRIPCION)));
            orden.setEstado(cursor.getString(cursor.getColumnIndex(Contrato.Orden.ESTADO)));
            registros.add(orden);

        }
        for(int i = 0; i<registros.size();i++){
      //  Log.i("sincronizacion","CrudOrden_readAll Id: "+ String.valueOf(registros.get(i).getId()));
      //  Log.i("sincronizacion","CrudOrden_readAll Codigo emple: "+String.valueOf(registros.get(i).getCodigoEmpleado()));
        }
        return registros;

    }





}
