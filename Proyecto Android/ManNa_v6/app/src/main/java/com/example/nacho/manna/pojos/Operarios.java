package com.example.nacho.manna.pojos;


public class Operarios {
    private  int _id;
    private  long id_tarea;
    private  int id_usuario;

    public Operarios() {
    }

    public Operarios(int _id, long id_tarea, int id_usuario) {
        this._id = _id;
        this.id_tarea = id_tarea;
        this.id_usuario = id_usuario;
    }

    public Operarios(long id_tarea, int id_usuario) {
        this.id_tarea = id_tarea;
        this.id_usuario = id_usuario;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(long id_tarea) {
        this.id_tarea = id_tarea;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
