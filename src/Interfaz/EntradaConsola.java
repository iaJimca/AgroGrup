package Interfaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EntradaConsola {
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero");
            }
        }
    }

    public static double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).replace(",", ".");
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido");
            }
        }
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDate.parse(texto, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Error: use el formato dd/MM/yyyy");
            }
        }
    }

    public static LocalDate leerFechaOpcional(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(texto, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Error: use el formato dd/MM/yyyy");
            }
        }
    }
}
