package test;

import Controllers.DatabaseConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by maxi on 08/09/16.
 */
public class MysqlConectionTest {

    public static void main (String a[]){
        DatabaseConnector connector;
        connector= new DatabaseConnector(
                "localhost",
                "3306",
                "mutual_sol_de_mayo",
                "root",
                "",
                DatabaseConnector.MYSQL
        );

        Connection connection=connector.getConexion();
        Statement selectCategorias=null;
        try {
            selectCategorias = connection.createStatement();
        }catch (Exception e){}

        ResultSet todasLasCategorias=null;
        if(selectCategorias!=null)
            try {
                todasLasCategorias= selectCategorias.executeQuery("select * from accidente");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(todasLasCategorias!=null)
            try {
                while(todasLasCategorias.next())
                    System.out.println(todasLasCategorias.getString("idaccidente")+" "+
                            todasLasCategorias.getString("fecha")+" "+
                            todasLasCategorias.getString("cantidadheridos")+" "+
                            todasLasCategorias.getString("localidad"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
