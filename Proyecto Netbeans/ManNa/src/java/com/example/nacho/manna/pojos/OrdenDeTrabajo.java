
package com.example.nacho.manna.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nacho
 */
@XmlRootElement

public class OrdenDeTrabajo {
    
    private long id;
    private int idEmpleado;
    private String fecha;
    private String prioridad;
    private String sintoma;
    private String ubicacion;
    private String descripcion;
    private String estado;

    public OrdenDeTrabajo() {
    }

    public OrdenDeTrabajo(long id, int idEmpleado, String fecha,
             String prioridad, String sintoma,String ubicacion, 
             String descripcion, String estado) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.sintoma = sintoma;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
   
    

}
