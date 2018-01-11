
package com.example.nacho.manna.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nacho
 */
@XmlRootElement
public class Tarea {

    private long id;
    private long id_orden;
    private String fecha_inicio;
    private String fecha_fin;
    private String descripcion;

    public Tarea() {
    }

    public Tarea(long id, long id_orden, String fecha_inicio, String fecha_fin,
            String descripcion) {
        this.id = id;
        this.id_orden = id_orden;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_orden() {
        return id_orden;
    }

    public void setId_orden(long id_orden) {
        this.id_orden = id_orden;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    

}
