package com.example.nacho.manna.volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Constantes;


public class ImagenVoley  extends Constantes {


    public static String imagenPath(Context context, long ordenId) {

        String imagePath = context.getFilesDir().getPath() + "/img_" + ordenId + ".jpg";

        return imagePath;
    }


    public static void subirImagenServidor(Context context, long ordenId) {

        String  imagenPath = imagenPath(context, ordenId);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, RUTA_SUBIR_IMAGENES_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
            }
        });

        smr.addFile("fichero", imagenPath);

        AppController.getInstance().addToRequestQueue(smr);

    }

}
