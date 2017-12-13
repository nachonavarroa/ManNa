package com.example.nacho.manna.volley;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Constantes;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class ImagenVoley {

    final static String ruta =  Constantes.RUTA_SERVIDOR + "/imagenes/subir";

    //----------------------------------------------------------------------------------------------
    private static String convertirImgString(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);

        //Log.i("nachito", "ENTRO EN convertirImgString "+imgString);

        return imgString;
    }
    //----------------------------------------------------------------------------------------------

    public static void addImageServer(final Bitmap img, long IdOrden, Context context) {
        String url = ruta;
        String nombreFichero = "img_"+String.valueOf(IdOrden)+".jpg";
        final String field_name ="fichero";
        RequestQueue request = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put(field_name, convertirImgString(img));
                Log.i("nachito", "ENTRO EN getParams -hast->" + map.get("fichero").toString());

                return map;
            }
        };

      request.add(stringRequest);

    }

}
