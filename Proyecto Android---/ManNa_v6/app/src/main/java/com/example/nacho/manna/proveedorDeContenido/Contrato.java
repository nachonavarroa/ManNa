package com.example.nacho.manna.proveedorDeContenido;

import android.net.Uri;
import android.provider.BaseColumns;


public class Contrato {

    public final static String AUTHORITY = "com.example.nacho.manna" +
            ".proveedorDeContenido.ProveedorContenido";

    //Tabla Orden-----------------------------------------------------------------------------------

    public static class Orden implements BaseColumns {
        public final static String NOMBRE_TABLA = "Orden";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + NOMBRE_TABLA);
        // Table column

        public final static String ID_EMPLEADO = "id_empleado";
        public final static String FECHA = "Fecha";
        public final static String PRIORIDAD = "Prioridad";
        public final static String SINTOMA = "Sintoma";
        public final static String UBICACION = "Ubicacion";
        public final static String DESCRIPCION = "Descripcion";
        public final static String ESTADO = "Estado";
        // -------------------------------------------------------------

        public final static String _fecha = "Fecha";
        public final static String _codigoOrden = "Código";
        public final static String _prioridad_ = "Prioridad";
        public final static String _sintoma = "Síntoma";
        public final static String _ubicacion = "Ubicación";
        public final static String _descripcion = "Descripción";
        public final static String _estado = "Estado";
    }

    //Tabla Usuario---------------------------------------------------------------------------------
    public static class Usuario implements BaseColumns {

        public final static String NOMBRE_TABLA = "Usuario";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + NOMBRE_TABLA);
        // Table column
        public final static String CODIGO_USUARIO = "Codigo_usuario";
        public final static String NOMBRE_USUARIO = "Nombre_usuario";
        public final static String ADMIN_USUARIO = "Admin";

        // -------------------------------------------------------------
        public final static String _codigoUsuario = "Código usuario";
        public final static String _nombreUsuario = "Nombre usuario";
        public final static String _adminUsario = "Administrador";


    }

    //Tabla BitacoraOrden--------------------------------------------------------------------------------

    public static class BitacoraOrden implements BaseColumns {

        public final static String NOMBRE_TABLA = "BitacoraOrden";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + NOMBRE_TABLA);
        // Table column
        public final static String ID_ORDEN = "id_orden";
        public final static String OPERACION = "Operacion";


    }

    //Tabla BitacoraUsuario--------------------------------------------------------------------------------

    public static class BitacoraUsuario implements BaseColumns {

        public final static String NOMBRE_TABLA = "BitacoraUsuario";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + NOMBRE_TABLA);
        // Table column
        public final static String ID_USUARIO = "id_usuario";
        public final static String OPERACION = "Operacion";


    }

}
