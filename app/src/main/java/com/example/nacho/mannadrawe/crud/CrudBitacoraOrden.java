package com.example.nacho.mannadrawe.crud;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.nacho.mannadrawe.pojos.BitacoraOrden;
import com.example.nacho.mannadrawe.proveedorDeContenido.Contrato;

import java.util.ArrayList;


public class CrudBitacoraOrden {

    static public void insert(ContentResolver resolvedor, BitacoraOrden bitacora) {

        Uri uri = Contrato.BitacoraOrden.CONTENT_URI;
        ContentValues values = new ContentValues();

        values.put(Contrato.BitacoraOrden.ID_ORDEN, bitacora.getID_Orden());
        values.put(Contrato.BitacoraOrden.OPERACION, bitacora.getOperacion());

         resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int bitacoraId) {

        Uri uri = Uri.parse(Contrato.BitacoraOrden.CONTENT_URI + "/" + bitacoraId);
        resolver.delete(uri, null, null);
    }


    static public void update(ContentResolver resolver, BitacoraOrden bitacora) {

        Uri uri = Uri.parse(Contrato.BitacoraOrden.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.BitacoraOrden.ID_ORDEN, bitacora.getID_Orden());
        values.put(Contrato.BitacoraOrden.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);

    }

    static public BitacoraOrden readRecord(ContentResolver resolver, int bitacoraId) {

        Uri uri = Uri.parse(Contrato.BitacoraOrden.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {
                Contrato.BitacoraOrden.ID_ORDEN,
                Contrato.BitacoraOrden.OPERACION,
             };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            BitacoraOrden Bitacora = new BitacoraOrden();

            Bitacora.setID(bitacoraId);
            Bitacora.setID_Orden(cursor.getLong(cursor.getColumnIndex(Contrato.BitacoraOrden.ID_ORDEN)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOrden.OPERACION)));

            return Bitacora;
        }
        return null;
    }

    static public ArrayList<BitacoraOrden> readAll(ContentResolver resolver) {
        Uri uri = Contrato.BitacoraOrden.CONTENT_URI;

        String[] projection = {
                Contrato.BitacoraOrden._ID,
                Contrato.BitacoraOrden.ID_ORDEN,
                Contrato.BitacoraOrden.OPERACION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<BitacoraOrden> bitacoras = new ArrayList<>();

        while (cursor.moveToNext()){
            BitacoraOrden bitacora = new BitacoraOrden();
            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOrden._ID)));
            bitacora.setID_Orden(cursor.getLong(cursor.getColumnIndex(Contrato.BitacoraOrden.ID_ORDEN)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOrden.OPERACION)));
            bitacoras.add(bitacora);
        }

        return bitacoras;

    }

}
