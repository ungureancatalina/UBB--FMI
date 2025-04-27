package ro.mpp2024.model;

import java.io.Serializable;
import java.util.Objects;

public class Echipa implements Identifiable<Integer>, Serializable {
    private int idEchipa;
    private String nume;

    public Echipa(String nume) {
        this.nume = nume;
    }

    public Echipa(int idEchipa,String nume) {
        this.idEchipa = idEchipa;
        this.nume = nume;
    }

    @Override
    public void setId(Integer integer) {
        this.idEchipa = integer;
    }

    @Override
    public Integer getId() {
        return idEchipa;
    }

    public void setIdEchipa(int idEchipa) {
        this.idEchipa = idEchipa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Echipa echipa = (Echipa) o;
        return idEchipa == echipa.idEchipa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEchipa);
    }

    @Override
    public String toString() {
        return "Echipa{" +
                "idEchipa=" + idEchipa +
                ", nume='" + nume + '\'' +
                '}';
    }

}
