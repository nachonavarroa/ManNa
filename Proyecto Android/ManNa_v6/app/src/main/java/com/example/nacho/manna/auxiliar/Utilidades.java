package com.example.nacho.manna.auxiliar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.example.nacho.manna.pojos.OrdenDeTrabajo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

  final static String nombreApp ="ManNa";
//--------------------------------------------------------------------------------------------
    static public void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img)
            throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);
    }

    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {
        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }



    //Generar carpetas de destino-------------------------------------------------------------------

    public static File crearCarpetaApp() {
        File carpetaManNan;
        carpetaManNan = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + nombreApp);
        if (!carpetaManNan.exists()) {
            carpetaManNan.mkdir();
            return carpetaManNan;
        }

        return null;
    }


    public static File crearCarpetaFecha() {
        File carpetaFecha;
        carpetaFecha = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + nombreApp
                + File.separator
                + fecha());
        if (!carpetaFecha.exists()) {
            carpetaFecha.mkdir();
            return carpetaFecha;
        }

        return null;
    }


    public static File crearCarpetaOrden(OrdenDeTrabajo ot) {
        File carpetaOrden;
        carpetaOrden = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + nombreApp
                + File.separator
                + fecha()
                + File.separator
                + ot.getId());
        if (!carpetaOrden.exists()) {
            carpetaOrden.mkdir();
            return carpetaOrden;
        }

        return null;
    }




//---------------------------------------------------------------------------------------
    private static String fecha() {
        String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return fecha;
    }


}
