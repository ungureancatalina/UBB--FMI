package ro.mpp2024.model;

import java.io.Serializable;

public class ParticipantDTOData implements Serializable {
    private String nume;
    private int capacitateMotor;
    private Echipa  echipa;

    public String getNume() {
        return nume;
    }

    public int getCapacitateMoto() {
        return capacitateMotor;
    }

    public Echipa getEchipa() {
        return echipa;
    }
}