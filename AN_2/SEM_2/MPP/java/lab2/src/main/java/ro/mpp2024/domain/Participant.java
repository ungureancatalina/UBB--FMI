package ro.mpp2024.domain;

public class Participant {
    private int idParticipant;
    private String nume;
    private String CNP;
    private int capacitateMotor;
    private int idEchipa;

    public Participant(int idParticipant, String nume, String CNP, int capacitateMotor, int idEchipa) {
        this.idParticipant = idParticipant;
        this.nume = nume;
        this.CNP = CNP;
        this.capacitateMotor = capacitateMotor;
        this.idEchipa = idEchipa;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
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

    public int getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(int idEchipa) {
        this.idEchipa = idEchipa;
    }
}
