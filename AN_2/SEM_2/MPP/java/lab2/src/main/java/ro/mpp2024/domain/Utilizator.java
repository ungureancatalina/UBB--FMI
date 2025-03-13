package ro.mpp2024.domain;

public class Utilizator {
    private int idUtilizator;
    private String nume;
    private String parola;

    public Utilizator(int idUtilizator, String nume, String parola) {
        this.idUtilizator = idUtilizator;
        this.nume = nume;
        this.parola = parola;
    }

    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
