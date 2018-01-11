/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.conexion;

import com.example.nacho.manna.pojos.Tarea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nacho
 */

public class TareaBD {

    static final private String NOMBRE_TABLA = "tarea ";

    static final private String ID = "_id";
    static final private String ID_ORDEN = "id_orden";
    static final private String FECHA_INICIO = "Fecha_inicio";
    static final private String FECHA_FIN = "Fecha_fin";
     static final private String DESCRIPCION = "Descripcion";

    static final private String SELECT = "SELECT * FROM ";
    static final private String INSERT = "INSERT INTO ";
    static final private String UPDATE = "UPDATE ";
    static final private String DELETE = "DELETE FROM ";
    static final private String SET = "SET ";
    static final private String WHERE = "WHERE ";

    //Extraer todas las tareas------------------------------------------------
    static public List<Tarea> getAllTareas() throws Exception {

        List<Tarea> registros = new ArrayList<>();

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA);
            rs = ps.executeQuery();

            while (rs.next()) {
                Tarea registro = new Tarea();
                
                registro.setId(rs.getLong(ID));
                registro.setId_orden(rs.getLong(ID_ORDEN));
                registro.setFecha_inicio(rs.getString(FECHA_INICIO));
                registro.setFecha_fin(rs.getString(FECHA_FIN));
                registro.setDescripcion(rs.getString(DESCRIPCION));
                
                registros.add(registro);
            }
            return registros;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    //Extraé una tarea---------------------------------------------------------
    static public Tarea getTarea(long id) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA
                    + WHERE + ID + " = " + id);
            rs = ps.executeQuery();

            while (rs.next()) {

                Tarea registro = new Tarea();
                
                registro.setId(rs.getLong(ID));
                registro.setId_orden(rs.getLong(ID_ORDEN));
                registro.setFecha_inicio(rs.getString(FECHA_INICIO));
                registro.setFecha_fin(rs.getString(FECHA_FIN));
                registro.setDescripcion(rs.getString(DESCRIPCION));


                return registro;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
        return null;
    }

//Inserta tarea---------------------------------------------------------------
    static public void addTarea(Tarea tarea) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query;
          
            query = INSERT + NOMBRE_TABLA
                    + " ("
                    + ID + ","
                    + ID_ORDEN + " ,"
                    + FECHA_INICIO + " ,"
                    + FECHA_FIN + " ,"
                    + DESCRIPCION 
                    + ") "
                    + "VALUES ("
                    + tarea.getId() 
                    + "," + tarea.getId_orden()+","
                    + "'" + tarea.getFecha_inicio() + "' ,"
                    + "'" + tarea.getFecha_fin() + "' ,"
                    + "'" + tarea.getDescripcion() + "' "
                    + ");";


            ps = conexion.prepareStatement(query);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    //Modificar tarea-----------------------------------------------------------
    static public void updateTarea(Tarea tarea) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query;
            query = UPDATE + NOMBRE_TABLA + SET
                    + ID + " = " + tarea.getId() + ", "
                    + ID_ORDEN + " = " + tarea.getId_orden() + " , "
                    + FECHA_INICIO + "  = '" + tarea.getFecha_inicio() + "' ,"
                    + FECHA_FIN + "  = '" + tarea.getFecha_fin() + "' , "
                    + DESCRIPCION + "  = '" + tarea.getDescripcion() + "'  "
                    + WHERE + ID + " = " + tarea.getId() + ";";

            ps = conexion.prepareStatement(query);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    //Borrar un usuario---------------------------------------------------------
    static public void deleteTarea(long id) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(DELETE + NOMBRE_TABLA
                    + WHERE + ID + " = " + id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

}
