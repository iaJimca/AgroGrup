package Interfaz;

import Modelo.Administrador;
import Modelo.Rol;
import Modelo.Usuario;
import Negocio.GestorCosecha;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import Negocio.GestorUsuario;

public class MenuAcceso {
    private GestorUsuario gestorUsuario;
    private GestorParcela gestorParcela;
    private GestorCultivo gestorCultivo;
    private GestorCosecha gestorCosecha;

    public MenuAcceso(GestorUsuario gestorUsuario, GestorParcela gestorParcela, GestorCultivo gestorCultivo, GestorCosecha gestorCosecha) {
        this.gestorUsuario = gestorUsuario;
        this.gestorParcela = gestorParcela;
        this.gestorCultivo = gestorCultivo;
        this.gestorCosecha = gestorCosecha;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("--- Inicio de sesión ---");
            System.out.println("1. Administrador");
            System.out.println("2. Comunero o coadministrador");
            System.out.println("0. Salir");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    ingresarAdministrador();
                    break;
                case 2:
                    ingresarPorId();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void ingresarAdministrador() {
        String correo = EntradaConsola.leerTexto("Correo electrónico: ");
        String clave = EntradaConsola.leerTexto("Contraseña: ");
        Administrador administrador = gestorUsuario.iniciarSesionAdministrador(correo, clave);
        if (administrador == null) {
            System.out.println(gestorUsuario.getMensaje());
        } else {
            MenuPrincipal menu = new MenuPrincipal(administrador, gestorUsuario, gestorParcela, gestorCultivo, gestorCosecha);
            menu.mostrar();
        }
    }

    private void ingresarPorId() {
        String id = EntradaConsola.leerTexto("ID de usuario: ");
        Usuario usuario = gestorUsuario.iniciarSesionPorId(id);
        if (usuario == null) {
            System.out.println(gestorUsuario.getMensaje());
        } else if (usuario.getRol() == Rol.COADMINISTRADOR) {
            MenuPrincipal menu = new MenuPrincipal(usuario, gestorUsuario, gestorParcela, gestorCultivo, gestorCosecha);
            menu.mostrar();
        } else {
            MenuComunero menu = new MenuComunero(usuario, gestorParcela, gestorCultivo);
            menu.mostrar();
        }
    }
}
