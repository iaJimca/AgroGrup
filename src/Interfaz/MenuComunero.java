package Interfaz;

import Modelo.Parcela;
import Modelo.Usuario;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MenuComunero {
    private Usuario usuario;
    private GestorParcela gestorParcela;
    private GestorCultivo gestorCultivo;

    public MenuComunero(Usuario usuario, GestorParcela gestorParcela, GestorCultivo gestorCultivo) {
        this.usuario = usuario;
        this.gestorParcela = gestorParcela;
        this.gestorCultivo = gestorCultivo;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            gestorParcela.actualizarEstados();
            System.out.println();
            System.out.println("--- Menú del comunero: " + usuario.getNombreCompleto() + " ---");
            System.out.println("1. Ver mi perfil");
            System.out.println("2. Ver mis parcelas");
            System.out.println("3. Ver mapa del huerto");
            System.out.println("0. Cerrar sesión");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    verPerfil();
                    break;
                case 2:
                    verParcelas();
                    break;
                case 3:
                    new MenuMapa(usuario, gestorParcela).mostrarMapa();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void verPerfil() {
        System.out.println("ID: " + usuario.getIdUsuario());
        System.out.println("Nombre: " + usuario.getNombreCompleto());
        System.out.println("Cédula: " + usuario.getCedula());
        System.out.println("Correo: " + usuario.getCorreo());
        System.out.println("Rol: " + usuario.getRol());
    }

    private void verParcelas() {
        ArrayList<Parcela> parcelas = gestorParcela.parcelasDe(usuario);
        if (parcelas.isEmpty()) {
            System.out.println("No tiene parcelas asignadas");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Parcela p : parcelas) {
            String linea = p.getIdParcela() + " - " + p.getAreaM2() + " m2 - " + p.getEstado();
            if (p.getFinDescanso() != null) {
                linea = linea + " - descansa hasta " + p.getFinDescanso().format(formato);
            }
            System.out.println(linea);
        }
    }
}
