package com.example.nacho.manna.auxiliar;

/**
 * Created by Nacho on 22/11/2017.
 */

public class Constantes {

    public static final String NOMBRE_APP = "ManNa";

    public static final int DURACION_SPLASH = 1600;
    public static final long SYNC_INTERVAL = 300; // cada 5 minutos
    public static final String RUTA_SERVIDOR = "http://manna.3utilities.com/ManNa/resources";

    public  static final  String RUTA_SUBIR_IMAGENES_SERVIDOR = RUTA_SERVIDOR + "/imagenes/subir";
    public static final  String RUTA_DESCARGA_IMAGENES_SERVIDOR = RUTA_SERVIDOR + "/imagenes/descarga/";

    public static final int SIN_VALOR_INT = -1;
    public static final String SIN_VALOR_STRING = "";

    //Operaciones a guardar en la Bitácora
    public static final int OPERACION_INSERTAR = 0;
    public static final int OPERACION_MODIFICAR = 1;
    public static final int OPERACION_BORRAR = 2;

    //Operaciones a guardar camara
    public static final int SACAR_FOTO = 1;
    public static final int VER_GALRIA = 2;

    public static final int SI_CONTIENE_IMAGEN = 1;
    public static final int NO_CONTIENE_IMAGEN = -1;

}
