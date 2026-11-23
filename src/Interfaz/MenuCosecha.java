package Interfaz;

import Modelo.Plansiembra;
import Negocio.GestorCosecha;
import Negocio.GestorCultivo;

public class MenuCosecha {
    private GestorCosecha gestorCosecha;
    private GestorCultivo gestorCultivo;

    public MenuCosecha(GestorCosecha gestorCosecha, GestorCultivo gestorCultivo) {
        this.gestorCosecha = gestorCosecha;
        this.gestorCultivo = gestorCultivo;
    }

    public void mostrar() {
        System.out.println();
        System.out.println("--- Registrar cosecha ---");
        boolean hayPendientes = false;
        for (Plansiembra p : gestorCultivo.getPlanes()) {
            if (!p.isCosechado()) {
                System.out.println(p.getIdPlan() + " - Parcela " + p.getParcela().getIdParcela() + " - " + p.getCultivo().getNombre());
                hayPendientes = true;
            }
        }
        if (!hayPendientes) {
            System.out.println("No hay planes pendientes de cosecha");
            return;
        }
        String idPlan = EntradaConsola.leerTexto("ID del plan: ");
        double peso = EntradaConsola.leerDecimal("Peso obtenido (kg): ");
        int cantidad = EntradaConsola.leerEntero("Cantidad cosechada: ");
        Plansiembra plan = gestorCultivo.buscarPlan(idPlan);
        System.out.println(gestorCosecha.registrarCosecha(plan, peso, cantidad));
    }
}
