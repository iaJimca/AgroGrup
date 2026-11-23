package Modelo;

import java.time.LocalDate;

public class Parcela {
    private String idParcela;
    private double areaM2;
    private EstadoParcela estado;
    private LocalDate finDescanso;
    private Usuario usuario;
    private Cultivo ultimoCultivo;

    public Parcela(String idParcela, double areaM2) {
        this.idParcela = idParcela;
        this.areaM2 = areaM2;
        this.estado = EstadoParcela.LIBRE;
        this.finDescanso = null;
        this.usuario = null;
        this.ultimoCultivo = null;
    }

    public void asignar(Usuario usuario) {
        this.usuario = usuario;
        this.estado = EstadoParcela.ASIGNADA;
    }

    public void liberar() {
        this.usuario = null;
        this.estado = EstadoParcela.LIBRE;
    }

    public boolean estaDisponible() {
        return estado == EstadoParcela.LIBRE;
    }

    public void iniciarDescanso(Cultivo cultivo, LocalDate fechaCosecha) {
        this.ultimoCultivo = cultivo;
        this.finDescanso = fechaCosecha.plusDays(cultivo.diasDescanso());
        this.estado = EstadoParcela.EN_DESCANSO;
    }

    public void actualizarEstado(LocalDate hoy) {
        if (estado == EstadoParcela.EN_DESCANSO && !hoy.isBefore(finDescanso)) {
            liberar();
        }
    }

    public String getIdParcela() {
        return idParcela;
    }

    public double getAreaM2() {
        return areaM2;
    }

    public EstadoParcela getEstado() {
        return estado;
    }

    public LocalDate getFinDescanso() {
        return finDescanso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Cultivo getUltimoCultivo() {
        return ultimoCultivo;
    }
}
