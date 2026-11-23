package Interfaz;

import Modelo.Cultivo;
import Modelo.Parcela;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import java.time.LocalDate;

public class MenuPlanSiembra {
    private GestorParcela gestorParcela;
    private GestorCultivo gestorCultivo;

    public MenuPlanSiembra(GestorParcela gestorParcela, GestorCultivo gestorCultivo) {
        this.gestorParcela = gestorParcela;
        this.gestorCultivo = gestorCultivo;
    }

    public void mostrar() {
        System.out.println();
        System.out.println("--- Crear plan de siembra ---");
        System.out.println("Cultivos disponibles:");
        for (Cultivo c : gestorCultivo.getCatalogo()) {
            System.out.println(c.getNombre() + " - " + c.getFamiliaBotanica() + " - " + c.getDiasACosecha() + " días");
        }
        String idParcela = EntradaConsola.leerTexto("ID de parcela: ");
        String tipoPlanta = EntradaConsola.leerTexto("Tipo de planta: ");
        String familia = EntradaConsola.leerTexto("Familia botánica: ");
        LocalDate inicio = EntradaConsola.leerFecha("Fecha de inicio (dd/MM/yyyy): ");
        int semillas = EntradaConsola.leerEntero("Cantidad de semillas: ");
        Parcela parcela = gestorParcela.buscarParcela(idParcela);
        System.out.println(gestorCultivo.crearPlan(parcela, tipoPlanta, familia, inicio, semillas));
    }
}
