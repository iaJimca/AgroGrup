package Interfaz;

import Negocio.GestorUsuario;

public class MenuRegistroAdm {
    private GestorUsuario gestorUsuario;

    public MenuRegistroAdm(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    public void mostrar() {
        System.out.println("--- Registro del administrador ---");
        boolean creada = false;
        while (!creada) {
            String nombre = EntradaConsola.leerTexto("Nombre completo: ");
            String cedula = EntradaConsola.leerTexto("Cédula de identidad: ");
            String correo = EntradaConsola.leerTexto("Correo electrónico: ");
            String contrasena = EntradaConsola.leerTexto("Contraseña: ");
            String mensaje = gestorUsuario.registrarAdministrador(nombre, cedula, correo, contrasena);
            System.out.println(mensaje);
            if (mensaje.startsWith("Cuenta creada")) {
                creada = true;
            }
        }
    }
}
