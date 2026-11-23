package Interfaz;

import Modelo.Rol;
import Modelo.Usuario;
import Negocio.GestorUsuario;

public class MenuUsuario {
    private GestorUsuario gestorUsuario;

    public MenuUsuario(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("--- Usuarios ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("0. Volver");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    verUsuarios();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void crearUsuario() {
        String nombre = EntradaConsola.leerTexto("Nombre completo: ");
        String cedula = EntradaConsola.leerTexto("Cédula de identidad: ");
        String correo = EntradaConsola.leerTexto("Correo electrónico: ");
        System.out.println("1. Comunero");
        System.out.println("2. Coadministrador");
        int opcionRol = EntradaConsola.leerEntero("Rol: ");
        Rol rol = null;
        if (opcionRol == 1) {
            rol = Rol.COMUNERO;
        } else if (opcionRol == 2) {
            rol = Rol.COADMINISTRADOR;
        }
        System.out.println(gestorUsuario.crearUsuario(nombre, cedula, correo, rol));
    }

    private void verUsuarios() {
        if (gestorUsuario.getUsuarios().isEmpty()) {
            System.out.println("No hay usuarios creados");
            return;
        }
        for (Usuario u : gestorUsuario.getUsuarios()) {
            System.out.println(u.getIdUsuario() + " - " + u.getNombreCompleto() + " - " + u.getRol());
        }
    }
}
