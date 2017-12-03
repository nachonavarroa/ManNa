package com.example.nacho.manna.pojos;

import com.example.nacho.manna.auxiliar.Constantes;

/**
 * Created by Nacho on 27/11/2017.
 */

public class BitacoraOrden {
    private int ID;
    private long ID_Orden;
    private int operacion;

    public BitacoraOrden() {
        this.setID(Constantes.SIN_VALOR_INT);
        this.setID_Orden(Constantes.SIN_VALOR_INT);
        this.setOperacion(Constantes.SIN_VALOR_INT);
    }

    public BitacoraOrden(int ID, int ID_Ciclo, int operacion) {
        this.setID(ID);
        this.setID_Orden(ID_Ciclo);
        this.setOperacion(operacion);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getID_Orden() {
        return ID_Orden;
    }

    public void setID_Orden(long ID_Orden) {
        this.ID_Orden = ID_Orden;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
