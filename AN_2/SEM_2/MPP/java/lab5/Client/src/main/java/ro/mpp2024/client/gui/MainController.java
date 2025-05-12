package ro.mpp2024.client.gui;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import ro.mpp2024.model.*;
import ro.mpp2024.services.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements IObserver {

    private IServices server;
    private Stage stage;

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

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initializeData() {
        loadCurse();
        loadParticipanti();
    }

    @FXML
    public void handleCautare() {
        String echipa = echipaField.getText().trim();
        if (!echipa.isEmpty()) {
            try {
                Echipa ech = server.findEchipaByNume(echipa);
                if (ech != null) {
                    List<Participant> participanti = server.cautaParticipantiDupaEchipa(echipa);
                    List<ParticipantDTO> filteredParticipants = participanti.stream()
                            .map(p -> new ParticipantDTO(p.getNume(), p.getCapacitateMotor(), p.getEchipa().getNume()))
                            .collect(Collectors.toList());

                    columnEchipa.setCellValueFactory(cellData -> cellData.getValue().getEchipaProperty());
                    columnNume.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
                    columnCapacitateMoto.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMotoProperty().asObject());

                    tableViewParticipanti.setItems(FXCollections.observableArrayList(filteredParticipants));
                    tableViewParticipanti.refresh();
                } else {
                    showErrorAlert("Echipa nu a fost gasita", "error-label");
                }
            } catch (Exception e) {
                showErrorAlert("Eroare la căutare: " + e.getMessage(), "error-label");
            }
        }
    }

    public void loadCurse() {
        try {
            List<Cursa> curse = server.findAllCurse();
            List<CursaDTO> curseDTO = curse.stream()
                    .map(c -> {
                        int nrParticipanti = 0;
                        try {
                            nrParticipanti = server.findNrPartByCursa(c);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return new CursaDTO(
                                c.getNume(),
                                c.getCapacitateMinima(),
                                c.getCapacitateMaxima(),
                                nrParticipanti
                        );
                    })
                    .collect(Collectors.toList());

            columnNumeCursa.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
            columnCapacitateMin.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMinProperty().asObject());
            columnCapacitateMax.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMaxProperty().asObject());
            columnNrParticipanti.setCellValueFactory(cellData -> cellData.getValue().getNrParticipantiProperty().asObject());

            tableViewCurse.setItems(FXCollections.observableArrayList(curseDTO));
            tableViewCurse.refresh();
        } catch (Exception e) {
            showErrorAlert("Eroare la încărcarea curselor: " + e.getMessage(), "error-label");
        }
    }

    public void loadParticipanti() {
        try {
            List<Participant> participanti = server.findAllParticipanti();
            List<ParticipantDTO> updatedParticipants = participanti.stream()
                    .map(p -> new ParticipantDTO(p.getNume(), p.getCapacitateMotor(), p.getEchipa().getNume()))
                    .collect(Collectors.toList());
            columnNume.setCellValueFactory(cellData -> cellData.getValue().getNumeProperty());
            columnCapacitateMoto.setCellValueFactory(cellData -> cellData.getValue().getCapacitateMotoProperty().asObject());
            columnEchipa.setCellValueFactory(cellData -> cellData.getValue().getEchipaProperty());

            tableViewParticipanti.setItems(FXCollections.observableArrayList(updatedParticipants));
            tableViewParticipanti.refresh();
        } catch (Exception e) {
            showErrorAlert("Eroare la încărcarea participanților: " + e.getMessage(), "error-label");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        Platform.exit();
    }

    @FXML
    private void handleInscriereParticipanti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inscriere.fxml"));
            Parent root = loader.load();

            InscriereController inscriereController = loader.getController();
            inscriereController.setServer(server);

            Stage newStage = new Stage();
            newStage.setTitle("Înscriere Participant");
            newStage.setScene(new Scene(root));
            inscriereController.setStage(newStage);
            newStage.show();
        } catch (IOException e) {
            showErrorAlert("Eroare la deschiderea ferestrei de înscriere", "error-label");
        }
    }

    private void showErrorAlert(String message, String styleClass) {
        errorMessageLabel.setText(message);
        errorMessageLabel.getStyleClass().setAll(styleClass);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    @Override
    public void adauga(Participant participant, Cursa cursa) throws Exception {
        Platform.runLater(() -> {
            loadCurse();
            loadParticipanti();
        });
    }

}
