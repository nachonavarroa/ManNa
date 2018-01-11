package com.example.nacho.manna.crud;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.pojos.BitacoraTarea;
import com.example.nacho.manna.pojos.Tarea;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.sync.Sincronizacion;

import java.util.ArrayList;


public class CrudTarea {

    static public Uri insert(ContentResolver resolvedor, Tarea tarea) {

        Uri uri = Contrato.Tarea.CONTENT_URI;

        ContentValues values = new ContentValues();

        values.put(Contrato.Tarea._ID,tarea.getId());
        values.put(Contrato.Tarea.ID_ORDEN, tarea.getId_Orden());
        values.put(Contrato.Tarea.FECHA_INICIO,tarea.getFecha_inicio());
        values.put(Contrato.Tarea.FECHA_FIN,tarea.getFecha_fin());
        values.put(Contrato.Tarea.DESCRIPCION,tarea.getDescripcion());

        Uri uriResult = resolvedor.insert(uri, values);

        return uriResult;
    }


    static public void insertTareaConBitacora(ContentResolver resolvedor,
                                               Tarea tarea, Context contexto) {

        Uri uri =insert(resolvedor,tarea);

        BitacoraTarea bitacora = new BitacoraTarea();
        bitacora.setID_Tarea(tarea.getId());
        bitacora.setOperacion(Constantes.OPERACION_INSERTAR);

        CrudBitacoraTarea.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);

    }

    static public void delete(ContentResolver resolver, long tareaId) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + tareaId);

        resolver.delete(uri, null, null);
    }



    static public void deleteTareaConBitacora(ContentResolver resolvedor, int tareaId
            , Context contexto) {

        delete(resolvedor, tareaId);
        BitacoraTarea bitacora = new BitacoraTarea();

        bitacora.setID_Tarea(tareaId);
        bitacora.setOperacion(Constantes.OPERACION_BORRAR);

        CrudBitacoraTarea.insert(resolvedor, bitacora);
        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public void update(ContentResolver resolver, Tarea tarea) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + tarea.getId());

        ContentValues values = new ContentValues();
        values.put(Contrato.Tarea.ID_ORDEN, tarea.getId_Orden());
        values.put(Contrato.Tarea.FECHA_INICIO,tarea.getFecha_inicio());
        values.put(Contrato.Tarea.FECHA_FIN,tarea.getFecha_fin());
        values.put(Contrato.Tarea.DESCRIPCION,tarea.getDescripcion());

        resolver.update(uri, values, null, null);

    }




    static public void updateTareaConBitacora(ContentResolver resolvedor,Tarea tarea,
                                                Context contexto) {

        update(resolvedor, tarea);

        BitacoraTarea bitacora = new BitacoraTarea();
        bitacora.setID_Tarea(tarea.getId());
        bitacora.setOperacion(Constantes.OPERACION_MODIFICAR);

        CrudBitacoraTarea.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);
    }




    static public Tarea readRecord(ContentResolver resolver, long tareaId) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + tareaId);

        String[] projection = {

                Contrato.Tarea.ID_ORDEN,
                Contrato.Tarea.FECHA_INICIO,
                Contrato.Tarea.FECHA_FIN,
                Contrato.Tarea.DESCRIPCION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            Tarea tarea = new Tarea();
            tarea.setId(tareaId);
            tarea.setId_Orden(cursor.getLong(cursor.getColumnIndex(Contrato.Tarea.ID_ORDEN)));
            tarea.setFecha_inicio(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.FECHA_INICIO)));
            tarea.setFecha_fin(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.FECHA_FIN)));
            tarea.setDescripcion(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.DESCRIPCION)));

            return tarea;
        }
      //  cursor.close();
        return null;
    }


    static public ArrayList<Tarea> readAll(ContentResolver resolver) throws Exception {
        Uri uri = Contrato.Tarea.CONTENT_URI;

        String[] projection = {
                Contrato.Tarea._ID,
                Contrato.Tarea.ID_ORDEN,
                Contrato.Tarea.FECHA_INICIO,
                Contrato.Tarea.FECHA_FIN,
                Contrato.Tarea.DESCRIPCION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Tarea> registros = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tarea tarea = new Tarea();

            tarea.setId(cursor.getLong(cursor.getColumnIndex(Contrato.Tarea._ID)));
            tarea.setId_Orden(cursor.getLong(cursor.getColumnIndex(Contrato.Tarea.ID_ORDEN)));
            tarea.setFecha_inicio(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.FECHA_INICIO)));
            tarea.setFecha_fin(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.FECHA_FIN)));
            tarea.setDescripcion(cursor.getString(cursor.getColumnIndex(Contrato.Tarea.DESCRIPCION)));

            registros.add(tarea);
        }
     //   cursor.close();
        return registros;

    }

}
