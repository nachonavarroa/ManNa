package com.example.nacho.manna.crud;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.nacho.manna.pojos.BitacoraOperarios;
import com.example.nacho.manna.proveedorDeContenido.Contrato;

import java.util.ArrayList;


public class CrudBitacoraOperarios {

    static public void insert(ContentResolver resolvedor, BitacoraOperarios bitacora) {

        Uri uri = Contrato.BitacoraOperarios.CONTENT_URI;
        ContentValues values = new ContentValues();

        values.put(Contrato.BitacoraOperarios.ID_OPERARIOS, bitacora.getID_Opererarios());
        values.put(Contrato.BitacoraOperarios.OPERACION, bitacora.getOperacion());

        resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int bitacoraId) {
        try {
            Uri uri = Uri.parse(Contrato.BitacoraOperarios.CONTENT_URI + "/" + bitacoraId);
            resolver.delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static public void update(ContentResolver resolver, BitacoraOperarios bitacora) {

        Uri uri = Uri.parse(Contrato.BitacoraOperarios.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.BitacoraOperarios.ID_OPERARIOS, bitacora.getID_Opererarios());
        values.put(Contrato.BitacoraOperarios.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);

    }

    static public BitacoraOperarios readRecord(ContentResolver resolver, int bitacoraId) {

        Uri uri = Uri.parse(Contrato.BitacoraOperarios.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {
                Contrato.BitacoraOperarios.ID_OPERARIOS,
                Contrato.BitacoraOperarios.OPERACION,
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            BitacoraOperarios Bitacora = new BitacoraOperarios();

            Bitacora.setID(bitacoraId);
            Bitacora.setID_Opererarios(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOperarios.ID_OPERARIOS)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOperarios.OPERACION)));

            return Bitacora;
        }
        return null;
    }

    static public ArrayList<BitacoraOperarios> readAll(ContentResolver resolver) {

        Uri uri = Contrato.BitacoraOperarios.CONTENT_URI;
        ArrayList<BitacoraOperarios> bitacoras = new ArrayList<>();

        String[] projection = {
                Contrato.BitacoraOperarios._ID,
                Contrato.BitacoraOperarios.ID_OPERARIOS,
                Contrato.BitacoraOperarios.OPERACION
        };

        try {
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            while (cursor.moveToNext()) {
                BitacoraOperarios bitacora = new BitacoraOperarios();
                bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOperarios._ID)));
                bitacora.setID_Opererarios(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOperarios.ID_OPERARIOS)));
                bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.BitacoraOperarios.OPERACION)));
                bitacoras.add(bitacora);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitacoras;

    }

}
