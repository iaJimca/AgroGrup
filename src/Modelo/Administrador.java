package Modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Administrador extends Usuario {
    private String contrasena;
    private int intentosFallidos;
    private LocalDateTime bloqueadoHasta;

    public Administrador(String idUsuario, String nombreCompleto, String cedula, String correo, String contrasena) {
        super(idUsuario, nombreCompleto, cedula, correo, Rol.ADMINISTRADOR);
        this.contrasena = cifrar(contrasena);
        this.intentosFallidos = 0;
        this.bloqueadoHasta = null;
    }

    private String cifrar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return texto;
        }
    }

    public boolean estaBloqueado() {
        return bloqueadoHasta != null && LocalDateTime.now().isBefore(bloqueadoHasta);
    }

    public boolean iniciarSesion(String correo, String clave) {
        if (estaBloqueado()) {
            return false;
        }
        if (getCorreo().equalsIgnoreCase(correo) && contrasena.equals(cifrar(clave))) {
            intentosFallidos = 0;
            return true;
        }
        intentosFallidos++;
        if (intentosFallidos >= 3) {
            bloqueadoHasta = LocalDateTime.now().plusMinutes(5);
            intentosFallidos = 0;
        }
        return false;
    }

    public Usuario crearUsuario(String idUsuario, String nombreCompleto, String cedula, String correo, Rol rol) {
        return new Usuario(idUsuario, nombreCompleto, cedula, correo, rol);
    }

    public MapaHuerto crearMapa(double areaTotal, double areaParcela) {
        return new MapaHuerto(areaTotal, areaParcela);
    }
}
