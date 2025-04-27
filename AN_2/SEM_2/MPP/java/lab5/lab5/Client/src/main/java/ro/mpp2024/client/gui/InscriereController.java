package ro.mpp2024.client.gui;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.model.*;
import ro.mpp2024.services.IObserver;
import ro.mpp2024.services.IServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class InscriereController implements Initializable, IObserver {

    private IServices server;
    private Stage stage;

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

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;


    private boolean initialized = false;
    public void setServer(IServices server) {
        this.server = server;
        if (initialized) {
            initModel();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

        try {
            Echipa echipa = server.findEchipaByNume(echipaNume);
            if (echipa == null) {
                echipa = new Echipa(echipaNume);
                server.adaugaEchipa(echipa);
            }

            Participant participant = new Participant(nume, cnp, capacitateMoto, echipa);
            server.inscriereParticipant(participant);

            List<Cursa> curse = server.findAllCurse();
            for (Cursa cursa : curse) {
                if (cursa.getCapacitateMinima() <= capacitateMoto && capacitateMoto <= cursa.getCapacitateMaxima()) {
                    server.adaugaParticipantLaCursa(participant, cursa);
                    break;
                }
            }

            showAlert("Succes", "Participantul a fost inscris");
            updateParticipantCursaTable();
        } catch (Exception e) {
            showAlert("Eroare la înscriere: " + e.getMessage(), "error-label");
        }
    }

    private void updateParticipantCursaTable() {
        try {
            List<ParticipantCursa> participantCursaList = server.findAllParticipantCursa();
            tableViewParticipantiCurse.setItems(FXCollections.observableArrayList(participantCursaList));
            columnNumeCursa.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCursa().getNume())));
            columnNumeParticipant.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getParticipant().getNume())));
        } catch (Exception e) {
            showAlert("Eroare la încărcarea participanților la cursă", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialized = true;

        if (server != null) {
            initModel();
        }

        tableViewParticipantiCurse.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableViewParticipantiCurse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    Participant participant = newValue.getParticipant();
                    Cursa cursa = newValue.getCursa();

                    numeField.setText(participant.getNume());
                    cnpField.setText(participant.getCNP());
                    capacitateMotoCombo.setValue(participant.getCapacitateMotor());
                    echipaField.setText(participant.getEchipa().getNume());

                } catch (Exception e) {
                    showAlert("Eroare", "Nu s-au putut încărca detaliile: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void adauga(Participant participant, Cursa cursa) throws Exception {
        updateParticipantCursaTable();
        showAlert("Notificare", "A fost adăugat un participant nou: " + participant.getNume() + " la cursa " + cursa.getNume());
    }
}