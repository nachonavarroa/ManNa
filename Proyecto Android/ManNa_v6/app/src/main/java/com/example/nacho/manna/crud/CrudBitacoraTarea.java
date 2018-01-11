package com.example.nacho.manna.crud;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.nacho.manna.pojos.BitacoraTarea;
import com.example.nacho.manna.proveedorDeContenido.Contrato;

import java.util.ArrayList;


public class CrudBitacoraTarea {

    static public void insert(ContentResolver resolvedor, BitacoraTarea bitacora) {

        Uri uri = Contrato.BitacoraTarea.CONTENT_URI;
        ContentValues values = new ContentValues();

        values.put(Contrato.BitacoraTarea.ID_TAREA, bitacora.getID_Tarea());
        values.put(Contrato.BitacoraTarea.OPERACION, bitacora.getOperacion());

        resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int bitacoraId) {
        try {
            Uri uri = Uri.parse(Contrato.BitacoraTarea.CONTENT_URI + "/" + bitacoraId);
            resolver.delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static public void update(ContentResolver resolver, BitacoraTarea bitacora) {

        Uri uri = Uri.parse(Contrato.BitacoraTarea.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.BitacoraTarea.ID_TAREA, bitacora.getID_Tarea());
        values.put(Contrato.BitacoraTarea.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);

    }

    static public BitacoraTarea readRecord(ContentResolver resolver, int bitacoraId) {

        Uri uri = Uri.parse(Contrato.BitacoraTarea.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {
                Contrato.BitacoraTarea.ID_TAREA,
                Contrato.BitacoraTarea.OPERACION,
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            BitacoraTarea Bitacora = new BitacoraTarea();

            Bitacora.setID(bitacoraId);
            Bitacora.setID_Tarea(cursor.getLong(cursor.getColumnIndex(Contrato.BitacoraTarea.ID_TAREA)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraTarea.OPERACION)));

            return Bitacora;
        }
        return null;
    }

    static public ArrayList<BitacoraTarea> readAll(ContentResolver resolver) {

        Uri uri = Contrato.BitacoraTarea.CONTENT_URI;
        ArrayList<BitacoraTarea> bitacoras = new ArrayList<>();

        String[] projection = {
                Contrato.BitacoraTarea._ID,
                Contrato.BitacoraTarea.ID_TAREA,
                Contrato.BitacoraTarea.OPERACION
        };

        try {
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            while (cursor.moveToNext()) {
                BitacoraTarea bitacora = new BitacoraTarea();
                bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraTarea._ID)));
                bitacora.setID_Tarea(cursor.getLong(cursor.getColumnIndex(Contrato.BitacoraTarea.ID_TAREA)));
                bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraTarea.OPERACION)));
                bitacoras.add(bitacora);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitacoras;

    }

}
