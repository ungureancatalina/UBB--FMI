package ro.mpp.labfx.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CursaDTO {
    private final StringProperty nume;
    private final IntegerProperty capacitate_min;
    private final IntegerProperty capacitate_max;
    private final IntegerProperty nrParticipanti;

    public CursaDTO(String nume, int capacitate_min, int capacitate_max ,int nrParticipanti) {
        this.nume = new SimpleStringProperty(nume);
        this.capacitate_min = new SimpleIntegerProperty(capacitate_min);
        this.capacitate_max = new SimpleIntegerProperty(capacitate_max);
        this.nrParticipanti = new SimpleIntegerProperty(nrParticipanti);
    }

    public StringProperty getNumeProperty() {
        return nume;
    }

    public IntegerProperty getCapacitateMinProperty() {
        return capacitate_min;
    }

    public IntegerProperty getCapacitateMaxProperty() {
        return capacitate_max;
    }

    public IntegerProperty getNrParticipantiProperty() {
        return nrParticipanti;
    }

    public String getNumeDTO() {
        return nume.get();
    }

    public int getCapacitateMinDTO() {
        return capacitate_min.get();
    }

    public int getCapacitateMaxDTO() {
        return capacitate_max.get();
    }

    public int getNrParticipantiDTO() {
        return nrParticipanti.get();
    }
}
