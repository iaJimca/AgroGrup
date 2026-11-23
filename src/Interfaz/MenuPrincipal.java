package Interfaz;

import Modelo.Usuario;
import Negocio.GestorCosecha;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import Negocio.GestorUsuario;

public class MenuPrincipal {
    private Usuario usuario;
    private GestorUsuario gestorUsuario;
    private GestorParcela gestorParcela;
    private GestorCultivo gestorCultivo;
    private GestorCosecha gestorCosecha;

    public MenuPrincipal(Usuario usuario, GestorUsuario gestorUsuario, GestorParcela gestorParcela, GestorCultivo gestorCultivo, GestorCosecha gestorCosecha) {
        this.usuario = usuario;
        this.gestorUsuario = gestorUsuario;
        this.gestorParcela = gestorParcela;
        this.gestorCultivo = gestorCultivo;
        this.gestorCosecha = gestorCosecha;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            gestorParcela.actualizarEstados();
            System.out.println();
            System.out.println("--- Menú principal: " + usuario.getNombreCompleto() + " (" + usuario.getRol() + ") ---");
            System.out.println("1. Crear usuarios");
            System.out.println("2. Mapa del huerto");
            System.out.println("3. Asignar parcela");
            System.out.println("4. Crear plan de siembra");
            System.out.println("5. Ver planes y descansos");
            System.out.println("6. Registrar cosecha");
            System.out.println("7. Reportes");
            System.out.println("0. Cerrar sesión");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    if (usuario.tienePermiso("CREAR_USUARIO")) {
                        new MenuUsuario(gestorUsuario).mostrar();
                    } else {
                        sinPermiso();
                    }
                    break;
                case 2:
                    new MenuMapa(usuario, gestorParcela).mostrar();
                    break;
                case 3:
                    if (usuario.tienePermiso("ASIGNAR_PARCELA")) {
                        new MenuAsignacion(gestorParcela, gestorUsuario).mostrar();
                    } else {
                        sinPermiso();
                    }
                    break;
                case 4:
                    if (usuario.tienePermiso("REGISTRAR_PLAN")) {
                        new MenuPlanSiembra(gestorParcela, gestorCultivo).mostrar();
                    } else {
                        sinPermiso();
                    }
                    break;
                case 5:
                    new MenuPlanes(gestorParcela, gestorCultivo).mostrar();
                    break;
                case 6:
                    if (usuario.tienePermiso("REGISTRAR_COSECHA")) {
                        new MenuCosecha(gestorCosecha, gestorCultivo).mostrar();
                    } else {
                        sinPermiso();
                    }
                    break;
                case 7:
                    new MenuReporte(gestorUsuario, gestorParcela, gestorCosecha).mostrar();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void sinPermiso() {
        System.out.println("Error: No tiene permisos para esta operación");
    }
}
