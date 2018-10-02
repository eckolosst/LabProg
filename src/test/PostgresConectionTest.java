package test;

import Controllers.DatabaseConnector;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by maxi on 08/09/16.
 */
public class PostgresConectionTest {

    public static void main (String a[]){
        DatabaseConnector connector;
        connector= new DatabaseConnector(
                "localhost",
                "5432",
                "MUTUAL_SOL_DE_MAYO",
                "postgres",
                "123",
                DatabaseConnector.POSTGRES);

        // Error de Autenticación? :  "show hba_file;" muestra la ubicación del archivo de permisos
        // Para habilitar el acceso local:
        // host    all             all             127.0.0.1/32            trust

        Connection connection=connector.getConexion();
        Statement selectCategorias=null;
        try {
            selectCategorias = connection.createStatement();
        }catch (Exception e){}

        ResultSet todasLasCategorias=null;
        if(selectCategorias!=null)
            try {
                todasLasCategorias= selectCategorias.executeQuery("select * from mutual.prestamo");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(todasLasCategorias!=null)
            try {
                while(todasLasCategorias.next())
                    System.out.println(todasLasCategorias.getString("id_prestamo")
                            /*+" "+
                            todasLasCategorias.getString("fecha")+" "+
                            todasLasCategorias.getString("cantidadheridos")+" "+
                            todasLasCategorias.getString("localidad")*/
                    );
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

}
