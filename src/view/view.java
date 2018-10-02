/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import utils.TecladoIn;
import Controllers.PostgtresController;
import Controllers.MySQLController;

/**
 *
 * @author nico
 */
public class view {

    public static void main(String[] args) {
//        MySQLController.connectDB();
        PostgtresController.connectDB();
        view.showMenu();

    }

    private static void showMenu() {
        System.out.println("+-----------------------------------------+");
        System.out.println("|         Ingrese una opción:             |");
        System.out.println("+-----------------------------------------+");
        System.out.println("| 1- POSTGRES Ingresar nuevo prestamo     |");
        System.out.println("| 2- MYSQL Ingresar nuevo prestamo        |");
        System.out.println("| 3- POSTGRES Modificar prestamo          |");
        System.out.println("| 4- MYSQL Modificar prestamo             |");
        System.out.println("| 5- POSTGRES Borrar prestamo             |");
        System.out.println("| 6- MYSQL Borrar prestamo                |");
        System.out.println("| 7- POSTGRES Mostrar todos los prestamos |");
        System.out.println("| 8- MYSQL Mostrar todos los prestamos    |");
        System.out.println("| 9- Salir                                |");
        System.out.println("+-----------------------------------------+");
        int opcion = TecladoIn.readLineInt();
        while (opcion > 9 || opcion < 1) {
            System.out.println("OPCION INCORRECTA, ELIJA 1, 2, 3, 4, 5, 6, 7, 8 o 9");
            opcion = TecladoIn.readLineInt();
        }

        view.switchear(opcion);
    }

    private static void switchear(int opcion) {
        switch (opcion) {
            case 1:
                view.nuevoPrestamoPsql();
                view.showMenu();

                break;
            case 2:
                view.nuevoPrestamoMysql();
                view.showMenu();

                break;
            case 3:
                view.modificarPrestamoPsql();
                view.showMenu();
                break;
            case 4:
                view.modificarPrestamoMysql();
                view.showMenu();
                break;
            case 5:
                view.borrarPrestamoPsql();
                view.showMenu();
                break;
            case 6:
                view.borrarPrestamoMysql();
                view.showMenu();
                break;
            case 7:
                view.showPrestamosPsql();
                view.showMenu();
                break;
            case 8:
                view.showPrestamosMysql();
                view.showMenu();
                break;
            case 9:
                PostgtresController.closeConnection();
//                MySQLController.close9Connection();
                System.out.println(""
                        + " ________\n"
                        + "|￣￣￣￣￣| \n"
                        + "|   BYE! |\n"
                        + "|________|\n"
                        + "(\\__/) ||\n"
                        + "(•ㅅ•) || \n"
                        + "/ 　 づ");
                return;

        }
    }

    private static void nuevoPrestamoPsql() {
        System.out.println("+-----------------------------------------+");
        System.out.println("| Ingrese el id de la SOLICITUD ASOCIADA  |");
        System.out.println("+-----------------------------------------+");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("+--------------------------------------------------+");
        System.out.println("| Ingrese el id de la TABLA DE REFERENCIA ASOCIADA |");
        System.out.println("+--------------------------------------------------+");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("+----------------------------------+");
        System.out.println("| Ingrese el porcentaje de INTERES |");
        System.out.println("+----------------------------------+");
        double interes = TecladoIn.readFloat();
        System.out.println("+-------------------------------------+");
        System.out.println("| Ingrese el MONTO TOTAL del prestamo |");
        System.out.println("+-------------------------------------+");
        double monto = TecladoIn.readFloat();

        String resultado = PostgtresController.nuevoPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println(resultado);
        System.out.println("");

    }

    private static void nuevoPrestamoMysql() {
        System.out.println("+----------------------------------------+");
        System.out.println("| Ingrese el id de la SOLICITUD ASOCIADA |");
        System.out.println("+----------------------------------------+");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("+--------------------------------------------------+");
        System.out.println("| Ingrese el id de la TABLA DE REFERENCIA ASOCIADA |");
        System.out.println("+--------------------------------------------------+");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("+----------------------------------+");
        System.out.println("| Ingrese el porcentaje de INTERES |");
        System.out.println("+----------------------------------+");
        double interes = TecladoIn.readFloat();
        System.out.println("+-------------------------------------+");
        System.out.println("| Ingrese el MONTO TOTAL del prestamo |");
        System.out.println("+-------------------------------------+");
        double monto = TecladoIn.readFloat();

        System.out.println(MySQLController.crearPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto));
        System.out.println("");

    }

    private static void modificarPrestamoPsql() {
        System.out.println("+----------------------------+");
        System.out.println("| Ingrese el id del PRESTAMO |");
        System.out.println("+----------------------------+");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println("+---------------------------------------+");
        System.out.println("|Ingrese el id de la SOLICITUD ASOCIADA |");
        System.out.println("+---------------------------------------+");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("+--------------------------------------------------+");
        System.out.println("| Ingrese el id de la TABLA DE REFERENCIA ASOCIADA |");
        System.out.println("+--------------------------------------------------+");

        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("+----------------------------------+");
        System.out.println("| Ingrese el porcentaje de INTERES |");
        System.out.println("+----------------------------------+");
        double interes = TecladoIn.readFloat();
        System.out.println("+-------------------------------------+");
        System.out.println("| Ingrese el MONTO TOTAL del prestamo |");
        System.out.println("+-------------------------------------+");
        double monto = TecladoIn.readFloat();

        String resultadoPSQL = PostgtresController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println(resultadoPSQL);
        System.out.println("");
    }

    private static void modificarPrestamoMysql() {
        System.out.println("+----------------------------+");
        System.out.println("| Ingrese el id del PRESTAMO |");
        System.out.println("+----------------------------+");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println("+----------------------------------------+");
        System.out.println("| Ingrese el id de la SOLICITUD ASOCIADA |");
        System.out.println("+----------------------------------------+");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("+--------------------------------------------------+");
        System.out.println("| Ingrese el id de la TABLA DE REFERENCIA ASOCIADA |");
        System.out.println("+--------------------------------------------------+");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("+----------------------------------+");
        System.out.println("| Ingrese el porcentaje de INTERES |");
        System.out.println("+----------------------------------+");
        double interes = TecladoIn.readFloat();
        System.out.println("+-------------------------------------+");
        System.out.println("| Ingrese el MONTO TOTAL del prestamo |");
        System.out.println("+-------------------------------------+");
        double monto = TecladoIn.readFloat();

        String resultadoMySQL = MySQLController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println("");
    }

    private static void borrarPrestamoPsql() {
        System.out.println("+----------------------------+");
        System.out.println("| Ingrese el id del PRESTAMO |");
        System.out.println("+----------------------------+");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println(PostgtresController.borrarPrestamo(id_prestamo));
    }

    private static void borrarPrestamoMysql() {
        System.out.println("+----------------------------+");
        System.out.println("| Ingrese el id del PRESTAMO |");
        System.out.println("+----------------------------+");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println(MySQLController.borrarPrestamo(id_prestamo));
    }

    private static void showPrestamosPsql() {
        System.out.println(PostgtresController.mostrarPrestamo());
    }

    private static void showPrestamosMysql() {
        System.out.println(MySQLController.mostrarPrestamo());
    }

}
