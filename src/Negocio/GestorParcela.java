package Negocio;

import Modelo.Administrador;
import Modelo.EstadoParcela;
import Modelo.MapaHuerto;
import Modelo.Parcela;
import Modelo.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestorParcela {
    private MapaHuerto mapa;

    public GestorParcela() {
        mapa = null;
    }

    public String crearMapa(Administrador administrador, double areaTotal, double areaParcela) {
        if (mapa != null) {
            return "Error: Ya existe un mapa del huerto";
        }
        if (areaParcela < 9 || areaParcela > 12) {
            return "Error: Tamaño de parcela inválido, debe estar entre 9 y 12 metros cuadrados";
        }
        if (areaTotal < areaParcela) {
            return "Error: Mapa inválido, el área del terreno no puede ser menor que el tamaño de una parcela";
        }
        mapa = administrador.crearMapa(areaTotal, areaParcela);
        return "Mapa creado con éxito. Listo para designar y sembrar";
    }

    public void actualizarEstados() {
        if (mapa == null) {
            return;
        }
        for (Parcela p : mapa.getParcelas()) {
            p.actualizarEstado(LocalDate.now());
        }
    }

    public Parcela buscarParcela(String id) {
        if (mapa == null) {
            return null;
        }
        for (Parcela p : mapa.getParcelas()) {
            if (p.getIdParcela().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public String asignarParcela(String idParcela, String idUsuario, GestorUsuario gestorUsuario) {
        if (idParcela.isEmpty() || idUsuario.isEmpty()) {
            return "Error: Campos obligatorios incompletos";
        }
        actualizarEstados();
        Parcela parcela = buscarParcela(idParcela);
        if (parcela == null) {
            return "Error: Parcela inexistente, ID de parcela inválido";
        }
        Usuario usuario = gestorUsuario.buscarPorId(idUsuario);
        if (usuario == null) {
            return "Error: Usuario inexistente, ID de usuario inválido";
        }
        if (parcela.getEstado() == EstadoParcela.EN_DESCANSO) {
            return "Error: Asignación imposible, la tierra no ha descansado lo suficiente";
        }
        if (!parcela.estaDisponible()) {
            return "Error: Parcela no disponible";
        }
        parcela.asignar(usuario);
        return "Parcela asignada con éxito. Lista para sembrar";
    }

    public ArrayList<Usuario> usuariosConParcela(ArrayList<Usuario> todos) {
        ArrayList<Usuario> resultado = new ArrayList<>();
        if (mapa == null) {
            return resultado;
        }
        for (Usuario u : todos) {
            for (Parcela p : mapa.getParcelas()) {
                if (p.getUsuario() == u) {
                    resultado.add(u);
                    break;
                }
            }
        }
        return resultado;
    }

    public ArrayList<Parcela> parcelasDe(Usuario usuario) {
        ArrayList<Parcela> resultado = new ArrayList<>();
        if (mapa == null) {
            return resultado;
        }
        for (Parcela p : mapa.getParcelas()) {
            if (p.getUsuario() == usuario) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public MapaHuerto getMapa() {
        return mapa;
    }
}
