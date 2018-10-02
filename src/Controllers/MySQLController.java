package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ecko
 */
public class MySQLController {

    public static void main(String[] args) {
        MySQLController msq = new MySQLController();
        //msq.modificarPrestamo(4, 0, 0, "'2017-04-05'", 0, 15000);
        //msq.borrarPrestamo(2);
        //msq.mostrarPrestamo(4);
    }

    public static DatabaseConnector connector = new DatabaseConnector("localhost", "3306", "mutual_sol_de_mayo", "root", "", DatabaseConnector.MYSQL);
    private static Connection connection;

    public static void connectDB() {
        connection = connector.getConexion();
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PostgtresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String crearPrestamo(int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Statement newPrestamo = null, selectSolicitud = null;
        try {
            newPrestamo = connection.createStatement();
            selectSolicitud = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        int result = 0;
        ResultSet resSelect = null;
        if (newPrestamo != null) {
            try {
                resSelect = selectSolicitud.executeQuery("SELECT * FROM  Solicitud_prestamo WHERE Solicitud_prestamo.id_solicitud=" + id_solicitud);
                if (resSelect.next()) {
                    result = newPrestamo.executeUpdate("INSERT INTO Prestamo (id_solicitud,id_tabla_referencia,fecha,tasaInteres,monto) "
                            + "VALUES (" + id_solicitud + "," + id_tabla + "," + fecha + "," + tasaInteres + "," + monto + ");");
                } else {
                    return ("Error: no existe una solicitud con el id: " + id_solicitud);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            return ("Prestamo insertado correctamente");
        } else {
            return ("Falló el insert en prestamo");
        }
    }

    public static String modificarPrestamo(int id_prestamo, int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Statement statament = null;
        String vals = "";
        if (id_solicitud != 0) {
            vals += "id_solicitud=" + id_solicitud;
        }
        if (id_tabla != 0) {
            vals += ",id_tabla=" + id_tabla;
        }
        if (fecha != null) {
            vals += ",fecha=" + fecha;
        }
        if (tasaInteres != 0) {
            vals += ",tasaInteres=" + tasaInteres;
        }
        if (monto != 0) {
            vals += ",monto=" + monto;
        }
        try {
            statament = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        int result = 0;
        if (statament != null) {
            try {
                //System.out.print("CONSULTA: => ");
                //System.out.println("UPDATE Prestamo SET " + vals + " WHERE id_prestamo=" + id_prestamo);
                result = statament.executeUpdate("UPDATE Prestamo SET " + vals + " WHERE id_prestamo=" + id_prestamo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            return ("Prestamo modificado correctamente");
        } else {
            return ("Falló el update en prestamo");
        }
    }

    public static String borrarPrestamo(int id_prestamo) {
        Statement deletePrestamo = null;
        try {
            deletePrestamo = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        int result = 0;
        try {
            result = deletePrestamo.executeUpdate("DELETE FROM  Prestamo WHERE id_prestamo=" + id_prestamo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result == 1) {
            return ("Prestamo borrado correctamente");
        } else {
            return ("Falló el delete en prestamo");
        }
    }

    public static String mostrarPrestamo() {
        Statement selectPrestamo = null;
        String datos = "";
        try {
            selectPrestamo = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        ResultSet result;
        try {
            result = selectPrestamo.executeQuery("SELECT * FROM Prestamo JOIN Solicitud_prestamo ON id_prestamo");
            StringBuffer buffer = new StringBuffer();
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            //System.out.println("| id_prestamo | id_solicitud | id_tabla_referencia | fecha      | tasaInteres | monto | id_solicitud | id_garante | id_socio | fecha      | resultado | monto    |");
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            while (result.next()) {
                buffer.append("id_prestamo: ").append(result.getInt("id_prestamo"));
                buffer.append("    ->id_solicitud: ").append(result.getInt(7));
                buffer.append("    ->id_tabla_referencia: ").append(result.getInt("id_tabla_referencia"));
                buffer.append("    ->fechaSolicitado: ").append(result.getString("fecha"));
                buffer.append("    ->tasaInteres: ").append(result.getInt("tasaInteres"));
                buffer.append("    ->montoSolicitado: ").append(result.getInt("monto"));
                buffer.append("    ->id_garante: ").append(result.getInt("id_garante"));
                buffer.append("    ->id_socio: ").append(result.getInt("id_socio"));
                buffer.append("    ->fechaAprobado: ").append(result.getString(10));
                buffer.append("    ->resultado: ").append(result.getInt("resultado"));
                buffer.append("    ->montoOtorgado: ").append(result.getInt(12));
                buffer.append("");
            }
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            return buffer.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
