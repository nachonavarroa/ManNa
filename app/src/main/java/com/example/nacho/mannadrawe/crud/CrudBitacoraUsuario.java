package com.example.nacho.mannadrawe.crud;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.nacho.mannadrawe.pojos.BitacoraUsuario;
import com.example.nacho.mannadrawe.proveedorDeContenido.Contrato;

import java.util.ArrayList;

public class CrudBitacoraUsuario {

    static public void insert(ContentResolver resolvedor, BitacoraUsuario bitacora) {

        Uri uri = Contrato.BitacoraUsuario.CONTENT_URI;
        ContentValues values = new ContentValues();

        values.put(Contrato.BitacoraUsuario.ID_USUARIO, bitacora.getID_usuario());
        values.put(Contrato.BitacoraUsuario.OPERACION, bitacora.getOperacion());

        resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int bitacoraId) {
        try {
            Uri uri = Uri.parse(Contrato.BitacoraUsuario.CONTENT_URI + "/" + bitacoraId);
            resolver.delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static public void update(ContentResolver resolver, BitacoraUsuario bitacora) {

        Uri uri = Uri.parse(Contrato.BitacoraUsuario.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.BitacoraUsuario.ID_USUARIO, bitacora.getID_usuario());
        values.put(Contrato.BitacoraUsuario.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);

    }

    static public BitacoraUsuario readRecord(ContentResolver resolver, int bitacoraId) {

        Uri uri = Uri.parse(Contrato.BitacoraUsuario.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {
                Contrato.BitacoraUsuario.ID_USUARIO,
                Contrato.BitacoraUsuario.OPERACION,
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            BitacoraUsuario Bitacora = new BitacoraUsuario();

            Bitacora.setID(bitacoraId);
            Bitacora.setID_usuario(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraUsuario.ID_USUARIO)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraUsuario.OPERACION)));

            return Bitacora;
        }
        return null;
    }

    static public ArrayList<BitacoraUsuario> readAll(ContentResolver resolver) {
        Uri uri = Contrato.BitacoraUsuario.CONTENT_URI;

        String[] projection = {
                Contrato.BitacoraUsuario._ID,
                Contrato.BitacoraUsuario.ID_USUARIO,
                Contrato.BitacoraUsuario.OPERACION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<BitacoraUsuario> bitacoras = new ArrayList<>();

        while (cursor.moveToNext()) {
            BitacoraUsuario bitacora = new BitacoraUsuario();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraUsuario._ID)));
            bitacora.setID_usuario(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraUsuario.ID_USUARIO)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraUsuario.OPERACION)));
            bitacoras.add(bitacora);
        }

        return bitacoras;

    }
}
