package ro.mpp.labfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.labfx.domain.Utilizator;
import ro.mpp.labfx.service.*;
import ro.mpp.labfx.validator.InscriereValidator;
import ro.mpp.labfx.validator.LoginValidare;
import ro.mpp.labfx.validator.MainValidator;
import ro.mpp.labfx.validator.ValidationException;

import java.io.IOException;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    private UtilizatorService utilizatorService;
    private CursaService cursaService;
    private ParticipantService participantService;
    private EchipaService echipaService;
    private ParticipantCursaService participantCursaService;
    private InscriereValidator inscriereValidator;
    private LoginValidare loginValidare;
    private MainValidator mainValidator;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setter(UtilizatorService utilizatorService, CursaService cursaService, ParticipantService participantService, EchipaService echipaService, ParticipantCursaService participantCursaService,InscriereValidator inscriereValidator, LoginValidare loginValidare, MainValidator mainValidator) {
        this.utilizatorService = utilizatorService;
        this.cursaService = cursaService;
        this.participantService = participantService;
        this.echipaService = echipaService;
        this.participantCursaService = participantCursaService;
        this.inscriereValidator = inscriereValidator;
        this.loginValidare = loginValidare;
        this.mainValidator = mainValidator;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Utilizator ut= new Utilizator(username,password);
        try {
            loginValidare.validate(ut);
        } catch (ValidationException e) {
            showAlert("Eroare de validare", e.getMessage());
            return;
        }

        Utilizator utilizator = utilizatorService.autentificare(username, password);

        if (utilizator != null) {
            openMainWindow();
        } else {
            showAlert("Error","Autentificare esuata");
        }
    }

    private void openMainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainv.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MainController mainController = fxmlLoader.getController();
            mainController.setter(cursaService, participantService, echipaService,participantCursaService,inscriereValidator, mainValidator);

            Stage stage = new Stage();
            stage.setTitle("Gestiune Concurs Moto");
            stage.setScene(scene);
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
