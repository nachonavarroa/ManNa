package com.example.nacho.manna.crud;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.pojos.BitacoraUsuario;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.proveedorDeContenido.DBHelper;
import com.example.nacho.manna.proveedorDeContenido.ProveedorContenido;
import com.example.nacho.manna.sync.Sincronizacion;

import java.util.ArrayList;


public class CrudUsuarios {

    static public Uri insert(ContentResolver resolvedor, Usuario usuario) {

        Uri uri = Contrato.Usuario.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Usuario._ID, usuario.getId());
        values.put(Contrato.Usuario.CODIGO_USUARIO, usuario.getCodigo());
        values.put(Contrato.Usuario.NOMBRE_USUARIO, usuario.getNombre());
        values.put(Contrato.Usuario.ADMIN_USUARIO, usuario.getAdmin());

        Uri uriResult = resolvedor.insert(uri, values);


        return uriResult;
    }


    static public void insertUsuarioConBitacora(ContentResolver resolvedor,
                                                Usuario usuario, Context contexto) {

        Uri uri = insert(resolvedor, usuario);

        BitacoraUsuario bitacora = new BitacoraUsuario();
        bitacora.setID_usuario(usuario.getId());
        bitacora.setOperacion(Constantes.OPERACION_INSERTAR);

        CrudBitacoraUsuario.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);

    }

    static public void delete(ContentResolver resolver, int usuarioId) {

        Uri uri = Uri.parse(Contrato.Usuario.CONTENT_URI + "/" + usuarioId);

        resolver.delete(uri, null, null);
    }

    static public void truncateTable(ContentResolver resolver) {

        Uri uri = Uri.parse(String.valueOf(Contrato.Usuario.CONTENT_URI));

        resolver.delete(uri, null, null);
    }

    static public void deleteUsuarioConBitacora(ContentResolver resolvedor, int usuarioId
            , Context contexto) {

        delete(resolvedor, usuarioId);
        BitacoraUsuario bitacora = new BitacoraUsuario();

        bitacora.setID_usuario(usuarioId);
        bitacora.setOperacion(Constantes.OPERACION_BORRAR);

        CrudBitacoraUsuario.insert(resolvedor, bitacora);
        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public void update(ContentResolver resolver, Usuario usuario) {

        Uri uri = Uri.parse(Contrato.Usuario.CONTENT_URI + "/" + usuario.getId());

        ContentValues values = new ContentValues();
        values.put(Contrato.Usuario.NOMBRE_USUARIO, usuario.getNombre());
        values.put(Contrato.Usuario.CODIGO_USUARIO, usuario.getCodigo());
        values.put(Contrato.Usuario.ADMIN_USUARIO, usuario.getAdmin());

        resolver.update(uri, values, null, null);

    }


    static public void updateSinCodigo(ContentResolver resolver, Usuario usuario) {

        Uri uri = Uri.parse(Contrato.Usuario.CONTENT_URI + "/" + usuario.getId());

        ContentValues values = new ContentValues();
        values.put(Contrato.Usuario.NOMBRE_USUARIO, usuario.getNombre());
        values.put(Contrato.Usuario.ADMIN_USUARIO, usuario.getAdmin());

        resolver.update(uri, values, null, null);

    }

    static public void updateUsuarioConBitacora(ContentResolver resolvedor, Usuario usuario,
                                                Context contexto) {

        update(resolvedor, usuario);

        BitacoraUsuario bitacora = new BitacoraUsuario();
        bitacora.setID_usuario(usuario.getId());
        bitacora.setOperacion(Constantes.OPERACION_MODIFICAR);

        CrudBitacoraUsuario.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);
    }

    static public void updateUsuarioSincodigoConBitacora(ContentResolver resolvedor, Usuario usuario,
                                                         Context contexto) {

        updateSinCodigo(resolvedor, usuario);

        BitacoraUsuario bitacora = new BitacoraUsuario();
        bitacora.setID_usuario(usuario.getId());
        bitacora.setOperacion(Constantes.OPERACION_MODIFICAR);

        CrudBitacoraUsuario.insert(resolvedor, bitacora);

        Sincronizacion.forzarSincronizacion(contexto);
    }


    static public Usuario readRecord(ContentResolver resolver, int usuarioId) {

        Uri uri = Uri.parse(Contrato.Usuario.CONTENT_URI + "/" + usuarioId);

        String[] projection = {
                Contrato.Usuario.NOMBRE_USUARIO,
                Contrato.Usuario.CODIGO_USUARIO,
                Contrato.Usuario.ADMIN_USUARIO
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {

            Usuario empleado = new Usuario();

            empleado.setId(usuarioId);
            empleado.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.NOMBRE_USUARIO)));
            empleado.setCodigo(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario.CODIGO_USUARIO)));
            empleado.setAdmin(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.ADMIN_USUARIO)));

            return empleado;
        }
        cursor.close();
        return null;
    }

    static public Usuario login(ContentResolver resolver,
                                String codigoEmpleado, String nombreEmpleado) {

        Uri uri = Contrato.Usuario.CONTENT_URI;
        String[] projection = {
                Contrato.Usuario.NOMBRE_USUARIO,
                Contrato.Usuario.CODIGO_USUARIO,
                Contrato.Usuario.ADMIN_USUARIO
        };
        String where = Contrato.Usuario.CODIGO_USUARIO
                + " = " + codigoEmpleado + " and "
                + Contrato.Usuario.NOMBRE_USUARIO
                + " like "
                + "'" + nombreEmpleado + "'";

        Cursor cursor = resolver.query(uri, projection, where, null, null);

        if (cursor.moveToFirst()) {

            Usuario empleado = new Usuario();

            empleado.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.NOMBRE_USUARIO)));
            empleado.setCodigo(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario.CODIGO_USUARIO)));
            empleado.setAdmin(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.ADMIN_USUARIO)));
            return empleado;
        }
        cursor.close();
        return null;
    }

    static public Usuario buscar(ContentResolver resolver, String codigoEmpleado) {

        Uri uri = Contrato.Usuario.CONTENT_URI;
        String[] projection = {
                Contrato.Usuario._ID,
                Contrato.Usuario.CODIGO_USUARIO,
                Contrato.Usuario.NOMBRE_USUARIO,
                Contrato.Usuario.ADMIN_USUARIO
        };
        String where = Contrato.Usuario.CODIGO_USUARIO
                + " = " + codigoEmpleado;

        Cursor cursor = resolver.query(uri, projection, where, null, null);

        if (cursor.moveToFirst()) {

            Usuario empleado = new Usuario();
            empleado.setId(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario._ID)));
            empleado.setCodigo(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario.CODIGO_USUARIO)));
            empleado.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.NOMBRE_USUARIO)));
            empleado.setAdmin(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.ADMIN_USUARIO)));
            return empleado;

        }
        cursor.close();
        return null;
    }


    static public String buscarUsuarioEnOrden(Context context, ContentResolver resolver, long idOrden) {

        DBHelper dbHelper = new DBHelper(context, ProveedorContenido.DATABASE_NAME,
                null, ProveedorContenido.DATABASE_VERSION);

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        final String query = "SELECT usuario.Nombre_Usuario FROM usuario INNER JOIN orden "
               + "ON ( usuario._id = orden.id_empleado ) and (orden._id =" + idOrden+")";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String nombreColumna = Contrato.Usuario.NOMBRE_USUARIO;
            int columna = cursor.getColumnIndex(nombreColumna);
            String nombre = cursor.getString(columna);
            return nombre;
        }
        cursor.close();
        return null;
    }

    static public ArrayList<Usuario> readAll(ContentResolver resolver) throws Exception {
        Uri uri = Contrato.Usuario.CONTENT_URI;

        String[] projection = {
                Contrato.Usuario._ID,
                Contrato.Usuario.CODIGO_USUARIO,
                Contrato.Usuario.NOMBRE_USUARIO,
                Contrato.Usuario.ADMIN_USUARIO
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Usuario> registros = new ArrayList<>();

        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();

            usuario.setId(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario._ID)));
            usuario.setCodigo(cursor.getInt(cursor.getColumnIndex(Contrato.Usuario.CODIGO_USUARIO)));
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.NOMBRE_USUARIO)));
            usuario.setAdmin(cursor.getString(cursor.getColumnIndex(Contrato.Usuario.ADMIN_USUARIO)));

            registros.add(usuario);
        }
        cursor.close();
        return registros;

    }

}
