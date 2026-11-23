package Modelo;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String cedula;
    private String correo;
    private Rol rol;

    public Usuario(String idUsuario, String nombreCompleto, String cedula, String correo, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.correo = correo;
        this.rol = rol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean validarCedula() {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(cedula.charAt(i))) {
                return false;
            }
        }
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (!((provincia >= 1 && provincia <= 24) || provincia == 30)) {
            return false;
        }
        int tercero = cedula.charAt(2) - '0';
        if (tercero >= 6) {
            return false;
        }
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cedula.charAt(i) - '0';
            int coeficiente;
            if (i % 2 == 0) {
                coeficiente = 2;
            } else {
                coeficiente = 1;
            }
            int producto = digito * coeficiente;
            if (producto > 9) {
                producto = producto - 9;
            }
            suma = suma + producto;
        }
        int verificador = (10 - (suma % 10)) % 10;
        return verificador == cedula.charAt(9) - '0';
    }

    public boolean tienePermiso(String operacion) {
        if (rol == Rol.ADMINISTRADOR) {
            return true;
        }
        if (operacion.equals("CONSULTAR")) {
            return true;
        }
        if (rol == Rol.COADMINISTRADOR) {
            return operacion.equals("ASIGNAR_PARCELA") || operacion.equals("REGISTRAR_PLAN") || operacion.equals("REGISTRAR_COSECHA");
        }
        return false;
    }
}
