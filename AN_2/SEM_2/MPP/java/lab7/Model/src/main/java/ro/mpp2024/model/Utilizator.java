package ro.mpp2024.model;

import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "utilizator")
public class Utilizator implements Identifiable<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtilizator")
    private int idUtilizator;

    @Column(name = "nume", nullable = false, unique = true)
    private String nume;

    @Column(name = "parola", nullable = false)
    private String parola;

    public Utilizator() {}

    public Utilizator(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }

    public Utilizator(int idUtilizator, String nume, String parola) {
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

    public void setParolaCriptata(String parola) {
        this.parola = BCrypt.hashpw(parola, BCrypt.gensalt());
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public boolean verificaParola(String parola) {
        return BCrypt.checkpw(parola, this.parola);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return idUtilizator == that.idUtilizator;
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
