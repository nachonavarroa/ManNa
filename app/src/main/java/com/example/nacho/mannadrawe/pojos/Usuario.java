package com.example.nacho.mannadrawe.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable{
    int id;
    int codigo;
    String nombre;
    String admin;

    public Usuario() {
    }

    public Usuario(int id, int codigo,String nombre, String admin) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.admin = admin;

    }

    public Usuario(String nombre, int codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Usuario(int codigo, String nombre, String admin) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.admin = admin;
    }

    protected Usuario(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        codigo = in.readInt();
        admin = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeInt(codigo);
        dest.writeString(admin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
