package com.example.nacho.mannadrawe.tabs;

public class TabsDrawer {
    final String generarOt;
    final String autor;
    final String ayuda;
    final String verOrdenes;
    final String verEmpleados;


    public TabsDrawer() {
        generarOt = "Ir a generar Orden";
        autor = "Autor";
        ayuda = "Ir a ayuda";
        verOrdenes = "Órdenes de trabajo";
        verEmpleados = "Usarios";

    }

    public String getVerEmpleados() {
        return verEmpleados;
    }

    public String getGenerarOt() {
        return generarOt;
    }

    public String getAutor() {
        return autor;
    }

    public String getAyuda() {
        return ayuda;
    }

    public String getVerOrdenes() {
        return verOrdenes;
    }
}
