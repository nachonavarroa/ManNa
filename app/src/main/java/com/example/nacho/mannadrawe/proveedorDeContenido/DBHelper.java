package com.example.nacho.mannadrawe.proveedorDeContenido;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tabla Orden-------------------------------------------------------------------------------

        db.execSQL("CREATE TABLE "
                + Contrato.Orden.NOMBRE_TABLA
                + "(_id LONG PRIMARY KEY ON CONFLICT ROLLBACK, "
                + Contrato.Orden.CODIGO_EMPLEADO + " INTEGER ,"
                + Contrato.Orden.FECHA + " TEXT ,"
                + Contrato.Orden.PRIORIDAD + " TEXT , "
                + Contrato.Orden.SINTOMA + " TEXT , "
                + Contrato.Orden.UBICACION + " TEXT , "
                + Contrato.Orden.DESCRIPCION + " TEXT , "
                + Contrato.Orden.ESTADO + " TEXT "
                + "); "
        );

        //Tabla Usuario-----------------------------------------------------------------------------

        db.execSQL("CREATE TABLE "
                + Contrato.Usuario.NOMBRE_TABLA
                + "(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                + Contrato.Usuario.CODIGO_USUARIO + " INTEGER UNIQUE,"
                + Contrato.Usuario.NOMBRE_USUARIO + " TEXT ,"
                + Contrato.Usuario.ADMIN_USUARIO + " TEXT "
                + "); "
        );

        //Tabla BitacoraOrden----------------------------------------------------------------------------

        db.execSQL("CREATE TABLE "
                + Contrato.BitacoraOrden.NOMBRE_TABLA
                + "(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                + Contrato.BitacoraOrden.ID_ORDEN + " INTEGER ,"
                + Contrato.BitacoraOrden.OPERACION + " INTEGER "
                + "); "
        );

        //Tabla BitacoraUsuario----------------------------------------------------------------------------

        db.execSQL("CREATE TABLE "
                + Contrato.BitacoraUsuario.NOMBRE_TABLA
                + "(_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK , "
                + Contrato.BitacoraUsuario.ID_USUARIO + " INTEGER ,"
                + Contrato.BitacoraUsuario.OPERACION + " INTEGER "
                + "); "
        );

        inicializarDatos(db);

    }



    void inicializarDatos(SQLiteDatabase db) {

        db.execSQL("INSERT INTO " + Contrato.Usuario.NOMBRE_TABLA
                + " (" + Contrato.Usuario.CODIGO_USUARIO + ","
                + Contrato.Usuario.NOMBRE_USUARIO + ","
                + Contrato.Usuario.ADMIN_USUARIO + ") "
                + " VALUES (1234,'Admin', 'Si')");


//        db.execSQL("INSERT INTO " + Contrato.BitacoraUsuario.NOMBRE_TABLA
//                + " (" + Contrato.BitacoraUsuario._ID + ","
//                + Contrato.BitacoraUsuario.ID_USUARIO + ","
//                + Contrato.BitacoraUsuario.OPERACION + ") "
//                + " VALUES (1, 1, 0)");

    }


//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        db.execSQL("PRAGMA foreign_keys=On;");
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Contrato.Orden.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.Usuario.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.BitacoraOrden.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.BitacoraUsuario.NOMBRE_TABLA);

        onCreate(db);
    }


}
