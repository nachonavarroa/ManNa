/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.rest;

import com.example.nacho.manna.conexion.OperariosBD;
import com.example.nacho.manna.pojos.Operarios;
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
@Path("operarios")
public class OperariosResources {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UauarioResource
     */
    public OperariosResources() {
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

        return "ManNa" + "\n Operarios" + "\n Funciona!";
    }

    // Devuelve todos los Registros---------------------------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOperarios() {
        try {
            List<Operarios> registros = OperariosBD.getAllOperarios();
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha consultado -getAllOperarios");
            String json = new Gson().toJson(registros);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "consultar los operarios ").build();
        }
    }

    // Añadir Registro.--------------------------------------------------------- 
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response addOperarios(Operarios operarios) {
        try {
           
            OperariosBD.addOperarios(operarios);
            
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido -addOperarios- "
                    + operarios.getId());
            String json = "{ \"Id\": \"" + String.valueOf(operarios.getId())
                    + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Insertar Registro: "
                    + operarios.getId()).build();
        }
    }

    // Actualizar un registro. -------------------------------------------------
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateOperarios(Operarios operarios, @PathParam("id") int id) {
        System.out.println("Llegó a la actualización");
        try {

            OperariosBD.updateOperarios(operarios);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha actualizado - updateOperarios- "
                     +" id: "+operarios.getId());
            
            String json = "{ \"id\": \"" + String.valueOf(operarios.getId()) + "\" }";

            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Actualizar Operarios: "
                    + operarios.getId()).build();
        }
    }

    // Borrar un registro. -----------------------------------------------------
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOperarios(@PathParam("id") int id) {

        try {
            Operarios operarios = OperariosBD.getOperarios(id);
            OperariosBD.deleteOperaios(id);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha borrado - deleteOperarios- "
                    + operarios.getId());
            String json = "{ \"id\": \"" + id + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo"
                    + " Borrar Registro: " + id).build();
        }
    }

}
