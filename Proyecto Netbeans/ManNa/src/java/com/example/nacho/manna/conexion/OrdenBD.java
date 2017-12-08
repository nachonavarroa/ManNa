package com.example.nacho.manna.conexion;

import com.example.nacho.manna.pojos.OrdenDeTrabajo;
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
public class OrdenBD {

    //Extraer todas las órdenes-------------------------------------------------
    static public List<OrdenDeTrabajo> getAllOrden() throws Exception {

        List<OrdenDeTrabajo> registros = new ArrayList<>();

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT * FROM orden ");
            rs = ps.executeQuery();

            while (rs.next()) {
                OrdenDeTrabajo registro = new OrdenDeTrabajo();

                registro.setId(rs.getLong("_id"));
                registro.setIdEmpleado(rs.getInt("id_empleado"));
                registro.setFecha(rs.getString("Fecha"));
                registro.setPrioridad(rs.getString("Prioridad"));
                registro.setSintoma(rs.getString("Sintoma"));
                registro.setUbicacion(rs.getString("Ubicacion"));
                registro.setDescripcion(rs.getString("Descripcion"));
                registro.setEstado(rs.getString("Estado"));

                registros.add(registro);
            }
            return registros;

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
    //Extraé una ordenen--------------------------------------------------------

    static public OrdenDeTrabajo getOrden(int id) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM orden WHERE _id = "
                    + id);
            rs = ps.executeQuery();

            while (rs.next()) {

                OrdenDeTrabajo registro = new OrdenDeTrabajo();

                registro.setId(rs.getLong("_id"));
                registro.setIdEmpleado(rs.getInt("id_empleado"));
                registro.setFecha(rs.getString("Fecha"));
                registro.setPrioridad(rs.getString("Prioridad"));
                registro.setSintoma(rs.getString("Sintoma"));
                registro.setUbicacion(rs.getString("Ubicacion"));
                registro.setDescripcion(rs.getString("Descripcion"));
                registro.setEstado(rs.getString("Estado"));

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

//Inserta orden-----------------------------------------------------------------
    static public void addOrden(OrdenDeTrabajo orden) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query;

            query = "INSERT INTO orden (_id,id_empleado, Fecha, "
                    + "Prioridad, Sintoma, Ubicacion, Descripcion, Estado) "
                    + "VALUES ("
                    + orden.getId() + ", "
                    + orden.getIdEmpleado()
                    + ", '" + orden.getFecha() + "' ,"
                    + "'" + orden.getPrioridad() + "', "
                    + "'" + orden.getSintoma() + "', "
                    + "'" + orden.getUbicacion() + "', "
                    + "'" + orden.getDescripcion() + "', "
                    + "'" + orden.getEstado() + "' )";

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

    //Actulizar orden-----------------------------------------------------------
    static public void updateOrden(OrdenDeTrabajo orden) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query;
            query = "UPDATE orden SET "
                    + "id_empleado = " + orden.getIdEmpleado() + ", "
                    + "Fecha = '" + orden.getFecha() + "' , "
                    + "Prioridad = '" + orden.getPrioridad() + "' , "
                    + "Sintoma = '" + orden.getSintoma() + "', "
                    + "Ubicacion = '" + orden.getUbicacion() + "', "
                    + "Descripcion = '" + orden.getDescripcion() + "', "
                    + "Estado= '" + orden.getEstado() + "' "
                    + "WHERE  _id = " + orden.getId() + ";";

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

    //Borrar una orden----------------------------------------------------------
    static public void deleteOrden(long id) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("DELETE FROM orden  WHERE _id = "
                    + id);

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
