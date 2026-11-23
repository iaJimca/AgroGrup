package Interfaz;

import Negocio.GestorCosecha;
import Negocio.GestorCultivo;
import Negocio.GestorParcela;
import Negocio.GestorUsuario;

public class Main {
    public static void main(String[] args) {
        GestorUsuario gestorUsuario = new GestorUsuario();
        GestorParcela gestorParcela = new GestorParcela();
        GestorCultivo gestorCultivo = new GestorCultivo();
        GestorCosecha gestorCosecha = new GestorCosecha();

        System.out.println("===== AgroGrup =====");
        if (!gestorUsuario.existeAdministrador()) {
            MenuRegistroAdm registro = new MenuRegistroAdm(gestorUsuario);
            registro.mostrar();
        }
        MenuAcceso acceso = new MenuAcceso(gestorUsuario, gestorParcela, gestorCultivo, gestorCosecha);
        acceso.mostrar();
        System.out.println("Hasta pronto");
    }
}
