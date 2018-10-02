package Controllers;

import java.sql.*;


public class DatabaseConnector
{

    public static final String MYSQL_DRIVER= "com.mysql.jdbc.Driver";
    public static final String POSTGRES_DRIVER="org.postgresql.Driver";
    public static final String MYSQL="mysql";
    public static final String POSTGRES="postgresql";
    public static final String MYSQL_DEFAULT_PORT="3306";
    public static final String POSTGRES_DEFAULT_PORT="5432";


    public Connection conexion;

    public DatabaseConnector(String ipAdress,
                             String port,
                             String database,
                             String user,
                             String password,
                             String databaseEngine){

        String driver="";

        if(databaseEngine.equals(MYSQL))
            driver=MYSQL_DRIVER;
        else if(databaseEngine.equals(POSTGRES))
            driver=POSTGRES_DRIVER;
        else {
            System.out.println("Motor desconocido: \""+databaseEngine+"\"");
            return;
        }

        String url = new String("jdbc:"+ databaseEngine +"://"+ipAdress+":"+port+"/"+database);

        try{
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url,user,password);
            System.out.println("Conexion a Base de Datos: " + database + " Ok");
        }catch (Exception e){
            System.out.println("Error al intentar de abrir la conexión con la base de datos en: " + database + " : " + e + "\n"+ url);
            e.printStackTrace();
        }
    }

    public Connection getConexion(){
        return conexion;
    }

    public Connection closeConnection(){
        try {
            conexion.close();
        }
        catch (SQLException e){
            System.out.println("Error al cerrar la conexión con la base de datos.");
            e.printStackTrace();
        }
        System.out.print("Conexión cerrada.");
        return conexion;
    }
}