package MySQLController;

import apilabprog.DatabaseConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ecko
 */
public class MySQLController {

    public static void main(String[] args) {
        MySQLController msq = new MySQLController();
        msq.crearPrestamo(3, 1, "'2018-04-05'", 25, 15000);
    }

    public static DatabaseConnector connector
            = new DatabaseConnector(
                    "localhost",
                    "3306",
                    "mutual_sol_de_mayo",
                    "root",
                    "",
                    DatabaseConnector.MYSQL
            );
    
    /**
     * 1 - Crear un nuevo préstamo (si existe su solicitud) y guardarlo en la base de datos
     * 2 - Modificar un préstamo
     * 3 - Borrar un préstamo 
     * 4 - Mostrar todos los préstamos junto con los datos de la solicitdu (quien lo pidió y garantes).
     */

    public void crearPrestamo(int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Connection connection = connector.getConexion();
        Statement statament = null;
        try {
            statament = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }

        int result = 0;
        if (statament != null) {
            try {
                result = statament.executeUpdate("INSERT INTO Prestamo (id_solicitud,id_tabla_referencia,fecha,tasaInteres,monto) "
                        + "VALUES (" + id_solicitud + "," + id_tabla + "," + fecha + "," + tasaInteres + "," + monto + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            System.out.println("Prestamo insertado correctamente");
        } else {
            System.out.println("Falló el insert en prestamo");
        }
        try {
            connection.close();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
