package com.example.nacho.manna.tabs;

public class TabsDrawer {
    final String generarOt;
    final String autor;
    final String ayuda;
    final String verOrdenes;
    final String verEmpleados;
    final String verOrdenesPendientes;
    final String verOrdenesProceso;
    final String verOrdenesRealizado;


    public TabsDrawer() {
        generarOt = "Ir a generar Orden";
        autor = "Autor";
        ayuda = "Ir a ayuda";
        verOrdenes = "Órdenes de trabajo";
        verEmpleados = "Usuarios";
        verOrdenesPendientes ="Órdenes pendientes";
        verOrdenesProceso    ="Órdenes en proceso";
        verOrdenesRealizado  ="Órdenes realizadas";

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

    public String getVerOrdenesPendientes() {
        return verOrdenesPendientes;
    }

    public String getVerOrdenesProceso() {
        return verOrdenesProceso;
    }

    public String getVerOrdenesRealizado() {
        return verOrdenesRealizado;
    }
}
