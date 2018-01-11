package com.example.nacho.manna.pojos;

import com.example.nacho.manna.auxiliar.Constantes;

/**
 * Created by Nacho on 27/11/2017.
 */

public class BitacoraTarea {
    private int ID;
    private long ID_Tarea;
    private int operacion;

    public BitacoraTarea() {
        this.setID(Constantes.SIN_VALOR_INT);
        this.setID_Tarea(Constantes.SIN_VALOR_INT);
        this.setOperacion(Constantes.SIN_VALOR_INT);
    }

    public BitacoraTarea(int ID, int ID_tarea, int operacion) {
        this.setID(ID);
        this.setID_Tarea(ID_tarea);
        this.setOperacion(operacion);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getID_Tarea() {
        return ID_Tarea;
    }

    public void setID_Tarea(long ID_Tarea) {
        this.ID_Tarea = ID_Tarea;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
