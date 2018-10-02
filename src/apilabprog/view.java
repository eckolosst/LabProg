/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apilabprog;

import java.text.SimpleDateFormat;
import java.util.Date;
import utils.TecladoIn;
import apilabprog.PostgtresController;
import MySQLController.MySQLController;

/**
 *
 * @author nico
 */
public class view {

    public static void main(String[] args) {
        view.showMenu();

    }

    private static void showMenu() {
        System.out.println("Ingrese una opción:");
        System.out.println("");
        System.out.println("1- POSTGRES Ingresar nuevo prestamo");
        System.out.println("2- MYSQL Ingresar nuevo prestamo");
        System.out.println("3- POSTGRES Modificar prestamo");
        System.out.println("4- MYSQL Modificar prestamo");
        System.out.println("5- POSTGRES Borrar prestamo");
        System.out.println("6- MYSQL Borrar prestamo");
        System.out.println("7- POSTGRES Mostrar todos los prestamos");
        System.out.println("8- MYSQL Mostrar todos los prestamos");
        System.out.println("9- Salir");
        int opcion = TecladoIn.readLineInt();
        while (opcion > 5 || opcion < 1) {
            System.out.println("OPCION INCORRECTA, ELIJA 1, 2, 3, 4 o 5.    ¿SE ENTIENDE?");
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
                return;

        }
    }

    private static void nuevoPrestamoPsql() {
        System.out.println("Ingrese el id de la SOLICITUD ASOCIADA ");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la TABLA DE REFERENCIA ASOCIADA ");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("Ingrese el porcentaje de INTERES");
        double interes = TecladoIn.readFloat();
        System.out.println("Ingrese el MONTO TOTAL del prestamo");
        double monto = TecladoIn.readFloat();

        String resultado = PostgtresController.nuevoPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println(resultado);
//        MySQLController.crearPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println("");

    }
    private static void nuevoPrestamoMysql() {
        System.out.println("Ingrese el id de la SOLICITUD ASOCIADA ");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la TABLA DE REFERENCIA ASOCIADA ");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("Ingrese el porcentaje de INTERES");
        double interes = TecladoIn.readFloat();
        System.out.println("Ingrese el MONTO TOTAL del prestamo");
        double monto = TecladoIn.readFloat();

        MySQLController.crearPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println("");

    }

    private static void modificarPrestamoPsql() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la SOLICITUD ASOCIADA ");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la TABLA DE REFERENCIA ASOCIADA ");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("Ingrese el porcentaje de INTERES");
        double interes = TecladoIn.readFloat();
        System.out.println("Ingrese el MONTO TOTAL del prestamo");
        double monto = TecladoIn.readFloat();

        String resultadoPSQL = PostgtresController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
//        MySQLController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println(resultadoPSQL);
        System.out.println("");
    }
    private static void modificarPrestamoMysql() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la SOLICITUD ASOCIADA ");
        int id_solicitud = TecladoIn.readLineInt();
        System.out.println("Ingrese el id de la TABLA DE REFERENCIA ASOCIADA ");
        int id_tabla = TecladoIn.readLineInt();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Current Date: " + ft.format(dNow)); con el formato YYYY/MM/DD
        System.out.println("Ingrese el porcentaje de INTERES");
        double interes = TecladoIn.readFloat();
        System.out.println("Ingrese el MONTO TOTAL del prestamo");
        double monto = TecladoIn.readFloat();

        MySQLController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println("");
    }

    private static void borrarPrestamoPsql() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        PostgtresController.borrarPrestamo(id_prestamo);
    }
    private static void borrarPrestamoMysql() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        MySQLController.borrarPrestamo(id_prestamo);
    }

    private static void showPrestamosPsql() {
        System.out.println(PostgtresController.mostrarPrestamo());
    }
    
    
    private static void showPrestamosMysql() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        MySQLController.mostrarPrestamo(id_prestamo);
    }

}
