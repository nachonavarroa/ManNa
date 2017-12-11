package com.example.nacho.manna.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudBitacoraOrden;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OrdenVolley {

    final static String ruta = Constantes.RUTA_SERVIDOR + "/orden";


    public static void getAllOrden() {
        String tag_json_obj = "getAllOrden";
        String url = ruta;


        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Sincronizacion.actulizaTablaOrdenSqliteConRespuestaServidor(response);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(getRequest, tag_json_obj);
    }

    public static void addOrden(final OrdenDeTrabajo orden, final boolean conBitacora,
                                final int idBitacora) {
        String tag_json_obj = "addOrden";
        String url = ruta;


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orden.getId());
            jsonObject.put("idEmpleado", orden.getIdEmpleado());
            jsonObject.put("fecha", orden.getFecha());
            jsonObject.put("prioridad", orden.getPrioridad());
            jsonObject.put("sintoma", orden.getSintoma());
            jsonObject.put("ubicacion", orden.getUbicacion());
            jsonObject.put("descripcion", orden.getDescripcion());
            jsonObject.put("estado", orden.getEstado());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraOrden.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );

        //---------
        final String urlPostImg = Constantes.RUTA_SERVIDOR + "/imagenes/subir/";
        final String fieldName = "fichero";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPostImg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(fieldName, Utilidades.bitmapToString(orden.getImagen()));

                return super.getParams();
            }
        };


        //---------

        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }


    public static void updateOrden(OrdenDeTrabajo orden, final boolean conBitacora,
                                   final int idBitacora) {
        String tag_json_obj = "updateOrden";
        String url = ruta + "/" + orden.getId();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orden.getId());
            jsonObject.put("idEmpleado", orden.getIdEmpleado());
            jsonObject.put("fecha", orden.getFecha());
            jsonObject.put("prioridad", orden.getPrioridad());
            jsonObject.put("sintoma", orden.getSintoma());
            jsonObject.put("ubicacion", orden.getUbicacion());
            jsonObject.put("descripcion", orden.getDescripcion());
            jsonObject.put("estado", orden.getEstado());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraOrden.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void deleteOrden(long id, final boolean conBitacora, final int idBitacora) {
        String tag_json_obj = "deleteOrden";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (conBitacora)
                            CrudBitacoraOrden.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );

        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }
}
