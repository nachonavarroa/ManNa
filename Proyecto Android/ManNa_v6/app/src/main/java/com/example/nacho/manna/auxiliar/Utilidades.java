package com.example.nacho.manna.auxiliar;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nacho.manna.R;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.pojos.Usuario;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.*;


public class Utilidades {

    final static String nombreApp = "ManNa";

    //------------------------------------------------
    //public   MultipartEntity multipartEntity


    //--------------------------------------------------------------------------------------------
    static public void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img)
            throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);
    }

    //----------------------------------------------------------------------------------------------
    static public Bitmap loadImgeFromStorageReturnBitmap(Context contexto, String imagenFichero)
            throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap img = BitmapFactory.decodeStream(new FileInputStream(f));

        return img;

    }

    public static String bitmapToString(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);

        Log.i("nachito", "ENTRO EN convertirImgString " + imgString);

        return imgString;
    }


    //----------------------------------------------------------------------------------------------
    static public void loadImageConPicassoFromStorage(Context contexto, String imagenFichero, ImageView img)
            throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Picasso.with(contexto).load(f).into(img);
    }

    static public void loadImageDesdeServidor(Context contexto, ImageView img, long Id) {
        Picasso.with(contexto)
                .load(Constantes.RUTA_SERVIDOR + "/imagenes/descarga/img_" + Id + ".jpg")
                .into(img);


    }


    //-------------------------------

    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {
        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }


    //-----------


    //---------------------

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

    //Permiso Administrador------------------------------------------------------------------------
    public static boolean permisoAdministrador(Context context) {
        boolean permisoAdministrador = false;
            int cofigoUsuario = AppController.getInstance().getCodigo();
            String nombreUsuario = AppController.getInstance().getNombre();
            Usuario usuarioConectado = CrudUsuarios.login(context.getContentResolver(),
                    String.valueOf(cofigoUsuario), nombreUsuario);

            if (usuarioConectado.getAdmin().equals(context.getResources().getText(R.string.string_si))) {
                permisoAdministrador = true;

            }

        return permisoAdministrador;
    }

    public static String referencia(Long id) {
        String referncia = String.valueOf((int) (id % 9999));
        return referncia;

    }
//Pedir permiso de escritura-----------------------------------------------------------------------

    public static  void pedirPermisos( Activity activity){
        int permissionCheck = ContextCompat.checkSelfPermission
                (activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        }
    }

    //borrar cache
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
