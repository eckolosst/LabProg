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
        //msq.modificarPrestamo(4, 0, 0, "'2017-04-05'", 0, 15000);
        //msq.borrarPrestamo(2);
        msq.mostrarPrestamo(4);
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

    public static void crearPrestamo(int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Connection connection = connector.getConexion();
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
                    System.out.println("Error: no existe una solicitud con el id: " + id_solicitud);
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            System.out.println("Prestamo insertado correctamente");
        } else {
            System.out.println("Falló el insert en prestamo");
        }
    }

    public static void modificarPrestamo(int id_prestamo, int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Connection connection = connector.getConexion();
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
                System.out.print("CONSULTA: => ");
                System.out.println("UPDATE Prestamo SET " + vals + " WHERE id_prestamo=" + id_prestamo);
                result = statament.executeUpdate("UPDATE Prestamo SET " + vals + " WHERE id_prestamo=" + id_prestamo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            System.out.println("Prestamo modificado correctamente");
        } else {
            System.out.println("Falló el update en prestamo");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void borrarPrestamo(int id_prestamo) {
        Connection connection = connector.getConexion();
        Statement deletePrestamo = null;
        try {
            deletePrestamo = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        int result = 0;
        try {
            result = deletePrestamo.executeUpdate("DELETE FROM  Prestamo WHERE id_prestamo=" + id_prestamo);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result == 1) {
            System.out.println("Prestamo borrado correctamente");
        } else {
            System.out.println("Falló el delete en prestamo");
        }
    }

    //Mostrar todos los préstamos junto con los datos de la solicitdu (quien lo pidió y garantes).
    public void mostrarPrestamo(int id_prestamo) {
        Connection connection = connector.getConexion();
        Statement selectPrestamo = null;
        String datos = "";
        try {
            selectPrestamo = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        ResultSet result = null;
        try {
            result = selectPrestamo.executeQuery("SELECT * FROM Prestamo JOIN Solicitud_prestamo ON id_prestamo");
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            //System.out.println("| id_prestamo | id_solicitud | id_tabla_referencia | fecha      | tasaInteres | monto | id_solicitud | id_garante | id_socio | fecha      | resultado | monto    |");
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            while (result.next()) {
                System.out.println("id_prestamo: "+result.getInt("id_prestamo"));
                System.out.println("    ->id_solicitud: "+result.getInt(7));
                System.out.println("    ->id_tabla_referencia: "+result.getInt("id_tabla_referencia"));
                System.out.println("    ->fechaSolicitado: "+result.getString("fecha"));
                System.out.println("    ->tasaInteres: "+result.getInt("tasaInteres"));
                System.out.println("    ->montoSolicitado: "+result.getInt("monto"));
                System.out.println("    ->id_garante: "+result.getInt("id_garante"));
                System.out.println("    ->id_socio: "+result.getInt("id_socio"));
                System.out.println("    ->fechaAprobado: "+result.getString(10));
                System.out.println("    ->resultado: "+result.getInt("resultado"));
                System.out.println("    ->montoOtorgado: "+result.getInt(12));
                System.out.println("");
            }
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
