package Modelo;

import java.util.ArrayList;

public class MapaHuerto {
    private String idMapa;
    private double areaTotal;
    private double areaParcela;
    private ArrayList<Parcela> parcelas;
    private int columnas;
    private int filas;

    public MapaHuerto(double areaTotal, double areaParcela) {
        this.idMapa = "MAPA-" + (int) (Math.random() * 9000 + 1000);
        this.areaTotal = areaTotal;
        this.areaParcela = areaParcela;
        this.parcelas = new ArrayList<>();
        generarParcelas();
    }

    public int numParcelas() {
        return (int) (areaTotal / areaParcela);
    }

    public double areaSobrante() {
        return areaTotal - numParcelas() * areaParcela;
    }

    public void generarParcelas() {
        int n = numParcelas();
        columnas = (int) Math.ceil(Math.sqrt(n));
        filas = (int) Math.ceil((double) n / columnas);
        for (int i = 1; i <= n; i++) {
            parcelas.add(new Parcela(String.format("P-%03d", i), areaParcela));
        }
    }

    public String getIdMapa() {
        return idMapa;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public double getAreaParcela() {
        return areaParcela;
    }

    public ArrayList<Parcela> getParcelas() {
        return parcelas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getFilas() {
        return filas;
    }
}
