package ro.mpp2024.model;

import java.io.Serializable;

public class CursaDTOData implements Serializable {
    private String nume;
    private int capacitateMin;
    private int capacitateMax;
    private int nrParticipanti;

    public String getNume() {
        return nume;
    }

    public int getCapacitateMinima() {
        return capacitateMin;
    }

    public int getCapacitateMaxima() {
        return capacitateMax;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }
}
