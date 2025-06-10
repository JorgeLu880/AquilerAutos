
package util;


import util.Conexion;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection con = Conexion.getConnection();
        if (con != null) {
            System.out.println("Conexion exitosa a la base de datos.");
        } else {
            System.out.println("Fallo en la conexión.");
        }
    }
}
