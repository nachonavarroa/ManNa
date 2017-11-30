package com.example.nacho.mannadrawe.pojos;

public class BitacoraUsuario {

    private int ID;
    private int ID_usuario;
    private int operacion;

    public BitacoraUsuario() {
    }


    public BitacoraUsuario(int ID, int ID_usuario, int operacion) {
        this.ID = ID;
        this.ID_usuario = ID_usuario;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
