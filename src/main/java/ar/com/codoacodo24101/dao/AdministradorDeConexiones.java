package ar.com.codoacodo24101.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdministradorDeConexiones {
    
    public static Connection conectar() {
        String url = "jdbc:mysql://localhost:3306/24101?serverTimeZone=UTC&userSSL=false";
        String user = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
        return con;
    }

    public static void desconectar(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }
    
}
