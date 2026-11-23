package Modelo;

public class CuotaDistribucion {
    private double pesoKg;
    private int unidades;
    private Usuario usuario;

    public CuotaDistribucion(double pesoKg, int unidades, Usuario usuario) {
        this.pesoKg = pesoKg;
        this.unidades = unidades;
        this.usuario = usuario;
    }

    public String mostrarReporte() {
        return usuario.getNombreCompleto() + " (" + usuario.getIdUsuario() + "): " + String.format("%.2f", pesoKg) + " kg y " + unidades + " unidades";
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public int getUnidades() {
        return unidades;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
