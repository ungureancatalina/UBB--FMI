package com.example.lab6fx.controller;

import com.example.lab6fx.HelloApplication;
import com.example.lab6fx.domain.*;
import com.example.lab6fx.domain.validator.ValidationException;
import com.example.lab6fx.event.TipEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.lab6fx.Observer;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.repository.database.CerereRepositoryDB;
import com.example.lab6fx.repository.database.PrietenieRepositoryDB;
import com.example.lab6fx.repository.database.UtilizatorRepositoryDB;
import com.example.lab6fx.service.CerereService;
import com.example.lab6fx.service.PrietenieService;
import com.example.lab6fx.service.UtilizatorService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController implements Observer<UtilizatorEvent> {

    UtilizatorRepositoryDB repo_ut;
    PrietenieRepositoryDB repo_pr;
    CerereRepositoryDB repo_ce;
    UtilizatorService serv_ut= new UtilizatorService(repo_ut,repo_pr);
    PrietenieService serv_pr = new PrietenieService(repo_ut,repo_pr);
    CerereService serv_ce = new CerereService(repo_ce);

    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,Long> tableIdUtilizator;
    @FXML
    TableColumn<Utilizator,String> tableFirstNameUtilizator;
    @FXML
    TableColumn<Utilizator,String> tableLastNameUtilizator;

    @FXML
    TextField cautare1;
    @FXML
    TextField cautare2;

    @FXML
    private Label notificareLabel;

    @FXML
    public void initialize() {
        serv_ce.addObserver(this);
        tableIdUtilizator.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("id"));
        tableLastNameUtilizator.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableFirstNameUtilizator.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableView.setItems(model);
    }

    @FXML
    public void handleAddUtilizator(ActionEvent actionEvent) {
        try{
            String query1 = LoggedIn.getFirstName();
            String query2 = LoggedIn.getLastName();
            FXMLLoader fxmlLoader = new FXMLLoader(UtilizatorController.class.getResource("/cereriAdaugate.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Aduga un prieten");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new Scene(root,1000,600));

            AddController add_controller= fxmlLoader.getController();
            add_controller.setService(serv_ut,serv_pr,serv_ce,stage);
            add_controller.setFirstNameUtilizator(query1);
            add_controller.setLastNameUtilizator(query2);
            stage.show();
        }
        catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    @FXML
    public void handleDeleteUtilizator(ActionEvent actionEvent) throws SQLException {
        Utilizator ut= serv_ut.findByFullName(LoggedIn.getFirstName(), LoggedIn.getLastName());
        Long loggedInUserId = ut.getId();
        if (loggedInUserId == null) {
            MessageAlert.showErrorMessage(null, "Utilizatorul nu exista");
            return;
        }

        // Get the selected user from the search or table
        Utilizator selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            MessageAlert.showErrorMessage(null, "Alegeti un utilizator pentru a sterge prietenia");
            return;
        }

        Long selectedUserId = selectedUser.getId();

        try {
            serv_pr.removePrietenie(new Tuple<>(loggedInUserId, selectedUserId));

            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,
                    "Sterge Prietenia",
                    "Prietenia dintre utilizatorul " + LoggedIn.getFirstName() + " "
                            + LoggedIn.getLastName() + " si utilizatorul "
                            + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " a fost stearsa");

        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, "" + e.getMessage());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, "A aparut o eroare " + e.getMessage());
        }
    }


    @FXML
    public void handleUpdateButton(ActionEvent actionEvent) {

        Utilizator toUpdate = tableView.getSelectionModel().getSelectedItem();
        if (toUpdate != null) {
            serv_ut.removeUtilizator(toUpdate.getId());
        } else MessageAlert.showErrorMessage(null, "Alege un utilizator sa fie updated");
    }

    @FXML
    public void handleCautare(ActionEvent event)  throws SQLException  {
        try {
            String query1 = cautare1.getText().trim();
            String query2 = cautare2.getText().trim();

            Iterable<Utilizator> utilizatori = serv_ut.getAllUtilizators();
            List<Utilizator> lista_utilizatori = StreamSupport.stream(utilizatori.spliterator(), false)
                    .collect(Collectors.toList());

            if (query1.isEmpty() && query2.isEmpty()) {
                model.setAll(lista_utilizatori);
            } else {
                ObservableList<Utilizator> lista = FXCollections.observableArrayList();
                for (Utilizator utilizator : lista_utilizatori) {
                    if (utilizator.getFirstName().contains(query1) || utilizator.getLastName().contains(query2)) {
                        Long id = utilizator.getId();
                        for (Prietenie p : serv_pr.getAllPrietenii()) {
                            Optional<Utilizator> u = Optional.empty();

                            if (p.getId().getLeft().equals(id)) {
                                u = Optional.ofNullable(serv_ut.getOne(p.getId().getRight()));
                            } else if (p.getId().getRight().equals(id)) {
                                u = Optional.ofNullable(serv_ut.getOne(p.getId().getLeft()));
                            }
                            u.ifPresent(lista::add);
                        }
                    }
                }
                model.setAll(lista);
            }
            tableView.setItems(model);

        } catch (Exception e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, "A occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleOpenChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chat.fxml"));
            Scene chatScene = new Scene(fxmlLoader.load());
            Stage chatStage = new Stage();
            chatStage.setScene(chatScene);
            chatStage.setTitle("Chat");
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAplicatie(UtilizatorService su,PrietenieService sp,CerereService sc)  throws SQLException {
        serv_ut = su;
        serv_pr = sp;
        serv_ce = sc;
        serv_ut.addObserver(this);
        serv_pr.addObserver(this);
        serv_ce.addObserver(this);
        initModel();
    }

    private void initModel()  throws SQLException {
        Iterable<Utilizator> utilizatori = serv_ut.getAllUtilizators();
        List<Utilizator> lista_utilizatori = StreamSupport.stream(utilizatori.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(lista_utilizatori);
    }

    @Override
    public void update(UtilizatorEvent utilizatorEvent)  {
        try {
            initModel();
            if (utilizatorEvent.getTip() == TipEvent.ADAUGA) {
                notificareLabel.setText("Ai o nouă cerere de prietenie");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getUtilizatorId(String firstname, String lastname) throws SQLException {
        Long id = null;
        Iterable<Utilizator> users = serv_ut.getAllUtilizators();
        List<Utilizator> lista = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        for (Utilizator u : lista) {
            if (u.getFirstName().equals(firstname) && u.getLastName().equals(lastname)) {
                id = u.getId();
            }
        }
        return id;
    }

}
