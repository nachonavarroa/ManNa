package com.example.nacho.manna.crud;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.pojos.BitacoraOperarios;
import com.example.nacho.manna.pojos.Operarios;
import com.example.nacho.manna.pojos.Tarea;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.sync.Sincronizacion;

import java.util.ArrayList;


public class CrudOperarios {

    static public Uri insert(ContentResolver resolvedor, Operarios operarios) {

        Uri uri = Contrato.Operarios.CONTENT_URI;

        ContentValues values = new ContentValues();
      //  values.put(Contrato.Operarios._ID, operarios.get_id());
        values.put(Contrato.Operarios.ID_TAREA, operarios.getId_tarea());
        values.put(Contrato.Operarios.ID_USUARIO,operarios.getId_usuario());


        Uri uriResult = resolvedor.insert(uri, values);


        return uriResult;
    }


    static public void insertOperariosConBitacora(ContentResolver resolvedor,
                                                  Operarios operarios, Context contexto) {

        Uri uri = insert(resolvedor, operarios);

        BitacoraOperarios bitacora = new BitacoraOperarios();
        bitacora.setID_Opererarios(operarios.get_id());
        bitacora.setOperacion(Constantes.OPERACION_INSERTAR);

        CrudBitacoraOperarios.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);

    }

    static public void delete(ContentResolver resolver, int operariosId) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + operariosId);

        resolver.delete(uri, null, null);
    }



    static public void deleteOperariosConBitacora(ContentResolver resolvedor, int operariosId
            , Context contexto) {

        delete(resolvedor, operariosId);
        BitacoraOperarios bitacora = new BitacoraOperarios();

        bitacora.setID_Opererarios(operariosId);
        bitacora.setOperacion(Constantes.OPERACION_BORRAR);

        CrudBitacoraOperarios.insert(resolvedor, bitacora);
        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public void update(ContentResolver resolver, Operarios operarios) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + operarios.get_id());

        ContentValues values = new ContentValues();
        values.put(Contrato.Operarios.ID_TAREA, operarios.getId_tarea());
        values.put(Contrato.Operarios.ID_USUARIO,operarios.getId_usuario());


        resolver.update(uri, values, null, null);

    }




    static public void updateOperariosConBitacora(ContentResolver resolvedor,Operarios operarios,
                                                Context contexto) {

        update(resolvedor, operarios);

        BitacoraOperarios bitacora = new BitacoraOperarios();
        bitacora.setID_Opererarios(operarios.get_id());
        bitacora.setOperacion(Constantes.OPERACION_MODIFICAR);

        CrudBitacoraOperarios.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);
    }




    static public Operarios readRecord(ContentResolver resolver, int operariosId) {

        Uri uri = Uri.parse(Contrato.Tarea.CONTENT_URI + "/" + operariosId);

        String[] projection = {
                Contrato.Operarios.ID_TAREA,
                Contrato.Operarios.ID_USUARIO

        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            Operarios operarios = new Operarios();
            operarios.set_id(operariosId);
            operarios.setId_tarea(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios.ID_TAREA)));
            operarios.setId_usuario(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios.ID_USUARIO)));


            return operarios;
        }
      //  cursor.close();
        return null;
    }

    static public Operarios buscar(ContentResolver resolver,long id_tarea,int id_usuario) {

        Uri uri = Contrato.Operarios.CONTENT_URI;
        String[] projection = {
                Contrato.Operarios._ID,
                Contrato.Operarios.ID_TAREA,
                Contrato.Operarios.ID_USUARIO

        };
        String where = Contrato.Operarios.ID_TAREA
                + " = " + id_tarea +" and "+Contrato.Operarios.ID_USUARIO +" = "+id_usuario;

        Cursor cursor = resolver.query(uri, projection, where, null, null);

        if (cursor.moveToFirst()) {

            Operarios operarios = new Operarios();
            operarios.set_id(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios._ID)));
            operarios.setId_tarea(cursor.getLong(cursor.getColumnIndex(Contrato.Operarios.ID_TAREA)));
            operarios.setId_usuario(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios.ID_USUARIO)));

            return operarios;

        }
        //  cursor.close();
        return null;
    }




    static public ArrayList<Operarios> readAll(ContentResolver resolver) throws Exception {
        Uri uri = Contrato.Operarios.CONTENT_URI;

        String[] projection = {
                Contrato.Operarios._ID,
                Contrato.Operarios.ID_TAREA,
                Contrato.Operarios.ID_USUARIO

        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Operarios> registros = new ArrayList<>();

        while (cursor.moveToNext()) {
            Operarios operarios = new Operarios();

            operarios.set_id(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios._ID)));
            operarios.setId_tarea(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios.ID_TAREA)));
            operarios.setId_usuario(cursor.getInt(cursor.getColumnIndex(Contrato.Operarios.ID_USUARIO)));


            registros.add(operarios);
        }
     //   cursor.close();
        return registros;

    }

}
