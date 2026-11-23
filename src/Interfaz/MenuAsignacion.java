package Interfaz;

import Negocio.GestorParcela;
import Negocio.GestorUsuario;

public class MenuAsignacion {
    private GestorParcela gestorParcela;
    private GestorUsuario gestorUsuario;

    public MenuAsignacion(GestorParcela gestorParcela, GestorUsuario gestorUsuario) {
        this.gestorParcela = gestorParcela;
        this.gestorUsuario = gestorUsuario;
    }

    public void mostrar() {
        System.out.println();
        System.out.println("--- Asignar parcela ---");
        String idParcela = EntradaConsola.leerTexto("ID de parcela: ");
        String idUsuario = EntradaConsola.leerTexto("ID de usuario: ");
        System.out.println(gestorParcela.asignarParcela(idParcela, idUsuario, gestorUsuario));
    }
}
