/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.rest;

import com.example.nacho.manna.conexion.TareaBD;
import com.example.nacho.manna.pojos.Tarea;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Nacho
 */
@Path("tarea")
public class TareaResources {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UauarioResource
     */
    public TareaResources() {
    }

    /**
     * Retrieves representation of an instance of
     * com.example.nacho.manna.rest.UauarioResource
     *
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPrueba() {

        return "ManNa" + "\n Tarea" + "\n Funciona!";
    }

    // Devuelve todos los Registros---------------------------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTarea() {
        try {
            List<Tarea> registros = TareaBD.getAllTareas();
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha consultado -getAllTareas");
            String json = new Gson().toJson(registros);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "consultar los tarea ").build();
        }
    }

    // Añadir Registro.--------------------------------------------------------- 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTarea(Tarea tarea) {
        try {
           
            TareaBD.addTarea(tarea);
            
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido -addTarea- "
                    + tarea.getId());
            String json = "{ \"Id\": \"" + String.valueOf(tarea.getId())
                    + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Insertar Registro: "
                    + tarea.getId()).build();
        }
    }

    // Actualizar un registro. -------------------------------------------------
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateTarea(Tarea tarea, @PathParam("id") long id) {
        System.out.println("Llegó a la actualización");
        try {

            TareaBD.updateTarea(tarea);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha actualizado - updateTarea- "
                     + tarea.getId());
            String json = "{ \"Id\": \"" + String.valueOf(tarea.getId()) + "\" }";

            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Actualizar tarea: "
                    + tarea.getId()).build();
        }
    }

    // Borrar un registro. -----------------------------------------------------
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTarea(@PathParam("id") long id) {

        try {
            Tarea tarea= TareaBD.getTarea((long) id);
            TareaBD.deleteTarea(id);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha borrado - deleteTarea- "
                    + tarea.getId());
            String json = "{ \"id\": \"" + id + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo"
                    + " Borrar Registro: " + id).build();
        }
    }

}
