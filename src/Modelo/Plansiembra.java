package Modelo;

import java.time.LocalDate;

public class Plansiembra {
    private String idPlan;
    private Parcela parcela;
    private Cultivo cultivo;
    private LocalDate fechaInicio;
    private int cantidadSemillas;
    private LocalDate fechaCosecha;
    private boolean cosechado;

    public Plansiembra(String idPlan, Parcela parcela, Cultivo cultivo, LocalDate fechaInicio, int cantidadSemillas) {
        this.idPlan = idPlan;
        this.parcela = parcela;
        this.cultivo = cultivo;
        this.fechaInicio = fechaInicio;
        this.cantidadSemillas = cantidadSemillas;
        this.cosechado = false;
        calcularCosecha();
    }

    public LocalDate calcularCosecha() {
        fechaCosecha = fechaInicio.plusDays(cultivo.getDiasACosecha());
        return fechaCosecha;
    }

    public boolean rotacionValida() {
        Cultivo anterior = parcela.getUltimoCultivo();
        if (anterior == null) {
            return true;
        }
        return !cultivo.mismaFamilia(anterior);
    }

    public String getIdPlan() {
        return idPlan;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getCantidadSemillas() {
        return cantidadSemillas;
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    public boolean isCosechado() {
        return cosechado;
    }

    public void setCosechado(boolean cosechado) {
        this.cosechado = cosechado;
    }
}
