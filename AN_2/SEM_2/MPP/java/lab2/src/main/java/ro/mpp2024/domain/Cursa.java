package ro.mpp2024.domain;

public class Cursa {
    private int idCursa;
    private String nume;
    private int capacitateMinima;
    private int capacitateMaxima;

    public Cursa(int idCursa, String nume, int capacitateMinima, int capacitateMaxima) {
        this.idCursa = idCursa;
        this.nume = nume;
        this.capacitateMinima = capacitateMinima;
        this.capacitateMaxima = capacitateMaxima;
    }

    public int getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(int idCursa) {
        this.idCursa = idCursa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCapacitateMinima() {
        return capacitateMinima;
    }

    public void setCapacitateMinima(int capacitateMinima) {
        this.capacitateMinima = capacitateMinima;
    }

    public int getCapacitateMaxima() {
        return capacitateMaxima;
    }

    public void setCapacitateMaxima(int capacitateMaxima) {
        this.capacitateMaxima = capacitateMaxima;
    }
}
