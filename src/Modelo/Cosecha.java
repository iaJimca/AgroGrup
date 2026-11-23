package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cosecha {
    private String idCosecha;
    private Plansiembra plan;
    private double pesoKg;
    private int cantidad;
    private LocalDate fecha;

    public Cosecha(String idCosecha, Plansiembra plan, double pesoKg, int cantidad, LocalDate fecha) {
        this.idCosecha = idCosecha;
        this.plan = plan;
        this.pesoKg = pesoKg;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public void registrar() {
        plan.getParcela().iniciarDescanso(plan.getCultivo(), fecha);
        plan.setCosechado(true);
    }

    public ArrayList<CuotaDistribucion> distribuir(ArrayList<Usuario> colaboradores) {
        ArrayList<CuotaDistribucion> cuotas = new ArrayList<>();
        int n = colaboradores.size();
        if (n == 0) {
            return cuotas;
        }
        long totalCentesimas = Math.round(pesoKg * 100);
        long baseCentesimas = totalCentesimas / n;
        long restoCentesimas = totalCentesimas % n;
        int baseUnidades = cantidad / n;
        int restoUnidades = cantidad % n;
        for (int i = 0; i < n; i++) {
            long centesimas = baseCentesimas;
            if (i < restoCentesimas) {
                centesimas++;
            }
            int unidades = baseUnidades;
            if (i < restoUnidades) {
                unidades++;
            }
            cuotas.add(new CuotaDistribucion(centesimas / 100.0, unidades, colaboradores.get(i)));
        }
        return cuotas;
    }

    public String getIdCosecha() {
        return idCosecha;
    }

    public Plansiembra getPlan() {
        return plan;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
