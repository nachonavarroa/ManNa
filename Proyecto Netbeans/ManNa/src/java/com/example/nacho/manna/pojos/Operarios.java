/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nacho
 */
@XmlRootElement
public class Operarios {

    private int id;
    private long id_tarea;
    private int id_usuario;

    public Operarios() {
    }

    public Operarios(long id_tarea, int id_usuario) {
        this.id_tarea = id_tarea;
        this.id_usuario = id_usuario;
    }

    public Operarios(int id, long id_tarea, int id_usuario) {
        this.id = id;
        this.id_tarea = id_tarea;
        this.id_usuario = id_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
