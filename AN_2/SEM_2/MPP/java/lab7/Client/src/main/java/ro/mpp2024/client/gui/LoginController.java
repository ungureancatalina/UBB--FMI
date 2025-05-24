package ro.mpp2024.client.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.mpp2024.model.Utilizator;
import ro.mpp2024.services.IServices;

import java.io.IOException;

public class LoginController {
    private FadeTransition fadeOut;
    private IServices server;
    private Stage stage;
    private Parent mainParent;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setParent(Parent parent) {
        this.mainParent = parent;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            Utilizator ut= new Utilizator(username, password);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainv.fxml"));
            Parent root = fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();
            mainController.setServer(server);
            mainController.setStage(stage);

            server.login(ut, mainController);

            mainController.initializeData();

            Stage newStage = new Stage();
            newStage.setTitle("Gestiune Concurs Moto");
            newStage.setScene(new Scene(root));
            newStage.show();

            if (stage != null) {
                stage.close();
            }
        } catch (Exception e) {
            showAlert("Eroare", e.getMessage());
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