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

    public static void main(String[] args) {
        PostgtresController controller = new PostgtresController();
        controller.nuevoPrestamo(1, 1, "'2018/10/19'", 888.0, 999.00);
    }

    public static DatabaseConnector connector = new DatabaseConnector(
            "localhost",
            "5432",
            "MUTUAL_SOL_DE_MAYO",
            "postgres",
            "123",
            DatabaseConnector.POSTGRES);

    public void nuevoPrestamo(int id_solicitud, int id_tabla_referencia, String fecha, Double tasainteres, Double monto) {

        Connection connection = connector.getConexion();
        Statement newPrestamo = null;
        Statement selectSolicitud = null;
        try {
            newPrestamo = connection.createStatement();
            selectSolicitud = connection.createStatement();
        } catch (Exception e) {
        }

        int result = 0;
        ResultSet resultadoSelect = null;
        if (newPrestamo != null) {
            try {
                resultadoSelect = selectSolicitud.executeQuery("select * from mutual.solicitudprestamo where mutual.solicitudprestamo.id_solicitud =" + id_solicitud);
                System.out.println(resultadoSelect.next());
                if (resultadoSelect.next()) {
                    result = newPrestamo.executeUpdate("INSERT INTO mutual.prestamo "
                            + "(id_solicitud,id_tabla_referencia,fecha,tasainteres,monto)\n"
                            + "VALUES ("
                            + id_solicitud + ","
                            + id_tabla_referencia + ","
                            + fecha + ","
                            + tasainteres + ","
                            + monto + ");"
                    );
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 1) {
            System.out.println("Nuevo prestamo insertado");
        } else {
            System.out.println("Error insertando prestamo");
        }

    }
}
