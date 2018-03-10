/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.conexion;

import com.example.nacho.manna.pojos.Operarios;
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
public class OperariosBD {

    static final private String NOMBRE_TABLA = "operarios ";

    static final private String ID = "id";
    static final private String ID_TAREA = "id_tarea";
    static final private String ID_USUARIO = "id_usuario";

    static final private String SELECT = "SELECT * FROM ";
    static final private String INSERT = "INSERT INTO ";
    static final private String UPDATE = "UPDATE ";
    static final private String DELETE = "DELETE FROM ";
    static final private String SET = "SET ";
    static final private String WHERE = " WHERE ";

    //Extraer todos los operarios------------------------------------------------
    static public List<Operarios> getAllOperarios() throws Exception {

        List<Operarios> registros = new ArrayList<>();

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA);
            rs = ps.executeQuery();

            while (rs.next()) {
                Operarios registro = new Operarios();

                registro.setId(rs.getInt(ID));
                registro.setId_tarea(rs.getLong(ID_TAREA));
                registro.setId_usuario(rs.getInt(ID_USUARIO));

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

    //Extraé un operarios---------------------------------------------------------
    static public Operarios getOperarios(int id) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA
                    + WHERE + ID + " = " + id);
            rs = ps.executeQuery();

            while (rs.next()) {

                Operarios registro = new Operarios();

                registro.setId(rs.getInt(ID));
                registro.setId_tarea(rs.getLong(ID_TAREA));
                registro.setId_usuario(rs.getInt(ID_USUARIO));;

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

//Inserta operarios---------------------------------------------------------------
    static public void addOperarios(Operarios operarios) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query;
            //INSERT INTO `operarios`(`id`, `id_tarea`, `id_usuario`) VALUES (1,1,1)

            query = INSERT + NOMBRE_TABLA
                    + " ("
                    + ID + ","
                    + ID_TAREA + " ,"
                    + ID_USUARIO  
                    + ") "
                    + "VALUES ("
                    + operarios.getId() + ","
                    + operarios.getId_tarea() + ","
                    + operarios.getId_usuario()
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

    //Modificar operarios-----------------------------------------------------------
    static public void updateOperarios(Operarios operarios) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query;
            //UPDATE `operarios` SET `id_tarea`=55,`id_usuario`=34 WHERE id =25
            query = UPDATE + NOMBRE_TABLA + SET
                   // + ID + " = " + operarios.getId() + ", "
                    + ID_TAREA + " = " + operarios.getId_tarea() + " , "
                    + ID_USUARIO + "  = " + operarios.getId_usuario()
                    + WHERE + ID + " = " + operarios.getId() + ";";

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

    //Borrar un operarios-------------------------------------------------------
    static public void deleteOperaios(int id) throws SQLException {
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
