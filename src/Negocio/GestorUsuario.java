package Negocio;

import Modelo.Administrador;
import Modelo.Rol;
import Modelo.Usuario;
import java.util.ArrayList;

public class GestorUsuario {
    private Administrador administrador;
    private ArrayList<Usuario> usuarios;
    private String mensaje;

    public GestorUsuario() {
        administrador = null;
        usuarios = new ArrayList<>();
        mensaje = "";
    }

    private boolean vacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public boolean existeAdministrador() {
        return administrador != null;
    }

    public String registrarAdministrador(String nombre, String cedula, String correo, String contrasena) {
        if (vacio(nombre) || vacio(cedula) || vacio(correo) || vacio(contrasena)) {
            return "Error: Campos obligatorios incompletos";
        }
        Administrador nuevo = new Administrador("ADM-001", nombre, cedula, correo, contrasena);
        if (!nuevo.validarCedula()) {
            return "Error: Cédula inválida";
        }
        if (contrasena.length() < 8) {
            return "Error: La contraseña debe contener mínimo 8 caracteres";
        }
        administrador = nuevo;
        return "Cuenta creada con éxito. Listo para designar y sembrar";
    }

    public Administrador iniciarSesionAdministrador(String correo, String clave) {
        if (administrador == null || !administrador.getCorreo().equalsIgnoreCase(correo)) {
            mensaje = "Usuario o contraseña incorrectos";
            return null;
        }
        if (administrador.estaBloqueado()) {
            mensaje = "Cuenta bloqueada temporalmente por múltiples intentos fallidos";
            return null;
        }
        if (administrador.iniciarSesion(correo, clave)) {
            return administrador;
        }
        if (administrador.estaBloqueado()) {
            mensaje = "Cuenta bloqueada temporalmente por múltiples intentos fallidos";
        } else {
            mensaje = "Usuario o contraseña incorrectos";
        }
        return null;
    }

    public Usuario iniciarSesionPorId(String id) {
        Usuario usuario = buscarPorId(id);
        if (usuario == null) {
            mensaje = "ID inexistente";
        }
        return usuario;
    }

    public String crearUsuario(String nombre, String cedula, String correo, Rol rol) {
        if (vacio(nombre) || vacio(cedula) || vacio(correo) || rol == null) {
            return "Error: Campos obligatorios incompletos";
        }
        Usuario prueba = new Usuario("", nombre, cedula, correo, rol);
        if (!prueba.validarCedula()) {
            return "Error: Cédula inválida";
        }
        if (correoExiste(correo)) {
            return "Error: Correo ya registrado";
        }
        String id = String.format("USR-%03d", usuarios.size() + 1);
        Usuario nuevo = administrador.crearUsuario(id, nombre, cedula, correo, rol);
        usuarios.add(nuevo);
        return "Usuario creado con éxito. Listo para sembrar. ID: " + id;
    }

    private boolean correoExiste(String correo) {
        if (administrador != null && administrador.getCorreo().equalsIgnoreCase(correo)) {
            return true;
        }
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }

    public Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    public String getMensaje() {
        return mensaje;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Administrador getAdministrador() {
        return administrador;
    }
}
