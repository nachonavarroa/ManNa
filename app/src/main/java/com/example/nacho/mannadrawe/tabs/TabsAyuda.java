package com.example.nacho.mannadrawe.tabs;


public class TabsAyuda {
    final  String tabMenu;
    final  String tabDatosEmpleado;
    final  String tabGenerarOrden;
    final  String tabCodigoOrden;


    public TabsAyuda() {
        tabMenu ="Menú";
        tabDatosEmpleado = "Datos de empleado";
        tabGenerarOrden  = "Generar Orden";
        tabCodigoOrden   = "Código  Orden";
    }
    public String getTabDatosEmpleado() {
        return tabDatosEmpleado;
    }
    public String getTabGenerarOrden() {
        return tabGenerarOrden;
    }
    public String getTabCodigoOrden() {
        return tabCodigoOrden;
    }

    public String getTabMenu() {
        return tabMenu;
    }
}
