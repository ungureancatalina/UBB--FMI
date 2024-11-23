package com.example.lab6fx.controller;

import com.example.lab6fx.domain.LoggedIn;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.service.CerereService;
import com.example.lab6fx.service.PrietenieService;
import com.example.lab6fx.service.UtilizatorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

        private UtilizatorService utilizatorService;
        private PrietenieService prietenieService;
        private CerereService cerereService;
        private Stage primaryStage;

        @FXML
        private TextField cauta_nume;

        @FXML
        private TextField cauta_prenume;

        @FXML
        private PasswordField cauta_parola;

        @FXML
        private Label erori;

         private Runnable utilizator_autentificat;


    public void setService(UtilizatorService utilizatorService, Stage stage) {
            this.utilizatorService = utilizatorService;
            this.primaryStage = stage;
        }

    public void setutilizator_autentificat(Runnable utilizator_autentificat) {
        this.utilizator_autentificat = utilizator_autentificat;
    }

        @FXML
        private void handleLogin() {
            String lastName = cauta_nume.getText().trim();
            String firstName = cauta_prenume.getText().trim();
            String password = cauta_parola.getText().trim();
            if (lastName.isEmpty() || firstName.isEmpty() || password.isEmpty()) {
                erori.setText("Toate câmpurile trebuie completate");
                return;
            }
            try {
                Utilizator utilizator = utilizatorService.findByFullName(firstName, lastName);
                if (utilizator != null && utilizator.getPassword().equals(password)) {
                    LoggedIn.setFirstName(firstName);
                    LoggedIn.setLastName(lastName);
                    if (utilizator_autentificat != null) {
                        utilizator_autentificat.run();
                    }
                } else {
                    erori.setText("Nume, prenume sau parola gresite");
                }
            } catch (Exception e) {
                e.printStackTrace();
                erori.setText("Eroare la autentificare");
            }
        }
}
