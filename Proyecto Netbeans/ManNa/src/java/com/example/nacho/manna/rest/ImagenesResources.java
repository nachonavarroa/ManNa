package com.example.nacho.manna.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author Nacho
 */
@Path("imagenes")
public class ImagenesResources {

    final String UPLOAD_FILE_SERVER = "C:/xampp/htdocs/ManNa/img/";

    @Context
    private UriInfo context;

    public ImagenesResources() {
    }

    @GET
    @Path("prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPrueba() {

        return "ManNa" + "\n Imagenes" + "\n Funciona!";
    }

    // Devuelve---------------------------------------------
    @GET
    @Path("/descarga/{imagen}")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response getImagen(@PathParam("imagen") String imagen) {
        File file = new File(UPLOAD_FILE_SERVER + imagen);
        Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition",
                "attachment; filename=\"" + imagen + "\"");
         Date fecha = new Date();
         System.out.println(fecha.toString() + ": Se ha descargado imagen -getImagen-" +imagen);
        
        return responseBuilder.build();
    }

    //Añadir Imagen.---------------------------------------------------------
    @POST
    @Path("/subir")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public Response addImagen(
            @FormDataParam("fichero") InputStream fileInputStream,
            @FormDataParam("fichero") FormDataContentDisposition fileFormDataContentDisposition) {

        String fileName = null;
        String uploadFilePath = null;

        try {
            Date fecha = new Date();
            fileName = fileFormDataContentDisposition.getFileName();
            uploadFilePath = writeToFileServer(fileInputStream, fileName);
            System.out.println(fecha.toString() + ": Se ha añadido imagen -addImagen-" +fileName);
            
        } catch (IOException ioe) {
            ioe.printStackTrace();

        } finally {
        }
        return Response.ok("Fichero subido a " + uploadFilePath).build();
    }

    private String writeToFileServer(InputStream inputStream, String fiName)
            throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fiName;
        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            outputStream.close();
        }

        return qualifiedUploadFilePath;
    }

}
