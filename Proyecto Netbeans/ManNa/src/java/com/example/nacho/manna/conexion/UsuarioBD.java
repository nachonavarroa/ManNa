/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nacho.manna.conexion;

import com.example.nacho.manna.pojos.Usuario;
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
public class UsuarioBD {

    static final private String NOMBRE_TABLA = "usuario ";

    static final private String ID = "_id";
    static final private String CODIGO_USUARIO = "Codigo_Usuario";
    static final private String NOMBRE_USUARIO = "Nombre_Usuario";
    static final private String ADMIN_USUARIO = "Administrador";

    static final private String SELECT = "SELECT * FROM ";
    static final private String INSERT = "INSERT INTO ";
    static final private String UPDATE = "UPDATE ";
    static final private String DELETE = "DELETE FROM ";
    static final private String SET = "SET ";
    static final private String WHERE = "WHERE ";

    //Extraer todas los usuarios------------------------------------------------
    static public List<Usuario> getAllUsuarios() throws Exception {

        List<Usuario> registros = new ArrayList<>();

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario registro = new Usuario();

                registro.setId(rs.getInt(ID));
                registro.setCodigo_usuario(rs.getInt(CODIGO_USUARIO));
                registro.setNombre(rs.getString(NOMBRE_USUARIO));
                registro.setAdmin(rs.getString(ADMIN_USUARIO));

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

    //Extraé un usuario---------------------------------------------------------
    static public Usuario getUsuario(int id) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(SELECT + NOMBRE_TABLA
                    + WHERE + ID + " = " + id);
            rs = ps.executeQuery();

            while (rs.next()) {

                Usuario registro = new Usuario();

                registro.setId(rs.getInt(ID));
                registro.setCodigo_usuario(rs.getInt(CODIGO_USUARIO));
                registro.setNombre(rs.getString(NOMBRE_USUARIO));
                registro.setAdmin(rs.getString(ADMIN_USUARIO));

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

//Inserta usuario---------------------------------------------------------------
    static public void addUsuario(Usuario usuario) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query;
            int codUsu = usuario.getCodigo_usuario();
            query = INSERT + NOMBRE_TABLA
                    + " ("
                    + ID + ","
                    + CODIGO_USUARIO + " ,"
                    + NOMBRE_USUARIO + " ,"
                    + ADMIN_USUARIO
                    + ") "
                    + "VALUES ("
                    + usuario.getId() 
                    + "," + codUsu+","
                    + "'"+usuario.getNombre() + "' ,"
                    + "'" + usuario.getAdmin() + "' "
                    + ");";

//            query = "INSERT INTO " + NOMBRE_TABLA + " (_id,Codigo_Usuario, Nombre_Usuario, "
//                    + "Administrador) "
//                    + "VALUES ("
//                    + usuario.getId() + ", "
//                    + usuario.getCodigoUsuario()
//                    + ", '" + usuario.getNombre() + "' ,"
//                    + "'" + usuario.getAdmin() + "' "
//                    + " )";

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

    //Modificar usuario-----------------------------------------------------------
    static public void updateUsaurio(Usuario usuario) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query;
            query = UPDATE + NOMBRE_TABLA + SET
                    + CODIGO_USUARIO + " = " + usuario.getCodigo_usuario() + ", "
                    + NOMBRE_USUARIO + " = '" + usuario.getNombre() + "' , "
                    + ADMIN_USUARIO + "  = '" + usuario.getAdmin() + "'  "
                    + WHERE + ID + " = " + usuario.getId() + ";";

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
    static public void deleteUsuario(long id) throws SQLException {
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
