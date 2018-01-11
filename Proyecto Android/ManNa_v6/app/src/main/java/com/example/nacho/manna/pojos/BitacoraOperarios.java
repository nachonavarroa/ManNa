package com.example.nacho.manna.pojos;

import com.example.nacho.manna.auxiliar.Constantes;

/**
 * Created by Nacho on 27/11/2017.
 */

public class BitacoraOperarios {

    private int ID;
    private int ID_Opererarios;
    private int operacion;

    public BitacoraOperarios() {
        this.setID(Constantes.SIN_VALOR_INT);
        this.setID_Opererarios(Constantes.SIN_VALOR_INT);
        this.setOperacion(Constantes.SIN_VALOR_INT);
    }

    public BitacoraOperarios(int ID, int ID_operarios, int operacion) {
        this.setID(ID);
        this.setID_Opererarios(ID_operarios);
        this.setOperacion(operacion);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Opererarios() {
        return ID_Opererarios;
    }

    public void setID_Opererarios(int ID_Opererarios) {
        this.ID_Opererarios = ID_Opererarios;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
