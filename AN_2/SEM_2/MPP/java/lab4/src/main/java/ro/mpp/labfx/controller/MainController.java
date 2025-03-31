package ro.mpp.labfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.mpp.labfx.domain.Cursa;
import ro.mpp.labfx.domain.CursaDTO;
import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.domain.ParticipantDTO;
import ro.mpp.labfx.service.CursaService;
import ro.mpp.labfx.service.EchipaService;
import ro.mpp.labfx.service.ParticipantCursaService;
import ro.mpp.labfx.service.ParticipantService;
import ro.mpp.labfx.validator.InscriereValidator;
import ro.mpp.labfx.validator.MainValidator;
import ro.mpp.labfx.validator.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    private CursaService cursaService;
    private ParticipantService participantService;
    private EchipaService echipaService;
    private ParticipantCursaService participantCursaService;
    private InscriereValidator InscriereValidator;
    private MainValidator mainValidator;

    @FXML
    private TableView<CursaDTO> tableViewCurse;

    @FXML
    private TableColumn<CursaDTO, String> columnNumeCursa;

    @FXML
    private TableColumn<CursaDTO, Integer> columnCapacitateMin;

    @FXML
    private TableColumn<CursaDTO, Integer> columnCapacitateMax;

    @FXML
    private TableColumn<CursaDTO, Integer> columnNrParticipanti;

    @FXML
    private TextField echipaField;

    @FXML
    private TableView<ParticipantDTO> tableViewParticipanti;

    @FXML
    private TableColumn<ParticipantDTO, String> columnNume;

    @FXML
    private TableColumn<ParticipantDTO, Integer> columnCapacitateMoto;

    @FXML
    private TableColumn<ParticipantDTO, String> columnEchipa;

    public void setter(CursaService cursaService, ParticipantService participantService, EchipaService echipaService, ParticipantCursaService participantCursaService, InscriereValidator InscriereValidator, MainValidator mainValidator) {
        this.cursaService = cursaService;
        this.participantService = participantService;
        this.echipaService = echipaService;
        this.participantCursaService = participantCursaService;
        this.InscriereValidator = InscriereValidator;
        this.mainValidator = mainValidator;
        initialize();
    }

    private void initialize() {
        loadCurse();
        loadParticipanti();
    }

    @FXML
    public void handleCautare() {
        String echipa = echipaField.getText().trim();
        if (!echipa.isEmpty()) {
            Echipa ech = echipaService.findByNume(echipa);
            if (ech != null) {
                List<ParticipantDTO> filteredParticipants = participantService.getParticipantiByEchipa(ech);
                columnEchipa.setCellValueFactory(cellData -> cellData.getValue().getEchipaProperty());
                columnNume.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
                columnCapacitateMoto.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMotoProperty().asObject());
                tableViewParticipanti.setItems(FXCollections.observableArrayList(filteredParticipants));
                tableViewParticipanti.refresh();
            }
            else {
                showAlert("Error","Echipa nu a fost gasita");
            }
        }
    }

    public void loadCurse() {
        List<Cursa> curse = cursaService.findAll();
        List<CursaDTO> curseDTO = cursaService.listDTOcurse(curse, participantCursaService);

        columnNumeCursa.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
        columnCapacitateMin.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMinProperty().asObject());
        columnCapacitateMax.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMaxProperty().asObject());
        columnNrParticipanti.setCellValueFactory(cellData -> cellData.getValue().getNrParticipantiProperty().asObject());
        tableViewCurse.setItems(FXCollections.observableArrayList(curseDTO));
        tableViewCurse.refresh();
    }

    public void loadParticipanti() {
        List<ParticipantDTO> updatedParticipants = participantService.findAll().stream()
                .map(participant -> new ParticipantDTO(participant.getNume(),participant.getCapacitateMotor(),participant.getEchipa().getNume()))
                .collect(Collectors.toList());
        columnNume.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
        columnCapacitateMoto.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMotoProperty().asObject());
        columnEchipa.setCellValueFactory(cellData -> cellData.getValue().getEchipaProperty());
        tableViewParticipanti.setItems(FXCollections.observableArrayList(updatedParticipants));
        tableViewParticipanti.refresh();
    }

    @FXML
    private void handleLogout() {
        System.exit(0);
    }

    @FXML
    private void handleInscriereParticipanti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inscriere.fxml"));
            Parent root = loader.load();
            InscriereController inscriereController = loader.getController();
            inscriereController.setter(cursaService, participantService, echipaService, participantCursaService, InscriereValidator);

            Stage stage = new Stage();
            stage.setTitle("Înscriere Participant");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
