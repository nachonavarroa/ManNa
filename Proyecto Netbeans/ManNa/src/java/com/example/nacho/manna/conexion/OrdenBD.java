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
    static String ID = "id";
    static String ID_EMPLEADO = "idEmpleado";
    static String FECHA = "fecha";
    static String PRIORIDAD = "prioridad";
    static String SINTOMA = "sintoma";
    static String UBICACION = "ubicacion";
    static String DESCRIPCION = "descripcion";
    static String ESTADO = "estado";
    static String CONTIENE_IMAGEN = "contiene_imagen";

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

                registro.setId(rs.getLong(ID));
                registro.setIdEmpleado(rs.getInt(ID_EMPLEADO));
                registro.setFecha(rs.getString(FECHA));
                registro.setPrioridad(rs.getString(PRIORIDAD));
                registro.setSintoma(rs.getString(SINTOMA));
                registro.setUbicacion(rs.getString(UBICACION));
                registro.setDescripcion(rs.getString(DESCRIPCION));
                registro.setEstado(rs.getString(ESTADO));
                registro.setContiene_imagen(rs.getInt(CONTIENE_IMAGEN));

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
            ps = conexion.prepareStatement("SELECT * FROM orden WHERE  id = "
                    + id);
            rs = ps.executeQuery();

            while (rs.next()) {

                OrdenDeTrabajo registro = new OrdenDeTrabajo();

                registro.setId(rs.getLong(ID));
                registro.setIdEmpleado(rs.getInt(ID_EMPLEADO));
                registro.setFecha(rs.getString(FECHA));
                registro.setPrioridad(rs.getString(PRIORIDAD));
                registro.setSintoma(rs.getString(SINTOMA));
                registro.setUbicacion(rs.getString(UBICACION));
                registro.setDescripcion(rs.getString(DESCRIPCION));
                registro.setEstado(rs.getString(ESTADO));
                registro.setContiene_imagen(rs.getInt(CONTIENE_IMAGEN));

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

            query = "INSERT INTO orden ( id,idEmpleado, fecha, "
                    + "prioridad, sintoma, ubicacion, descripcion, estado,contiene_imagen) "
                    + "VALUES ("
                    + orden.getId() + ", "
                    + orden.getIdEmpleado()
                    + ", '" + orden.getFecha() + "' ,"
                    + "'" + orden.getPrioridad() + "', "
                    + "'" + orden.getSintoma() + "', "
                    + "'" + orden.getUbicacion() + "', "
                    + "'" + orden.getDescripcion() + "', "
                    + "'" + orden.getEstado() + "', "
                    + orden.getContiene_imagen()
                    + " )";

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
                    + ID_EMPLEADO + " = " + orden.getIdEmpleado() + ", "
                    + FECHA + " = " + orden.getFecha() + "' , "
                    + "prioridad = '" + orden.getPrioridad() + "' , "
                    + "sintoma = '" + orden.getSintoma() + "', "
                    + "ubicacion = '" + orden.getUbicacion() + "', "
                    + "descripcion = '" + orden.getDescripcion() + "', "
                    + "estado= '" + orden.getEstado() + "' "
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
            ps = conexion.prepareStatement("DELETE FROM orden  WHERE  id = "
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
