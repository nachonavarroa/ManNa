package com.example.nacho.manna.proveedorDeContenido;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;


public class ProveedorContenido extends ContentProvider {

    private final static int ORDENES_ONE_REG = 1;
    private final static int ORDENES_ALL_REGS = 2;

    private final static int USUARIO_ONE_REG = 3;
    private final static int USUARIO_ALL_REGS = 4;

    private final static int TAREA_ONE_REG = 5;
    private final static int TAREA_ALL_REGS = 6;

    private final static int OPERARIOS_ONE_REG = 7;
    private final static int OPERARIOS_ALL_REGS = 8;

    private final static int BITACORA_ORDEN_ONE_REG = 9;
    private final static int BITACORA_ORDEN_ALL_REGS = 10;

    private final static int BITACORA_USUARIO_ONE_REG = 11;
    private final static int BITACORA_USUARIO_ALL_REGS = 12;

    private final static int BITACORA_TAREA_ONE_REG = 13;
    private final static int BITACORA_TAREA_ALL_REGS = 14;

    private final static int BITACORA_OPERARIOS_ONE_REG = 15;
    private final static int BITACORA_OPERARIOS_ALL_REGS = 16;


    private SQLiteDatabase sqLiteDatabase;
    public DBHelper dbHelper;

    public static final String DATABASE_NAME = "Manna.db";
    public static final int DATABASE_VERSION = 692;

    private static final String ORDEN_TABLE_NAME = Contrato.Orden.NOMBRE_TABLA;
    private static final String USUARIO_TABLE_NAME = Contrato.Usuario.NOMBRE_TABLA;

    private static final String TAREA_TABLE_NAME = Contrato.Tarea.NOMBRE_TABLA;
    private static final String OPRERARIOS_TABLE_NAME = Contrato.Operarios.NOMBRE_TABLA;

    private static final String BITACORA_ORDEN_TABLE_NAME = Contrato.BitacoraOrden.NOMBRE_TABLA;
    private static final String BITACORA_USUARIO_TABLE_NAME = Contrato.BitacoraUsuario.NOMBRE_TABLA;

    private static final String BITACORA_TAREA_TABLE_NAME = Contrato.BitacoraTarea.NOMBRE_TABLA;
    private static final String BITACORA_OPERARIOS_TABLE_NAME = Contrato.BitacoraOperarios.NOMBRE_TABLA;

    private static final UriMatcher mUriMatcher = new UriMatcher(0);
    private static final SparseArray<String> sMimeTypes;


