package com.example.nacho.manna.pojos;

public class Tarea {
    private long id;
    private long Id_Orden;
    private String fecha_inicio;
    private String fecha_fin;
    private String descripcion;

    public Tarea() {
    }

    public Tarea(long id, long id_Orden, String fecha_inicio, String fecha_fin, String descripcion) {
        this.id = id;
        Id_Orden = id_Orden;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.descripcion = descripcion;
    }

    public Tarea(long id_Orden, String fecha_inicio, String fecha_fin, String descripcion) {
        Id_Orden = id_Orden;
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

    public long getId_Orden() {
        return Id_Orden;
    }

    public void setId_Orden(long id_Orden) {
        Id_Orden = id_Orden;
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
