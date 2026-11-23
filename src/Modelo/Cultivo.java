package Modelo;

public class Cultivo {
    private String nombre;
    private String familiaBotanica;
    private int diasACosecha;
    private NivelDesgaste desgaste;
    private int descansoDias;

    public Cultivo(String nombre, String familiaBotanica, int diasACosecha, NivelDesgaste desgaste) {
        this.nombre = nombre;
        this.familiaBotanica = familiaBotanica;
        this.diasACosecha = diasACosecha;
        this.desgaste = desgaste;
        if (desgaste == NivelDesgaste.ALTO) {
            this.descansoDias = 60;
        } else if (desgaste == NivelDesgaste.MEDIO) {
            this.descansoDias = 30;
        } else {
            this.descansoDias = 15;
        }
    }

    public int diasDescanso() {
        return descansoDias;
    }

    public boolean mismaFamilia(Cultivo otro) {
        return familiaBotanica.equalsIgnoreCase(otro.getFamiliaBotanica());
    }

    public String getNombre() {
        return nombre;
    }

    public String getFamiliaBotanica() {
        return familiaBotanica;
    }

    public int getDiasACosecha() {
        return diasACosecha;
    }

    public NivelDesgaste getDesgaste() {
        return desgaste;
    }
}
