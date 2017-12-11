package com.example.nacho.manna.pojos;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.nacho.manna.auxiliar.Constantes;

public class OrdenDeTrabajo implements Parcelable {
    private long id;
    private int idEmpleado;
    private String fecha;
    private String prioridad;
    private String sintoma;
    private String ubicacion;
    private String descripcion;
    private String estado;
    private Bitmap imagen;


    public OrdenDeTrabajo(long id, int idEmpleado, String fecha,
                          String prioridad, String sintoma, String ubicacion,
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

    public OrdenDeTrabajo() {
        this.id = Constantes.SIN_VALOR_INT;
        this.idEmpleado = Constantes.SIN_VALOR_INT;
        this.fecha = Constantes.SIN_VALOR_STRING;
        this.prioridad =  Constantes.SIN_VALOR_STRING;
        this.sintoma =  Constantes.SIN_VALOR_STRING;
        this.ubicacion = Constantes.SIN_VALOR_STRING;
        this.descripcion = Constantes.SIN_VALOR_STRING;
        this.estado =  Constantes.SIN_VALOR_STRING;
        this.imagen = null;
    }



    protected OrdenDeTrabajo(Parcel in) {
        id = in.readLong();
        idEmpleado = in.readInt();
        fecha = in.readString();
        prioridad = in.readString();
        sintoma = in.readString();
        ubicacion = in.readString();
        descripcion = in.readString();
        estado = in.readString();
    }

    public static final Creator<OrdenDeTrabajo> CREATOR = new Creator<OrdenDeTrabajo>() {
        @Override
        public OrdenDeTrabajo createFromParcel(Parcel in) {
            return new OrdenDeTrabajo(in);
        }

        @Override
        public OrdenDeTrabajo[] newArray(int size) {
            return new OrdenDeTrabajo[size];
        }
    };

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(idEmpleado);
        dest.writeString(fecha);
        dest.writeString(prioridad);
        dest.writeString(sintoma);
        dest.writeString(ubicacion);
        dest.writeString(descripcion);
        dest.writeString(estado);


    }
}
