package com.example.nacho.manna.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nacho
 */

@XmlRootElement

public class Usuario {

    private int id;
    private int codigo_usuario;
    private String nombre;
    private String admin;

    public Usuario() {
    }

    public Usuario(int id, int codigo_usuario, String nombre, String admin) {
        this.id = id;
        this.codigo_usuario = codigo_usuario;
        this.nombre = nombre;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }



}
