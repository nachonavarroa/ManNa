package com.example.nacho.manna.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.crud.CrudBitacoraTarea;
import com.example.nacho.manna.pojos.Tarea;
import com.example.nacho.manna.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TareaVolley {

    final static String ruta = Constantes.RUTA_SERVIDOR + "/tarea";

    public static void getAllTarea() {
        String tag_json_obj = "getAllTarea";
        String url = ruta;

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Sincronizacion.actulizaTablaTareaSqliteConRespuestaServidor(response);
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

    public static void addTarea(Tarea tarea, final boolean conBitacora,
                                final int idBitacora) {
        String tag_json_obj = "addTarea";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tarea.getId());
            jsonObject.put("id_orden", tarea.getId_Orden());
            jsonObject.put("fecha_inicio", tarea.getFecha_inicio());
            jsonObject.put("fecha_fin", tarea.getFecha_fin());
            jsonObject.put("descripcion", tarea.getDescripcion());



        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraTarea.delete(AppController.getResolvedor(), idBitacora);
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

        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateTarea(Tarea tarea, final boolean conBitacora,
                                   final int idBitacora) {
        String tag_json_obj = "updateTarea";
        String url = ruta + "/" + tarea.getId();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tarea.getId());
            jsonObject.put("id_orden", tarea.getId_Orden());
            jsonObject.put("fecha_inicio", tarea.getFecha_inicio());
            jsonObject.put("fecha_fin", tarea.getFecha_fin());
            jsonObject.put("descripcion", tarea.getDescripcion());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraTarea.delete(AppController.getResolvedor(), idBitacora);
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

    public static void deleteTarea(long id, final boolean conBitacora, final int idBitacora) {
        String tag_json_obj = "deleteTarea";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (conBitacora)
                            CrudBitacoraTarea.delete(AppController.getResolvedor(), idBitacora);
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
