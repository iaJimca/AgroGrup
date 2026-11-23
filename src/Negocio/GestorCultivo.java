package Negocio;

import Modelo.Cultivo;
import Modelo.EstadoParcela;
import Modelo.NivelDesgaste;
import Modelo.Parcela;
import Modelo.Plansiembra;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestorCultivo {
    private ArrayList<Cultivo> catalogo;
    private ArrayList<Plansiembra> planes;
    private DateTimeFormatter formato;

    public GestorCultivo() {
        catalogo = new ArrayList<>();
        planes = new ArrayList<>();
        formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        catalogo.add(new Cultivo("Tomate", "Solanáceas", 90, NivelDesgaste.ALTO));
        catalogo.add(new Cultivo("Col", "Brasicáceas", 90, NivelDesgaste.ALTO));
        catalogo.add(new Cultivo("Zanahoria", "Apiáceas", 90, NivelDesgaste.MEDIO));
        catalogo.add(new Cultivo("Lechuga", "Asteráceas", 60, NivelDesgaste.MEDIO));
        catalogo.add(new Cultivo("Fréjol", "Leguminosas", 80, NivelDesgaste.BAJO));
    }

    public Cultivo buscarCultivo(String nombre) {
        for (Cultivo c : catalogo) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    private boolean existeFamilia(String familia) {
        for (Cultivo c : catalogo) {
            if (c.getFamiliaBotanica().equalsIgnoreCase(familia)) {
                return true;
            }
        }
        return false;
    }

    private boolean tienePlanActivo(Parcela parcela) {
        for (Plansiembra p : planes) {
            if (p.getParcela() == parcela && !p.isCosechado()) {
                return true;
            }
        }
        return false;
    }

    public String crearPlan(Parcela parcela, String tipoPlanta, String familia, LocalDate inicio, int semillas) {
        if (parcela == null) {
            return "Error: Parcela inexistente, ID de parcela inválido";
        }
        if (tipoPlanta.isEmpty() || familia.isEmpty()) {
            return "Error: Campos obligatorios incompletos";
        }
        Cultivo cultivo = buscarCultivo(tipoPlanta);
        if (cultivo == null) {
            return "Error: Tipo de siembra inexistente";
        }
        if (!existeFamilia(familia)) {
            return "Error: Familia botánica inexistente";
        }
        if (!cultivo.getFamiliaBotanica().equalsIgnoreCase(familia)) {
            return "Error: La familia botánica no corresponde al cultivo";
        }
        if (semillas <= 0) {
            return "Error: La cantidad de semillas debe ser mayor a 0";
        }
        if (parcela.getEstado() == EstadoParcela.EN_DESCANSO) {
            return "Parcela en descanso hasta el " + parcela.getFinDescanso().format(formato);
        }
        if (parcela.getEstado() != EstadoParcela.ASIGNADA) {
            return "Error: La parcela debe estar asignada a un comunero";
        }
        if (tienePlanActivo(parcela)) {
            return "Error: La parcela ya tiene un plan de siembra activo";
        }
        String id = String.format("PLAN-%03d", planes.size() + 1);
        Plansiembra plan = new Plansiembra(id, parcela, cultivo, inicio, semillas);
        if (!plan.rotacionValida()) {
            return "Error: Rotación inválida, no se puede repetir la misma familia botánica en la siguiente temporada";
        }
        planes.add(plan);
        return "Plan de siembra creado con éxito. Pronto se cosechará. ID: " + id + "\nSe podrá cosechar el " + plan.getFechaCosecha().format(formato);
    }

    public Plansiembra buscarPlan(String id) {
        for (Plansiembra p : planes) {
            if (p.getIdPlan().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Cultivo> getCatalogo() {
        return catalogo;
    }

    public ArrayList<Plansiembra> getPlanes() {
        return planes;
    }
}
