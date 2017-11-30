package com.example.nacho.mannadrawe.volley;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.nacho.mannadrawe.aplication.AppController;
import com.example.nacho.mannadrawe.auxiliar.Constantes;
import com.example.nacho.mannadrawe.crud.CrudBitacoraUsuario;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.sync.Sincronizacion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UsuarioVolley {

    final static String ruta = Constantes.RUTA_SERVIDOR + "/usuario";

    public static void getAllUsuario() {
        String tag_json_obj = "getAllUsuario";
        String url = ruta;

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Sincronizacion.actulizaTablaUsuarioSqliteConRespuestaServidor(response);
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

    public static void addUsuario(Usuario usuario, final boolean conBitacora,
                                  final int idBitacora) {
        String tag_json_obj = "addUsuario";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", usuario.getId());
            jsonObject.put("codigo_usuario", usuario.getCodigo());
            jsonObject.put("nombre", usuario.getNombre());
            jsonObject.put("admin", usuario.getAdmin());



        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraUsuario.delete(AppController.getResolvedor(), idBitacora);
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

    public static void updateUsuario(Usuario usuario, final boolean conBitacora,
                                   final int idBitacora) {
        String tag_json_obj = "updateUsuario";
        String url = ruta + "/" + usuario.getId();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", usuario.getId());
            jsonObject.put("codigo_usuario", usuario.getCodigo());
            jsonObject.put("nombre", usuario.getNombre());
            jsonObject.put("admin", usuario.getAdmin());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (conBitacora)
                            CrudBitacoraUsuario.delete(AppController.getResolvedor(), idBitacora);
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

    public static void deleteUsuario(int id, final boolean conBitacora, final int idBitacora) {
        String tag_json_obj = "deleteUsuario";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (conBitacora)
                            CrudBitacoraUsuario.delete(AppController.getResolvedor(), idBitacora);
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