    static {
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Orden.NOMBRE_TABLA, ORDENES_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Orden.NOMBRE_TABLA + "/#", ORDENES_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Usuario.NOMBRE_TABLA, USUARIO_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Usuario.NOMBRE_TABLA + "/#", USUARIO_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Tarea.NOMBRE_TABLA, TAREA_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Tarea.NOMBRE_TABLA + "/#", TAREA_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Operarios.NOMBRE_TABLA, OPERARIOS_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.Operarios.NOMBRE_TABLA + "/#", OPERARIOS_ALL_REGS);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraOrden.NOMBRE_TABLA, BITACORA_ORDEN_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraOrden.NOMBRE_TABLA + "/#", BITACORA_ORDEN_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraUsuario.NOMBRE_TABLA, BITACORA_USUARIO_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraUsuario.NOMBRE_TABLA + "/#", BITACORA_USUARIO_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraTarea.NOMBRE_TABLA, BITACORA_TAREA_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraTarea.NOMBRE_TABLA + "/#", BITACORA_TAREA_ONE_REG);

        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraOperarios.NOMBRE_TABLA, BITACORA_OPERARIOS_ALL_REGS);
        mUriMatcher.addURI(Contrato.AUTHORITY, Contrato.BitacoraOperarios.NOMBRE_TABLA + "/#", BITACORA_OPERARIOS_ONE_REG);

        sMimeTypes = new SparseArray<String>();

        sMimeTypes.put(ORDENES_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Orden.NOMBRE_TABLA);

        sMimeTypes.put(ORDENES_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Orden.NOMBRE_TABLA);

        sMimeTypes.put(USUARIO_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Usuario.NOMBRE_TABLA);

        sMimeTypes.put(USUARIO_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Usuario.NOMBRE_TABLA);

        sMimeTypes.put(TAREA_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Tarea.NOMBRE_TABLA);

        sMimeTypes.put(TAREA_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Tarea.NOMBRE_TABLA);

        sMimeTypes.put(OPERARIOS_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Operarios.NOMBRE_TABLA);

        sMimeTypes.put(OPERARIOS_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.Operarios.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_ORDEN_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraOrden.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_ORDEN_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraOrden.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_USUARIO_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraUsuario.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_USUARIO_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraUsuario.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_TAREA_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraTarea.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_TAREA_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraTarea.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_OPERARIOS_ALL_REGS, "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraOperarios.NOMBRE_TABLA);

        sMimeTypes.put(BITACORA_OPERARIOS_ONE_REG, "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "."
                + Contrato.BitacoraOperarios.NOMBRE_TABLA);
    }

    public ProveedorContenido() {

    }


    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);

        return (dbHelper == null ? false : true);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (mUriMatcher.match(uri)) {
            case ORDENES_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Orden._ID + " = "
                        + uri.getLastPathSegment();
                table = ORDEN_TABLE_NAME;
                break;

            case ORDENES_ALL_REGS:
                table = ORDEN_TABLE_NAME;
                break;
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuario._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIO_TABLE_NAME;
                break;
            case USUARIO_ALL_REGS:
                table = USUARIO_TABLE_NAME;
                break;
            case TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Tarea._ID + " = "
                        + uri.getLastPathSegment();
                table = TAREA_TABLE_NAME;
                break;
            case TAREA_ALL_REGS:
                table = TAREA_TABLE_NAME;
                break;
            case OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Operarios._ID + " = "
                        + uri.getLastPathSegment();
                table = OPRERARIOS_TABLE_NAME;
                break;
            case OPERARIOS_ALL_REGS:
                table = OPRERARIOS_TABLE_NAME;
                break;

            case BITACORA_ORDEN_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOrden._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_ORDEN_TABLE_NAME;
                break;
            case BITACORA_ORDEN_ALL_REGS:
                table = BITACORA_ORDEN_TABLE_NAME;
                break;
            case BITACORA_USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraUsuario._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_USUARIO_TABLE_NAME;
                break;
            case BITACORA_USUARIO_ALL_REGS:
                table = BITACORA_USUARIO_TABLE_NAME;
                break;
            case BITACORA_TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraTarea._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TAREA_TABLE_NAME;
                break;
            case BITACORA_TAREA_ALL_REGS:
                table = BITACORA_TAREA_TABLE_NAME;
                break;
            case BITACORA_OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOperarios._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_OPERARIOS_TABLE_NAME;
                break;
            case BITACORA_OPERARIOS_ALL_REGS:
                table = BITACORA_OPERARIOS_TABLE_NAME;
                break;

        }
        int rows = sqLiteDatabase.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        String table = "";
        switch (mUriMatcher.match(uri)) {
            case ORDENES_ALL_REGS:
                table = Contrato.Orden.NOMBRE_TABLA;
                break;
            case USUARIO_ALL_REGS:
                table = Contrato.Usuario.NOMBRE_TABLA;
                break;
            case TAREA_ALL_REGS:
                table = Contrato.Tarea.NOMBRE_TABLA;
                break;
            case OPERARIOS_ALL_REGS:
                table = Contrato.Operarios.NOMBRE_TABLA;
                break;
            case BITACORA_ORDEN_ALL_REGS:
                table = Contrato.BitacoraOrden.NOMBRE_TABLA;
                break;
            case BITACORA_USUARIO_ALL_REGS:
                table = Contrato.BitacoraUsuario.NOMBRE_TABLA;
                break;
            case BITACORA_TAREA_ALL_REGS:
                table = Contrato.BitacoraTarea.NOMBRE_TABLA;
                break;
            case BITACORA_OPERARIOS_ALL_REGS:
                table = Contrato.BitacoraOperarios.NOMBRE_TABLA;
                break;
        }

        long rowId = sqLiteDatabase.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (mUriMatcher.match(uri)) {
            case ORDENES_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Orden._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.Orden.NOMBRE_TABLA);

                break;
            case ORDENES_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.Orden.ESTADO + " asc";
                qb.setTables(Contrato.Orden.NOMBRE_TABLA);

                break;
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuario._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.Usuario.NOMBRE_TABLA);

                break;
            case USUARIO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.Usuario.NOMBRE_USUARIO + " asc";
                qb.setTables(Contrato.Usuario.NOMBRE_TABLA);

                break;

            case TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Tarea._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.Tarea.NOMBRE_TABLA);

                break;
            case TAREA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.Tarea.ID_ORDEN + " asc";
                qb.setTables(Contrato.Tarea.NOMBRE_TABLA);

                break;
            case OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Operarios._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.Operarios.NOMBRE_TABLA);

                break;
            case OPERARIOS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = Contrato.Operarios._ID+ " asc";
                qb.setTables(Contrato.Operarios.NOMBRE_TABLA);

                break;

            case BITACORA_ORDEN_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOrden._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.BitacoraOrden.NOMBRE_TABLA);

                break;
            case BITACORA_ORDEN_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.BitacoraOrden.OPERACION + " asc";
                qb.setTables(Contrato.BitacoraOrden.NOMBRE_TABLA);

                break;

            case BITACORA_USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraUsuario._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.BitacoraUsuario.NOMBRE_TABLA);

                break;
            case BITACORA_USUARIO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.BitacoraUsuario.OPERACION + " asc";
                qb.setTables(Contrato.BitacoraUsuario.NOMBRE_TABLA);

                break;

            case BITACORA_TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraTarea._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.BitacoraTarea.NOMBRE_TABLA);

                break;
            case BITACORA_TAREA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.BitacoraTarea.OPERACION + " asc";
                qb.setTables(Contrato.BitacoraTarea.NOMBRE_TABLA);

                break;
            case BITACORA_OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOperarios._ID + " = " + uri.getLastPathSegment();
                qb.setTables(Contrato.BitacoraOperarios.NOMBRE_TABLA);

                break;
            case BITACORA_OPERARIOS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.BitacoraOperarios.OPERACION + " asc";
                qb.setTables(Contrato.BitacoraOperarios.NOMBRE_TABLA);

                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (mUriMatcher.match(uri)) {
            case ORDENES_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Orden._ID + " = "
                        + uri.getLastPathSegment();
                table = ORDEN_TABLE_NAME;
                break;
            case ORDENES_ALL_REGS:
                table = ORDEN_TABLE_NAME;
                break;
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuario._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIO_TABLE_NAME;
                break;
            case USUARIO_ALL_REGS:
                table = USUARIO_TABLE_NAME;
                break;
            case TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Tarea._ID + " = "
                        + uri.getLastPathSegment();
                table = TAREA_TABLE_NAME;
                break;
            case TAREA_ALL_REGS:
                table = TAREA_TABLE_NAME;
                break;

            case OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Operarios._ID + " = "
                        + uri.getLastPathSegment();
                table = OPRERARIOS_TABLE_NAME;
                break;
            case OPERARIOS_ALL_REGS:
                table = OPRERARIOS_TABLE_NAME;
                break;

            case BITACORA_ORDEN_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOrden._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_ORDEN_TABLE_NAME;
                break;
            case BITACORA_ORDEN_ALL_REGS:
                table = BITACORA_ORDEN_TABLE_NAME;
                break;
            case BITACORA_USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraUsuario._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_USUARIO_TABLE_NAME;
                break;
            case BITACORA_USUARIO_ALL_REGS:
                table = BITACORA_USUARIO_TABLE_NAME;
                break;
            case BITACORA_TAREA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraTarea._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TAREA_TABLE_NAME;
                break;
            case BITACORA_TAREA_ALL_REGS:
                table = BITACORA_TAREA_TABLE_NAME;
                break;
            case BITACORA_OPERARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.BitacoraOperarios._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_OPERARIOS_TABLE_NAME;
                break;
            case BITACORA_OPERARIOS_ALL_REGS:
                table = BITACORA_OPERARIOS_TABLE_NAME;
                break;
        }

        int rows = sqLiteDatabase.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);
    }


}
