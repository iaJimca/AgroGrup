package Interfaz;

import Modelo.Administrador;
import Modelo.EstadoParcela;
import Modelo.MapaHuerto;
import Modelo.Parcela;
import Modelo.Usuario;
import Negocio.GestorParcela;

public class MenuMapa {
    private Usuario usuario;
    private GestorParcela gestorParcela;

    public MenuMapa(Usuario usuario, GestorParcela gestorParcela) {
        this.usuario = usuario;
        this.gestorParcela = gestorParcela;
    }

    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            gestorParcela.actualizarEstados();
            System.out.println();
            System.out.println("--- Mapa del huerto ---");
            System.out.println("1. Crear mapa");
            System.out.println("2. Ver mapa");
            System.out.println("3. Listado de parcelas");
            System.out.println("0. Volver");
            int opcion = EntradaConsola.leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    crearMapa();
                    break;
                case 2:
                    mostrarMapa();
                    break;
                case 3:
                    listarParcelas();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void crearMapa() {
        if (!usuario.tienePermiso("CREAR_MAPA")) {
            System.out.println("Error: No tiene permisos para esta operación");
            return;
        }
        double areaTotal = EntradaConsola.leerDecimal("Área total del terreno (m2): ");
        double areaParcela = EntradaConsola.leerDecimal("Área de cada parcela (m2): ");
        String mensaje = gestorParcela.crearMapa((Administrador) usuario, areaTotal, areaParcela);
        System.out.println(mensaje);
        MapaHuerto mapa = gestorParcela.getMapa();
        if (mensaje.startsWith("Mapa creado")) {
            System.out.println("Parcelas creadas: " + mapa.numParcelas());
            System.out.println("Área sobrante: " + mapa.areaSobrante() + " m2");
        }
    }

    public void mostrarMapa() {
        gestorParcela.actualizarEstados();
        MapaHuerto mapa = gestorParcela.getMapa();
        if (mapa == null) {
            System.out.println("Todavía no se ha creado el mapa del huerto");
            return;
        }
        System.out.println("Mapa " + mapa.getIdMapa() + " (" + mapa.numParcelas() + " parcelas)");
        for (int fila = 0; fila < mapa.getFilas(); fila++) {
            String linea = "";
            for (int columna = 0; columna < mapa.getColumnas(); columna++) {
                int posicion = fila * mapa.getColumnas() + columna;
                if (posicion < mapa.getParcelas().size()) {
                    Parcela p = mapa.getParcelas().get(posicion);
                    linea = linea + "[" + p.getIdParcela() + " " + letraEstado(p) + "] ";
                }
            }
            System.out.println(linea);
        }
        System.out.println("L = Libre, A = Asignada, D = En descanso");
    }

    private String letraEstado(Parcela p) {
        if (p.getEstado() == EstadoParcela.LIBRE) {
            return "L";
        } else if (p.getEstado() == EstadoParcela.ASIGNADA) {
            return "A";
        }
        return "D";
    }

    private void listarParcelas() {
        MapaHuerto mapa = gestorParcela.getMapa();
        if (mapa == null) {
            System.out.println("Todavía no se ha creado el mapa del huerto");
            return;
        }
        System.out.println("1. Todas");
        System.out.println("2. Por estado");
        System.out.println("3. Por usuario");
        int filtro = EntradaConsola.leerEntero("Filtro: ");
        EstadoParcela estado = null;
        String idUsuario = "";
        if (filtro == 2) {
            System.out.println("1. Libre");
            System.out.println("2. Asignada");
            System.out.println("3. En descanso");
            int opcionEstado = EntradaConsola.leerEntero("Estado: ");
            if (opcionEstado == 1) {
                estado = EstadoParcela.LIBRE;
            } else if (opcionEstado == 2) {
                estado = EstadoParcela.ASIGNADA;
            } else if (opcionEstado == 3) {
                estado = EstadoParcela.EN_DESCANSO;
            } else {
                System.out.println("Estado inválido");
                return;
            }
        } else if (filtro == 3) {
            idUsuario = EntradaConsola.leerTexto("ID de usuario: ");
        }
        for (Parcela p : mapa.getParcelas()) {
            boolean mostrar = true;
            if (estado != null && p.getEstado() != estado) {
                mostrar = false;
            }
            if (!idUsuario.isEmpty()) {
                if (p.getUsuario() == null || !p.getUsuario().getIdUsuario().equalsIgnoreCase(idUsuario)) {
                    mostrar = false;
                }
            }
            if (mostrar) {
                String dueno = "-";
                if (p.getUsuario() != null) {
                    dueno = p.getUsuario().getNombreCompleto();
                }
                System.out.println(p.getIdParcela() + " - " + p.getAreaM2() + " m2 - " + p.getEstado() + " - " + dueno);
            }
        }
    }
}
