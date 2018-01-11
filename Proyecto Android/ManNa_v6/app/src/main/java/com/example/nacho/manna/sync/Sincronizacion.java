package com.example.nacho.manna.sync;


import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.example.nacho.manna.auxiliar.Constantes;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.crud.CrudBitacoraOperarios;
import com.example.nacho.manna.crud.CrudBitacoraOrden;
import com.example.nacho.manna.crud.CrudBitacoraTarea;
import com.example.nacho.manna.crud.CrudBitacoraUsuario;
import com.example.nacho.manna.crud.CrudOperarios;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.crud.CrudTarea;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.BitacoraOperarios;
import com.example.nacho.manna.pojos.BitacoraOrden;
import com.example.nacho.manna.pojos.BitacoraTarea;
import com.example.nacho.manna.pojos.BitacoraUsuario;
import com.example.nacho.manna.pojos.Operarios;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.example.nacho.manna.pojos.Tarea;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.volley.OperariosVolley;
import com.example.nacho.manna.volley.OrdenVolley;
import com.example.nacho.manna.volley.TareaVolley;
import com.example.nacho.manna.volley.UsuarioVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sincronizacion {

    private static final String LOGTAG = "Nacho- Sincronizacion";
    private static ContentResolver resolvedor;
    private static Context contexto;
    private static boolean esperandoRespuestaDeServidor = false;

    public Sincronizacion(Context contexto) {
        this.resolvedor = contexto.getContentResolver();
        this.contexto = contexto;

        //  recibirActualizacionesDelServidor();
        // La primera vez se cargan los datos siempre
    }


    public synchronized static boolean isEsperandoRespuestaDeServidor() {
        return esperandoRespuestaDeServidor;
    }

    public synchronized static void setEsperandoRespuestaDeServidor(boolean esperandoRespuestaDeServidor) {
        Sincronizacion.esperandoRespuestaDeServidor = esperandoRespuestaDeServidor;
    }

    public synchronized boolean sincronizar(Context context) {
        //   Log.i("sincronizacion", "SINCRONIZAR");

        Utilidades.deleteCache(context);

        if (isEsperandoRespuestaDeServidor()) {
            return true;
        }

        enviarActualizacionesAlServidor();
        recibirActualizacionesDelServidor();


        //filtro
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void forzarSincronizacion(Context contexto) {

        Sincronizacion sync = new Sincronizacion(contexto);
        sync.sincronizar(contexto);

    }

    public synchronized static void enviarActualizacionesAlServidor() {
        enviarActualizacionesOrdenAlServidor();
        enviarActualizacionesUsuarioAlServidor();
        enviarActualizacionesTareaAlServidor();
        enviarActualizacionesOperariosAlServidor();
    }

    public synchronized static void recibirActualizacionesDelServidor() {

        recibirActualizacionesOrdenDelServidor();
        recibirActualizacionesUsuarioDelServidor();
        recibirActualizacionesTareaDelServidor();
        recibirActualizacionesOperariosDelServidor();


    }

    public static void enviarActualizacionesOperariosAlServidor() {
        ArrayList<BitacoraOperarios> registrosBitacora = CrudBitacoraOperarios.readAll(resolvedor);
        for (BitacoraOperarios bitacora : registrosBitacora) {
            Operarios operarios = null;
            switch (bitacora.getOperacion()) {
                case Constantes.OPERACION_INSERTAR:
                    try {
                        operarios = CrudOperarios.readRecord(resolvedor, bitacora.getID_Opererarios());
                        OperariosVolley.addOperarios(operarios, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_MODIFICAR:
                    try {
                        operarios = CrudOperarios.readRecord(resolvedor, bitacora.getID_Opererarios());
                        OperariosVolley.updateOperarios(operarios, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_BORRAR:
                    OperariosVolley.deleteOperarios( bitacora.getID_Opererarios(), true, bitacora.getID());
                    break;
            }
            //  Log.i(LOGTAG, "acabo de enviar");
        }

    }

    public static void enviarActualizacionesTareaAlServidor() {
        ArrayList<BitacoraTarea> registrosBitacora = CrudBitacoraTarea.readAll(resolvedor);
        for (BitacoraTarea bitacora : registrosBitacora) {
            Tarea tarea = null;
            switch (bitacora.getOperacion()) {
                case Constantes.OPERACION_INSERTAR:
                    try {
                        tarea = CrudTarea.readRecord(resolvedor, bitacora.getID_Tarea());
                        TareaVolley.addTarea(tarea, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_MODIFICAR:
                    try {
                        tarea = CrudTarea.readRecord(resolvedor, bitacora.getID_Tarea());
                        TareaVolley.updateTarea(tarea, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_BORRAR:
                   TareaVolley.deleteTarea( bitacora.getID_Tarea(), true, bitacora.getID());
                    break;
            }
            //  Log.i(LOGTAG, "acabo de enviar");
        }

    }

    public static void enviarActualizacionesUsuarioAlServidor() {
        ArrayList<BitacoraUsuario> registrosBitacora = CrudBitacoraUsuario.readAll(resolvedor);
        for (BitacoraUsuario bitacora : registrosBitacora) {
            Usuario usario = null;
            switch (bitacora.getOperacion()) {
                case Constantes.OPERACION_INSERTAR:
                    try {
                        usario = CrudUsuarios.readRecord(resolvedor, bitacora.getID_usuario());
                        UsuarioVolley.addUsuario(usario, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_MODIFICAR:
                    try {
                        usario = CrudUsuarios.readRecord(resolvedor, bitacora.getID_usuario());
                        UsuarioVolley.updateUsuario(usario, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_BORRAR:
                    UsuarioVolley.deleteUsuario(bitacora.getID_usuario(), true, bitacora.getID());
                    break;
            }
            //  Log.i(LOGTAG, "acabo de enviar");
        }

    }

    public static void enviarActualizacionesOrdenAlServidor() {
        ArrayList<BitacoraOrden> registrosBitacora = CrudBitacoraOrden.readAll(resolvedor);
        for (BitacoraOrden bitacora : registrosBitacora) {
            OrdenDeTrabajo orden = null;
            switch (bitacora.getOperacion()) {
                case Constantes.OPERACION_INSERTAR:
                    try {

                        orden = CrudOrdenes.readRecord(resolvedor, bitacora.getID_Orden());
                        OrdenVolley.addOrden(orden, true, bitacora.getID());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_MODIFICAR:
                    try {
                        orden = CrudOrdenes.readRecord(resolvedor, bitacora.getID_Orden());
                        OrdenVolley.updateOrden(orden, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.OPERACION_BORRAR:
                    OrdenVolley.deleteOrden(bitacora.getID_Orden(), true, bitacora.getID());
                    break;
            }
            //   Log.i(LOGTAG, "acabo de enviar");
        }


    }

    public static void recibirActualizacionesOperariosDelServidor() {
        ArrayList<BitacoraOperarios> bitacoras;
        bitacoras = CrudBitacoraOperarios.readAll(resolvedor);
        // Log.i("nachito", "size bitacora" + String.valueOf(bitacora.size()));
        if (bitacoras.size() == 0) {
            OperariosVolley.getAllOperarios();

        }

    }

    public static void recibirActualizacionesTareaDelServidor() {
        ArrayList<BitacoraTarea> bitacoras;
        bitacoras = CrudBitacoraTarea.readAll(resolvedor);
        // Log.i("nachito", "size bitacora" + String.valueOf(bitacora.size()));
        if (bitacoras.size() == 0) {
            TareaVolley.getAllTarea();

        }

    }

    public static void recibirActualizacionesOrdenDelServidor() {
        ArrayList<BitacoraOrden> bitacoras;
        bitacoras = CrudBitacoraOrden.readAll(resolvedor);
        // Log.i("nachito", "size bitacora" + String.valueOf(bitacora.size()));
        if (bitacoras.size() == 0) {
            OrdenVolley.getAllOrden();

        }

    }


    public static void recibirActualizacionesUsuarioDelServidor() {
        ArrayList<BitacoraUsuario> bitacoras;
        bitacoras = CrudBitacoraUsuario.readAll(resolvedor);
        // Log.i("nachito", "size bitacora" + String.valueOf(bitacora.size()));
        if (bitacoras.size() == 0)
            UsuarioVolley.getAllUsuario();
    }


    public static void actulizaTablaOrdenSqliteConRespuestaServidor(JSONArray jsonArray) {
        //  Log.i("sincronizacion", "recibirActualizacionesOrdenDelServidor");


        try {
            ArrayList<Long> identificadoresDeRegistrosViejos = new ArrayList<>();
            ArrayList<Long> identificadoresDeRegistrosActualizados = new ArrayList<>();
            ArrayList<OrdenDeTrabajo> registrosViejos = CrudOrdenes.readAll(resolvedor);
            ArrayList<OrdenDeTrabajo> registrosNuevos = new ArrayList<>();


            for (OrdenDeTrabajo orden : registrosViejos) {
                identificadoresDeRegistrosViejos.add(orden.getId());
                // Log.i("sincronizacion", " identificadoresDeRegistrosOrdenViejos:-->"
                //        + orden.getId());
            }

            JSONObject objJson = null;

            for (int i = 0; i < jsonArray.length(); i++) {

                objJson = jsonArray.getJSONObject(i);

                registrosNuevos.add(new OrdenDeTrabajo(
                        objJson.getLong("id"),
                        objJson.getInt("idEmpleado"),
                        objJson.getString("fecha"),
                        objJson.getString("prioridad"),
                        objJson.getString("sintoma"),
                        objJson.getString("ubicacion"),
                        objJson.getString("descripcion"),
                        objJson.getString("estado"),
                        objJson.getInt("contiene_imagen"))
                );

            }

            for (OrdenDeTrabajo orden : registrosNuevos) {
                try {

                    if (identificadoresDeRegistrosViejos.contains(orden.getId())) {
                        CrudOrdenes.update(resolvedor, orden, contexto);

                    } else {
                        CrudOrdenes.insert(resolvedor, orden, contexto);

                    }
                    identificadoresDeRegistrosActualizados.add(orden.getId());
                } catch (Exception e) {
                    orden.setId(-1);
//                    Log.i(LOGTAG,
//                            "Probablemente el registro ya existía en la BD." + "" +
//                                    " Esto se podría controlar mejor con una Bitácora.");
                }

                //  Log.i("sincronizacion", " identificadoresDeRegistrosNuevosOrden:-->" + orden.getId());
            }

            for (OrdenDeTrabajo orden : registrosViejos) {
                if (!identificadoresDeRegistrosActualizados.contains(orden.getId())) {
                    try {
                        CrudOrdenes.delete(resolvedor, orden.getId());
                    } catch (Exception e) {
                        Log.i(LOGTAG, "Error al borrar el registro con id:" + orden.getId());
                    }
                }
            }

            // OrdenVolley.getAllOrden(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
        // actulizaTablaOrdenSqliteConRespuestaServidor(jsonArray);
    }

    public static void actulizaTablaUsuarioSqliteConRespuestaServidor(JSONArray jsonArray) {
        Log.i("sincronizacion", "recibirActualizacionesDelServidorUsuario");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<Usuario> registrosNuevos = new ArrayList<>();
            ArrayList<Usuario> registrosViejos = CrudUsuarios.readAll(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
//
//            Log.i("sincronizacion", "id--usuario: *" + String.valueOf(registrosViejos.get(0).getId()));
//            Log.i("sincronizacion", "Cod--usuario: *" + String.valueOf(registrosViejos.get(0).getCodigo()));
//            Log.i("sincronizacion", "Nombre--usuario: *" + registrosViejos.get(0).getNombre());
//            Log.i("sincronizacion", "admin--usuario: *" + registrosViejos.get(0).getAdmin());

            //identificadoresDeRegistrosViejos-----------------------------------------------------

            for (Usuario i : registrosViejos) {
                identificadoresDeRegistrosViejos.add(i.getId());
                //   Log.i("sincronizacion", " identificadoresDeRegistrosViejosUsuarioNombre Usuario:-->" + i.getNombre());

            }

            //registrosNuevos -----------------------------------------------------------------------

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Usuario(
                        obj.getInt("id"),
                        obj.getInt("codigo_usuario"),
                        obj.getString("nombre"),
                        obj.getString("admin"))
                );


            }

            //CrudUsuarios--------------------------------------------------------------------------

            for (Usuario usuario : registrosNuevos) {
                try {
                    if (identificadoresDeRegistrosViejos.contains(usuario.getId())) {
                        CrudUsuarios.update(resolvedor, usuario);
                        //     Log.i("sincronizacion", "nombre usu1: " + usuario.getNombre());
                    } else {
                        CrudUsuarios.insert(resolvedor, usuario);
                        //     Log.i("sincronizacion", "nombre usu2: " + usuario.getNombre());
                    }
                    identificadoresDeRegistrosActualizados.add(usuario.getId());
                } catch (Exception e) {
                    Log.i(LOGTAG,
                            "Probablemente el registro ya existía en la BD." + "" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
                //   Log.i("sincronizacion", " identificadoresDeRegistrosNuevosUsuario:-->" + usuario.getId());
            }

            //registrosViejos-----------------------------------------------------------------------
            for (Usuario usuario : registrosViejos) {

                if (!identificadoresDeRegistrosActualizados.contains(usuario.getId())) {
                    try {
                        CrudUsuarios.delete(resolvedor, usuario.getId());
                    } catch (Exception e) {
                        Log.i(LOGTAG, "Error al borrar el registro con id:" + usuario.getId());
                    }
                }
            }

            //  UsuarioVolley.getAllUsuario(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actulizaTablaTareaSqliteConRespuestaServidor(JSONArray jsonArray) {
        Log.i("sincronizacion", "recibirActualizacionesDelServidorTarea");

        try {
            ArrayList<Long> identificadoresDeRegistrosActualizados = new ArrayList<Long>();
            ArrayList<Tarea> registrosNuevos = new ArrayList<>();
            ArrayList<Tarea> registrosViejos = CrudTarea.readAll(resolvedor);
            ArrayList<Long> identificadoresDeRegistrosViejos = new ArrayList<Long>();
//
//            Log.i("sincronizacion", "id--usuario: *" + String.valueOf(registrosViejos.get(0).getId()));
//            Log.i("sincronizacion", "Cod--usuario: *" + String.valueOf(registrosViejos.get(0).getCodigo()));
//            Log.i("sincronizacion", "Nombre--usuario: *" + registrosViejos.get(0).getNombre());
//            Log.i("sincronizacion", "admin--usuario: *" + registrosViejos.get(0).getAdmin());

            //identificadoresDeRegistrosViejos-----------------------------------------------------

            for (Tarea i : registrosViejos) {
                identificadoresDeRegistrosViejos.add(i.getId());
                //   Log.i("sincronizacion", " identificadoresDeRegistrosViejosUsuarioNombre Usuario:-->" + i.getNombre());

            }

            //registrosNuevos -----------------------------------------------------------------------

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Tarea(
                        obj.getLong("id"),
                        obj.getLong("id_orden"),
                        obj.getString("fecha_inicio"),
                        obj.getString("fecha_fin"),
                        obj.getString("descripcion")
                        )
                );


            }

            //CrudTarea--------------------------------------------------------------------------

            for (Tarea tarea : registrosNuevos) {
                try {
                    if (identificadoresDeRegistrosViejos.contains(tarea.getId())) {
                        CrudTarea.update(resolvedor, tarea);
                        //     Log.i("sincronizacion", "nombre usu1: " + usuario.getNombre());
                    } else {
                        CrudTarea.insert(resolvedor, tarea);
                        //     Log.i("sincronizacion", "nombre usu2: " + usuario.getNombre());
                    }
                    identificadoresDeRegistrosActualizados.add(tarea.getId());
                } catch (Exception e) {
                    Log.i(LOGTAG,
                            "Probablemente el registro ya existía en la BD." + "" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
                //   Log.i("sincronizacion", " identificadoresDeRegistrosNuevosUsuario:-->" + usuario.getId());
            }

            //registrosViejos-----------------------------------------------------------------------
            for (Tarea tarea : registrosViejos) {

                if (!identificadoresDeRegistrosActualizados.contains(tarea.getId())) {
                    try {
                        CrudTarea.delete(resolvedor, tarea.getId());
                        Log.i("nachito", "Se ha borrado: " + tarea.getId());
                    } catch (Exception e) {
                        Log.i(LOGTAG, "Error al borrar el registro con id:" + tarea.getId());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actulizaTablaOperariosSqliteConRespuestaServidor(JSONArray jsonArray) {
        Log.i("sincronizacion", "recibirActualizacionesDelServidorOperarios");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<Operarios> registrosNuevos = new ArrayList<>();
            ArrayList<Operarios> registrosViejos = CrudOperarios.readAll(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
//
//            Log.i("sincronizacion", "id--usuario: *" + String.valueOf(registrosViejos.get(0).getId()));
//            Log.i("sincronizacion", "Cod--usuario: *" + String.valueOf(registrosViejos.get(0).getCodigo()));
//            Log.i("sincronizacion", "Nombre--usuario: *" + registrosViejos.get(0).getNombre());
//            Log.i("sincronizacion", "admin--usuario: *" + registrosViejos.get(0).getAdmin());

            //identificadoresDeRegistrosViejos-----------------------------------------------------

            for (Operarios i : registrosViejos) {
                identificadoresDeRegistrosViejos.add(i.get_id());
                //   Log.i("sincronizacion", " identificadoresDeRegistrosViejosUsuarioNombre Usuario:-->" + i.getNombre());

            }

            //registrosNuevos -----------------------------------------------------------------------

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Operarios(
                                obj.getInt("id"),
                                obj.getLong("id_tarea"),
                                obj.getInt("id_usuario")

                        )
                );


            }

            //CrudOperarios--------------------------------------------------------------------------

            for (Operarios operarios : registrosNuevos) {
                try {
                    if (identificadoresDeRegistrosViejos.contains(operarios.get_id())) {
                        CrudOperarios.update(resolvedor, operarios);
                        //     Log.i("sincronizacion", "nombre usu1: " + usuario.getNombre());
                    } else {
                        CrudOperarios.insert(resolvedor, operarios);
                        //     Log.i("sincronizacion", "nombre usu2: " + usuario.getNombre());
                    }
                    identificadoresDeRegistrosActualizados.add(operarios.get_id());
                } catch (Exception e) {
                    Log.i(LOGTAG,
                            "Probablemente el registro ya existía en la BD." + "" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
                //   Log.i("sincronizacion", " identificadoresDeRegistrosNuevosUsuario:-->" + usuario.getId());
            }

            //registrosViejos-----------------------------------------------------------------------
            for (Operarios operarios : registrosViejos) {

                if (!identificadoresDeRegistrosActualizados.contains(operarios.get_id())) {
                    try {
                        CrudOperarios.delete(resolvedor, operarios.get_id());
                        Log.i("nachito", "Se ha borrado: " + operarios.get_id());
                    } catch (Exception e) {
                        Log.i(LOGTAG, "Error al borrar el registro con id:" + operarios.get_id());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
