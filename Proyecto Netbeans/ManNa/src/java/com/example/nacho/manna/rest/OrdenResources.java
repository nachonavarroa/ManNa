package com.example.nacho.manna.rest;

import com.example.nacho.manna.conexion.OrdenBD;
import com.example.nacho.manna.pojos.OrdenDeTrabajo;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Nacho
 */

@Path("orden")
public class OrdenResources {

    @Context
    private UriInfo context;

    public OrdenResources() {
    }

    @GET
    @Path("prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPrueba() {

        return "ManNa" +"\n Orden"+ "\n Funciona!";
    }

    // Devuelve todos los Registros---------------------------------------------
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrden() {
        try {
            List<OrdenDeTrabajo> registros = OrdenBD.getAllOrden();
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha consultado -getAllOrden-");
            String json = new Gson().toJson(registros);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                  + "consultar las órdenes ").build();
        }
    }

    // Añadir Registro.--------------------------------------------------------- 
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrden(OrdenDeTrabajo orden) {
        try {

            OrdenBD.addOrden(orden);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido - addOrden- "
                    + orden.getId());
            String json = "{ \"Id\": \"" + String.valueOf(orden.getId())
                    + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Insertar Registro: " 
                  + orden.getId()).build();
        }
    }


    // Actualizar un registro. -------------------------------------------------
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response updateOrden(OrdenDeTrabajo orden, @PathParam("id") long id) {
        System.out.println("Llegó a la actualización");
        try {
     
           OrdenBD.updateOrden(orden);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha actualizado - updateOrden- " 
                    + orden.getId());
            String json = "{ \"Id\": \"" + String.valueOf(orden.getId()) + "\" }";
            
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
            
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Actualizar Orden: "
                  + orden.getId()).build();
        }
    }
    
    
    
    // Borrar un registro. -----------------------------------------------------
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrden(@PathParam("id") long id) {

        try {
            OrdenBD.deleteOrden(id);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha borrado - deleteOrden- "
                    + id);
            String json = "{ \"id\": \"" + id + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo"
                    + " Borrar Registro: " + id ).build();
        }
    }
}
