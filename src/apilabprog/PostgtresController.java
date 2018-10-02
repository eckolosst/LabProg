package apilabprog;

import apilabprog.DatabaseConnector;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nico
 */
public class PostgtresController {

//    public static void main(String[] args) {
//        PostgtresController.nuevoPrestamo(1, 1, "2018-10-10", 40.0, 666.0);
//
//    }
    public static DatabaseConnector connector = new DatabaseConnector(
            "localhost",
            "5432",
            "MUTUAL_SOL_DE_MAYO",
            "postgres",
            "123",
            DatabaseConnector.POSTGRES);

    public static void borrarPrestamo(int id_prestamo) {
        Connection connection = connector.getConexion();
        Statement deletePrestamo = null;
        try {
            deletePrestamo = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        int result = 0;
        try {
            result = deletePrestamo.executeUpdate("DELETE FROM  mutual.prestamo WHERE mutual.prestamo.id_prestamo=" + id_prestamo);
            connection.close();
        } catch (SQLException e) {
             System.out.println(e);
        }
        if (result == 1) {
            System.out.println("Prestamo borrado correctamente");
        } else {
            System.out.println("Falló el delete en prestamo");
        }
    }

    public static String nuevoPrestamo(int id_solicitud, int id_tabla_referencia, String fecha, Double tasainteres, Double monto) {

        Connection connection = connector.getConexion();
        Statement newPrestamo = null;
        Statement selectSolicitud = null;
        try {
            newPrestamo = connection.createStatement();
            selectSolicitud = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

        int result = 0;
        ResultSet resultadoSelect;
        if (newPrestamo != null) {
            try {
                resultadoSelect = selectSolicitud.executeQuery("select * from mutual.solicitudprestamo "
                        + "where mutual.solicitudprestamo.id_solicitud =" + id_solicitud);
                System.out.println(resultadoSelect.next());
                if (resultadoSelect.next()) {
                    result = newPrestamo.executeUpdate("INSERT INTO mutual.prestamo "
                            + "(id_solicitud,id_tabla_referencia,fecha,tasainteres,monto)\n"
                            + "VALUES ("
                            + id_solicitud + ","
                            + id_tabla_referencia + ","
                            + "'" + fecha + "'" + ","
                            + tasainteres + ","
                            + monto + ");"
                    );
                }
                System.out.println("--------------------->" + result);
                connection.close();

            } catch (SQLException e) {
                System.out.println(e);
            }

        }
        if (result == 1) {
            return ("Nuevo prestamo insertado");
        } else {
            return ("NO SE INSERTO PRESTAMO");
        }

    }

    public static String modificarPrestamo(int id_prestamo, int id_solicitud, int id_tabla, String fecha, double tasaInteres, double monto) {
        Connection connection = connector.getConexion();
        Statement statament = null;
        try {
            statament = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

        String vals = "";
        if (id_solicitud != 0) {
            vals += "id_solicitud=" + id_solicitud;
        }
        if (id_tabla != 0) {
            vals += ",id_tabla_referencia=" + id_tabla;
        }
        if (fecha != null) {
            vals += ",fecha='" + fecha + "'";
        }
        if (tasaInteres != 0) {
            vals += ",tasaInteres=" + tasaInteres;
        }
        if (monto != 0) {
            vals += ",monto=" + monto;
        }
        try {
            statament = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        int result = 0;
        if (statament != null) {
            try {
                System.out.print("CONSULTA: => ");
                System.out.println("UPDATE mutual.prestamo SET " + vals + " WHERE mutual.prestamo.id_prestamo=" + id_prestamo);
                result = statament.executeUpdate("UPDATE mutual.prestamo SET " + vals + " WHERE mutual.prestamo.id_prestamo=" + id_prestamo);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (result == 1) {
            return ("Nuevo prestamo insertado");
        } else {
            return ("NO SE INSERTO PRESTAMO");
        }

    }
    
    
     //Mostrar todos los préstamos junto con los datos de la solicitdu (quien lo pidió y garantes).
    public static void mostrarPrestamo(int id_prestamo) {
        Connection connection = connector.getConexion();
        Statement selectPrestamo = null;
        String datos = "";
        try {
            selectPrestamo = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        ResultSet result;
        try {
            result = selectPrestamo.executeQuery("SELECT * FROM mutual.prestamo NATURAL JOIN mutual.solicitudprestamo ;");
            System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------");
            System.out.println("| id_prestamo | id_solicitud | id_tabla_referencia | fecha      | tasaInteres | monto |   id_garante | id_socio | fecha      | resultado | monto    |");
            System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+------");
            while (result.next()) {
                System.out.println(result.getInt("id_prestamo")+"                      "+
                        result.getInt("id_solicitud")+"                  "+
                     result.getInt("id_tabla_referencia") +"      "+
                        result.getString("fecha")+"     "+
                        result.getInt("tasaInteres")+"             "+
                        result.getInt("monto")+"       "+
                        result.getInt("id_garante")+"               "+
                        result.getInt("id_socio")
                        );
//                System.out.println("    ->id_solicitud: "+result.getInt("id_solicitud"));
//                System.out.println("    ->id_tabla_referencia: "+result.getInt("id_tabla_referencia"));
//                System.out.println("    ->fechaSolicitado: "+result.getString("fecha"));
//                System.out.println("    ->tasaInteres: "+result.getInt("tasaInteres"));
//                System.out.println("    ->montoSolicitado: "+result.getInt("monto"));
//                System.out.println("    ->id_garante: "+result.getInt("id_garante"));
//                System.out.println("    ->id_socio: "+result.getInt("id_socio"));
//                System.out.println("    ->fechaAprobado: "+result.getString(10));
//                System.out.println("    ->resultado: "+result.getInt("resultado"));
//                System.out.println("    ->montoOtorgado: "+result.getInt(12));
                System.out.println("");
            }
            //System.out.println("+-------------+--------------+---------------------+------------+-------------+-------+--------------+------------+----------+------------+-----------+----------+");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
