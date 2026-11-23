package Interfaz;

import Modelo.EstadoParcela;
import Modelo.Parcela;
import Modelo.Plansiembra;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import java.time.format.DateTimeFormatter;

public class MenuPlanes {
    private GestorParcela gestorParcela;
    private GestorCultivo gestorCultivo;

    public MenuPlanes(GestorParcela gestorParcela, GestorCultivo gestorCultivo) {
        this.gestorParcela = gestorParcela;
        this.gestorCultivo = gestorCultivo;
    }

    public void mostrar() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println();
        System.out.println("--- Planes de siembra ---");
        if (gestorCultivo.getPlanes().isEmpty()) {
            System.out.println("No hay planes registrados");
        }
        for (Plansiembra p : gestorCultivo.getPlanes()) {
            String estado = "En curso";
            if (p.isCosechado()) {
                estado = "Cosechado";
            }
            System.out.println(p.getIdPlan() + " - Parcela " + p.getParcela().getIdParcela() + " - " + p.getCultivo().getNombre() + " - siembra " + p.getFechaInicio().format(formato) + " - cosecha " + p.getFechaCosecha().format(formato) + " - " + estado);
        }
        System.out.println();
        System.out.println("--- Parcelas en descanso ---");
        boolean hayDescanso = false;
        if (gestorParcela.getMapa() != null) {
            for (Parcela p : gestorParcela.getMapa().getParcelas()) {
                if (p.getEstado() == EstadoParcela.EN_DESCANSO) {
                    System.out.println("Parcela " + p.getIdParcela() + " en descanso hasta el " + p.getFinDescanso().format(formato));
                    hayDescanso = true;
                }
            }
        }
        if (!hayDescanso) {
            System.out.println("No hay parcelas en descanso");
        }
    }
}
