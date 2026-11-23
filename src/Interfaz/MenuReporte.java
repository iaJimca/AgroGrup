package Interfaz;

import Modelo.Cosecha;
import Modelo.CuotaDistribucion;
import Modelo.Usuario;
import Negocio.GestorCosecha;
import Negocio.GestorParcela;
import Negocio.GestorUsuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MenuReporte {
    private GestorUsuario gestorUsuario;
    private GestorParcela gestorParcela;
    private GestorCosecha gestorCosecha;

    public MenuReporte(GestorUsuario gestorUsuario, GestorParcela gestorParcela, GestorCosecha gestorCosecha) {
        this.gestorUsuario = gestorUsuario;
        this.gestorParcela = gestorParcela;
        this.gestorCosecha = gestorCosecha;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("--- Reportes ---");
            System.out.println("1. Distribución de una cosecha");
            System.out.println("2. Historial de producción");
            System.out.println("0. Volver");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    distribuir();
                    break;
                case 2:
                    historial();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void distribuir() {
        if (gestorCosecha.getCosechas().isEmpty()) {
            System.out.println("No hay cosechas registradas");
            return;
        }
        for (Cosecha c : gestorCosecha.getCosechas()) {
            System.out.println(c.getIdCosecha() + " - Parcela " + c.getPlan().getParcela().getIdParcela() + " - " + c.getPlan().getCultivo().getNombre() + " - " + c.getPesoKg() + " kg");
        }
        String id = EntradaConsola.leerTexto("ID de la cosecha: ");
        Cosecha cosecha = gestorCosecha.buscarCosecha(id);
        if (cosecha == null) {
            System.out.println("Error: Cosecha inexistente");
            return;
        }
        ArrayList<Usuario> colaboradores = gestorParcela.usuariosConParcela(gestorUsuario.getUsuarios());
        ArrayList<CuotaDistribucion> cuotas = gestorCosecha.distribuir(cosecha, colaboradores);
        if (cuotas.isEmpty()) {
            System.out.println("Error: Debe existir al menos un colaborador");
            return;
        }
        System.out.println("Distribución de " + cosecha.getIdCosecha() + " entre " + cuotas.size() + " colaboradores:");
        for (CuotaDistribucion c : cuotas) {
            System.out.println(c.mostrarReporte());
        }
    }

    private void historial() {
        String idParcela = EntradaConsola.leerTexto("ID de parcela (vacío para todas): ");
        LocalDate desde = EntradaConsola.leerFechaOpcional("Desde (dd/MM/yyyy, vacío para omitir): ");
        LocalDate hasta = EntradaConsola.leerFechaOpcional("Hasta (dd/MM/yyyy, vacío para omitir): ");
        ArrayList<Cosecha> lista = gestorCosecha.historial(idParcela, desde, hasta);
        if (lista.isEmpty()) {
            System.out.println("No hay cosechas para ese filtro");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double total = 0;
        for (Cosecha c : lista) {
            System.out.println(c.getIdCosecha() + " - Parcela " + c.getPlan().getParcela().getIdParcela() + " - " + c.getPlan().getCultivo().getNombre() + " - " + c.getPesoKg() + " kg - " + c.getCantidad() + " unidades - " + c.getFecha().format(formato));
            total = total + c.getPesoKg();
        }
        System.out.println("Peso total: " + total + " kg");
    }
}
