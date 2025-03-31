package ro.mpp.labfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ro.mpp.labfx.domain.*;
import ro.mpp.labfx.service.*;
import ro.mpp.labfx.validator.InscriereValidator;
import ro.mpp.labfx.validator.ValidationException;

import java.util.List;
import java.util.Optional;

public class InscriereController {

    private CursaService cursaService;
    private ParticipantService participantService;
    private EchipaService echipaService;
    private ParticipantCursaService participantCursaService;
    private InscriereValidator inscriereValidator;

    @FXML
    private TextField numeField;

    @FXML
    private TextField cnpField;

    @FXML
    private ComboBox<Integer> capacitateMotoCombo;

    @FXML
    private TextField echipaField;

    @FXML
    private TableView<ParticipantCursa> tableViewParticipantiCurse;

    @FXML
    private TableColumn<ParticipantCursa, String> columnNumeCursa;

    @FXML
    private TableColumn<ParticipantCursa, String> columnNumeParticipant;

    public void setter(CursaService cursaService, ParticipantService participantService, EchipaService echipaService, ParticipantCursaService participantCursaService, InscriereValidator inscriereValidator) {
        this.cursaService = cursaService;
        this.participantService = participantService;
        this.echipaService = echipaService;
        this.participantCursaService = participantCursaService;
        this.inscriereValidator = inscriereValidator;
        initModel();
    }

    private void initModel() {
        updateParticipantCursaTable();
        for (int i = 600; i <= 1099; i += 50) {
            capacitateMotoCombo.getItems().add(i);
        }
    }

    @FXML
    public void handleInscriere() {
        String nume = numeField.getText().trim();
        String cnp = cnpField.getText().trim();
        Integer capacitateMoto = capacitateMotoCombo.getValue();
        String echipaNume = echipaField.getText().trim();
        Echipa echipa = echipaService.findByNume(echipaNume);
        Participant participant = new Participant(nume, cnp, capacitateMoto, echipa);

        try {
            inscriereValidator.validate(participant);
        } catch (ValidationException e) {
            showAlert("Eroare de validare", e.getMessage());
            return;
        }

        Cursa cursa = null;
        List<Cursa> curse = cursaService.findAll();
        for (Cursa c : curse) {
            if (c.getCapacitateMinima() <= capacitateMoto && capacitateMoto <= c.getCapacitateMaxima()) {
                cursa = c;
                break;
            }
        }

        Echipa ech = echipaService.findByNume(echipaNume);
        if (echipa == null) {
            echipa = new Echipa(echipaNume);
            echipaService.adauga(ech);
        }

        Participant p = new Participant(nume, cnp, capacitateMoto, ech);
        participantService.inscriereParticipant(p);

        participantCursaService.adauga(p,cursa);
        showAlert("Succes", "Participantul a fost inscris");
        updateParticipantCursaTable();
    }

    private void updateParticipantCursaTable() {
        List<ParticipantCursa> participantCursaList = participantCursaService.findAll();
        tableViewParticipantiCurse.setItems(FXCollections.observableArrayList(participantCursaList));
        columnNumeCursa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCursa().getNume()));
        columnNumeParticipant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParticipant().getNume()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
