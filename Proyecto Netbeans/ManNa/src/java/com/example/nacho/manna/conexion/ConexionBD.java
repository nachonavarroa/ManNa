package com.example.nacho.manna.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nacho
 */

public class ConexionBD {

    static final String URL_MYSQL   = "jdbc:mysql://localhost:3306/manna";
    static final String USUARIO_BD  = "root";
    static final String CONTRA_BD   = "";

    public static Connection getConexion() {
        try {
            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL_MYSQL, USUARIO_BD, CONTRA_BD);

            return conexion;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
