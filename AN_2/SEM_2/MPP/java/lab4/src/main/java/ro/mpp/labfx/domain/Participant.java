package ro.mpp.labfx.domain;

import java.util.Objects;

public class Participant implements Identifiable<Integer>{
    private int idParticipant;
    private String nume;
    private String CNP;
    private int capacitateMotor;
    private Echipa echipa;

    public Participant(String nume, String CNP, int capacitateMotor, Echipa echipa) {
        this.nume = nume;
        this.CNP = CNP;
        this.capacitateMotor = capacitateMotor;
        this.echipa = echipa;
    }

    public Participant(int idParticipant,String nume, String CNP, int capacitateMotor, Echipa echipa) {
        this.idParticipant = idParticipant;
        this.nume = nume;
        this.CNP = CNP;
        this.capacitateMotor = capacitateMotor;
        this.echipa = echipa;
    }

    @Override
    public void setId(Integer integer) {
        this.idParticipant = integer;
    }

    @Override
    public Integer getId() {
        return idParticipant;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getCapacitateMotor() {
        return capacitateMotor;
    }

    public void setCapacitateMotor(int capacitateMotor) {
        this.capacitateMotor = capacitateMotor;
    }

    public Echipa getEchipa() {
        return echipa;
    }

    public void setEchipa(Echipa echipa) {
        this.echipa = echipa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant participant = (Participant) o;
        return idParticipant == participant.idParticipant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipant);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "idParticipant=" + idParticipant +
                ", nume='" + nume + '\'' +
                ", CNP='" + CNP + '\'' +
                ", capacitateMotor=" + capacitateMotor +
                ", echipa=" + echipa +
                '}';
    }

}
