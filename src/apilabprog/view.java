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
        System.out.println("1- Ingresar nuevo prestamo");
        System.out.println("2- Modificar prestamo");
        System.out.println("3- Borrar prestamo");
        System.out.println("4- Mostrar todos los prestamos");
        System.out.println("5- Salir");
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
                view.nuevoPrestamo();
                view.showMenu();

                break;
            case 2:
                view.modificarPrestamo();
                view.showMenu();
                break;
            case 3:
                view.borrarPrestamo();
                view.showMenu();
                break;
            case 4:
                view.showPrestamos();
                view.showMenu();
                break;
            case 5:
                return;

        }
    }

    private static void nuevoPrestamo() {
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
        MySQLController.crearPrestamo(id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println("");

    }

    private static void modificarPrestamo() {
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
        MySQLController.modificarPrestamo(id_prestamo, id_solicitud, id_tabla, ft.format(dNow), interes, monto);
        System.out.println(resultadoPSQL);
        System.out.println("");
    }

    private static void borrarPrestamo() {
        System.out.println("Ingrese el id del PRESTAMO");
        int id_prestamo = TecladoIn.readLineInt();
        PostgtresController.borrarPrestamo(id_prestamo);
    }

    private static void showPrestamos() {

        System.out.println(PostgtresController.mostrarPrestamo());
    }

}
