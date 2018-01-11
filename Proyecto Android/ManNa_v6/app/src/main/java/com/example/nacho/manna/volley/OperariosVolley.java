package com.example.nacho.manna.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.crud.CrudBitacoraOperarios;
import com.example.nacho.manna.pojos.Operarios;
import com.example.nacho.manna.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OperariosVolley {

    final static String ruta = Constantes.RUTA_SERVIDOR + "/operarios";

    public static void getAllOperarios() {
        String tag_json_obj = "getAllOperarios";
        String url = ruta;

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Sincronizacion.actulizaTablaOperariosSqliteConRespuestaServidor(response);
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

    public static void addOperarios(Operarios operarios, final boolean conBitacora,
                                    final int idBitacora) {
        String tag_json_obj = "addOperarios";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", operarios.get_id());
            jsonObject.put("id_tarea", operarios.getId_tarea());
            jsonObject.put("id_usuario", operarios.getId_usuario());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraOperarios.delete(AppController.getResolvedor(), idBitacora);
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

    public static void updateOperarios(Operarios operarios, final boolean conBitacora,
                                   final int idBitacora) {
        String tag_json_obj = "updateOperarios";
        String url = ruta + "/" + operarios.get_id();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", operarios.get_id());
            jsonObject.put("id_tarea", operarios.getId_tarea());
            jsonObject.put("id_usuario", operarios.getId_usuario());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraOperarios.delete(AppController.getResolvedor(), idBitacora);
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

    public static void deleteOperarios(int id, final boolean conBitacora, final int idBitacora) {
        String tag_json_obj = "deleteOperarios";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (conBitacora)
                            CrudBitacoraOperarios.delete(AppController.getResolvedor(), idBitacora);
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
