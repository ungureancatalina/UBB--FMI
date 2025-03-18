package ro.mpp.labfx.domain;

import java.util.Objects;

public class Utilizator implements Identifiable<Integer>{
    private int idUtilizator;
    private String nume;
    private String parola;

    public Utilizator(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }

    public Utilizator(int idUtilizator,String nume, String parola) {
        this.idUtilizator = idUtilizator;
        this.nume = nume;
        this.parola = parola;
    }

    @Override
    public void setId(Integer integer) {
        this.idUtilizator = integer;
    }

    @Override
    public Integer getId() {
        return idUtilizator;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator utilizator = (Utilizator) o;
        return idUtilizator == utilizator.idUtilizator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilizator);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "idUtilizator=" + idUtilizator +
                ", nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
