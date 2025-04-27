package ro.mpp2024.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParticipantDTO {
    private final StringProperty nume;
    private final IntegerProperty capacitateMoto;
    private final StringProperty echipa;

    public ParticipantDTO(String nume, int capacitateMoto, String echipa) {
        this.nume = new SimpleStringProperty(nume);
        this.capacitateMoto = new SimpleIntegerProperty(capacitateMoto);
        this.echipa = new SimpleStringProperty(echipa);
    }

    public StringProperty getNumeProperty() {
        return nume;
    }

    public IntegerProperty getCapacitateMotoProperty() {
        return capacitateMoto;
    }

    public StringProperty getEchipaProperty() {
        return echipa;
    }

    public String getNumeDTO() {
        return nume.get();
    }

    public int getCapacitateMotoDTO() {
        return capacitateMoto.get();
    }

    public String getEchipaDTO() {
        return echipa.get();
    }
}
