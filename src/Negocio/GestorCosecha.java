package Negocio;

import Modelo.Cosecha;
import Modelo.CuotaDistribucion;
import Modelo.Plansiembra;
import Modelo.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestorCosecha {
    private ArrayList<Cosecha> cosechas;

    public GestorCosecha() {
        cosechas = new ArrayList<>();
    }

    public String registrarCosecha(Plansiembra plan, double peso, int cantidad) {
        if (plan == null) {
            return "Error: Plan de cultivo inexistente";
        }
        if (peso <= 0 || cantidad <= 0) {
            return "Error: El peso y la cantidad deben ser mayores a 0";
        }
        if (plan.isCosechado()) {
            return "Error: El plan ya fue cosechado";
        }
        String id = String.format("COS-%03d", cosechas.size() + 1);
        Cosecha cosecha = new Cosecha(id, plan, peso, cantidad, LocalDate.now());
        cosecha.registrar();
        cosechas.add(cosecha);
        return "Cosecha registrada con éxito. ID: " + id;
    }

    public Cosecha buscarCosecha(String id) {
        for (Cosecha c : cosechas) {
            if (c.getIdCosecha().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<CuotaDistribucion> distribuir(Cosecha cosecha, ArrayList<Usuario> colaboradores) {
        return cosecha.distribuir(colaboradores);
    }

    public ArrayList<Cosecha> historial(String idParcela, LocalDate desde, LocalDate hasta) {
        ArrayList<Cosecha> resultado = new ArrayList<>();
        for (Cosecha c : cosechas) {
            boolean cumple = true;
            if (!idParcela.isEmpty() && !c.getPlan().getParcela().getIdParcela().equalsIgnoreCase(idParcela)) {
                cumple = false;
            }
            if (desde != null && c.getFecha().isBefore(desde)) {
                cumple = false;
            }
            if (hasta != null && c.getFecha().isAfter(hasta)) {
                cumple = false;
            }
            if (cumple) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<Cosecha> getCosechas() {
        return cosechas;
    }
}
