package ro.mpp2024.model;

import java.io.Serializable;
import java.util.Objects;

public class Cursa implements Identifiable<Integer>, Serializable {
    private int idCursa;
    private String nume;
    private int capacitateMinima;
    private int capacitateMaxima;

    public Cursa(String nume, int capacitateMinima, int capacitateMaxima) {
        this.nume = nume;
        this.capacitateMinima = capacitateMinima;
        this.capacitateMaxima = capacitateMaxima;
    }

    public Cursa(int idCursa, String nume, int capacitateMinima, int capacitateMaxima) {
        this.idCursa = idCursa;
        this.nume = nume;
        this.capacitateMinima = capacitateMinima;
        this.capacitateMaxima = capacitateMaxima;
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

    @Override
    public void setId(Integer integer) {
        this.idCursa = integer;
    }

    @Override
    public Integer getId() {
        return idCursa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursa cursa = (Cursa) o;
        return idCursa == cursa.idCursa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCursa);
    }

    @Override
    public String toString() {
        return "Cursa{" +
                "idCursa=" + idCursa +
                ", nume='" + nume + '\'' +
                ", capacitateMinima=" + capacitateMinima +
                ", capacitateMaxima=" + capacitateMaxima +
                '}';
    }
}
