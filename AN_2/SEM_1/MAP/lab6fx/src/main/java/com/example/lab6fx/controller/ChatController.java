package com.example.lab6fx.controller;

import com.example.lab6fx.Observer;
import com.example.lab6fx.domain.LoggedIn;
import com.example.lab6fx.domain.Message;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.domain.validator.UtilizatorValidator;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.repository.database.MessageRepositoryDB;
import com.example.lab6fx.repository.database.UtilizatorRepositoryDB;
import com.example.lab6fx.service.UtilizatorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ChatController implements Observer<UtilizatorEvent> {

    @FXML
    private TextField cautare5;
    @FXML
    private TextField cautare6;
    @FXML
    private Button chooseButton;
    @FXML
    private Label utilizatoriAlesi;
    @FXML
    private ListView<String> utilizatoriList;

    private UtilizatorRepositoryDB repo_ut=new UtilizatorRepositoryDB(new UtilizatorValidator(), "jdbc:postgresql://localhost:5432/lab6","postgres","catalina");

    private MessageRepositoryDB messageRepo = new MessageRepositoryDB("jdbc:postgresql://localhost:5432/lab6", "postgres", "catalina");

    private List<Utilizator> utilizatori_conv=new ArrayList<>();

    @FXML
    private TextArea chat;
    @FXML
    private TextField mesaje;
    @FXML
    private Button sendButton;

    String loggedInFirstName = LoggedIn.getFirstName();
    String loggedInLastName = LoggedIn.getLastName();
    String prenume = LoggedIn.getFirstName();
    String nume = LoggedIn.getLastName();

    public ChatController() {
    }

    public void initialize() {

    }

    public void load() {
        try {
            List<Message> allMessages = (List<Message>) messageRepo.findAll();
            chat.clear();

            for (Utilizator utilizator : utilizatori_conv) {
                String convFirstName = utilizator.getFirstName();
                String convLastName = utilizator.getLastName();

                chat.appendText("Conversatie cu " + convFirstName + " " + convLastName + ":\n");

                for (Message message : allMessages) {
                    boolean primeste = message.getTo().stream()
                            .anyMatch(user -> user.getFirstName().equalsIgnoreCase(loggedInFirstName) &&
                                    user.getLastName().equalsIgnoreCase(loggedInLastName)) &&
                            message.getFrom().getFirstName().equalsIgnoreCase(convFirstName) &&
                            message.getFrom().getLastName().equalsIgnoreCase(convLastName);

                    boolean trimite = message.getFrom().getFirstName().equalsIgnoreCase(loggedInFirstName) &&
                            message.getFrom().getLastName().equalsIgnoreCase(loggedInLastName) &&
                            message.getTo().stream()
                                    .anyMatch(user -> user.getFirstName().equalsIgnoreCase(convFirstName) &&
                                            user.getLastName().equalsIgnoreCase(convLastName));

                    if (primeste) {
                        chat.appendText("De la " + message.getFrom().getFirstName() + ": " +
                                message.getMessage() + "\n");
                    }

                    if (trimite) {
                        chat.appendText("De la " + loggedInFirstName + ": " +
                                message.getMessage() + "\n");
                    }
                }

                chat.appendText("\n");
            }
        } catch (RuntimeException e) {
            chat.appendText("Eroare la încărcarea mesajelor: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }



    @FXML
    public void onChooseButtonClick() {
        String nume = cautare5.getText();
        String prenume = cautare6.getText();

        try {
            Utilizator utilizatorGasit = repo_ut.findByFullName(prenume, nume);
            utilizatori_conv.add(utilizatorGasit);
            if (utilizatorGasit != null) {
                utilizatoriList.getItems().add(utilizatorGasit.getFirstName() + " " + utilizatorGasit.getLastName());
            } else {
                utilizatoriAlesi.setText("Nu a fost găsit niciun utilizator.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilizatoriAlesi.setText("Eroare la conectarea la baza de date.");
        }
        load();
    }

    @FXML
    public void onSendButtonClick() {
        String mesaj = mesaje.getText().trim();
        Utilizator ut;

        try {
            ut = repo_ut.findByFullName(prenume, nume);
            if (ut == null) {
                chat.appendText("Nu exista utilizatorul\n");
                return;
            }
            Message mes=new Message(ut, utilizatori_conv,mesaj,LocalDateTime.now());
            messageRepo.saveMessage(mes);
            for(Utilizator utilizator : utilizatori_conv)
            {
                chat.appendText("de la " +prenume  +" catre " + utilizator.getFirstName()+ ": " + mesaj + "\n");
            }

            mesaje.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UtilizatorEvent utilizatorEvent) {

    }
}
