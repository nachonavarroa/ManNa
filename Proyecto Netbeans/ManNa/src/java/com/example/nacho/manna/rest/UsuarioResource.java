/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.rest;

import com.example.nacho.manna.conexion.UsuarioBD;
import com.example.nacho.manna.pojos.Usuario;
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
@Path("usuario")
public class UsuarioResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UauarioResource
     */
    public UsuarioResource() {
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

        return "ManNa" + "\n Usuario" + "\n Funciona!";
    }

    // Devuelve todos los Registros---------------------------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuario() {
        try {
            List<Usuario> registros = UsuarioBD.getAllUsuarios();
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha consultado -getAllUsuario");
            String json = new Gson().toJson(registros);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "consultar los usuarios ").build();
        }
    }

    // Añadir Registro.--------------------------------------------------------- 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUsuario(Usuario usuario) {
        try {

            UsuarioBD.addUsuario(usuario);
            
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido -addUsuario- "
                    + usuario.getNombre()+" id: "+usuario.getId());
            String json = "{ \"Id\": \"" + String.valueOf(usuario.getId())
                    + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Insertar Registro: "
                    + usuario.getId()).build();
        }
    }

    // Actualizar un registro. -------------------------------------------------
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateUsuario(Usuario usuario, @PathParam("id") int id) {
        System.out.println("Llegó a la actualización");
        try {

            UsuarioBD.updateUsaurio(usuario);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha actualizado - updateUsuario- "
                     + usuario.getNombre()+" id: "+usuario.getId());
            String json = "{ \"Id\": \"" + String.valueOf(usuario.getId()) + "\" }";

            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo "
                    + "Actualizar Orden: "
                    + usuario.getId()).build();
        }
    }

    // Borrar un registro. -----------------------------------------------------
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") long id) {

        try {
            Usuario usuario= UsuarioBD.getUsuario((int) id);
            UsuarioBD.deleteUsuario(id);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha borrado - deleteUsuario- "
                    + usuario.getNombre()+" id: "+usuario.getId());
            String json = "{ \"id\": \"" + id + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo"
                    + " Borrar Registro: " + id).build();
        }
    }

}
