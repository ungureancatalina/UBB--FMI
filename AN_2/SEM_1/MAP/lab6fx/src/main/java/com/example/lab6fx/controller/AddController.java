package com.example.lab6fx.controller;

import com.example.lab6fx.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.example.lab6fx.Observer;
import com.example.lab6fx.event.UtilizatorEvent;
import com.example.lab6fx.service.CerereService;
import com.example.lab6fx.service.PrietenieService;
import com.example.lab6fx.service.UtilizatorService;
import javafx.scene.control.Button;


import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddController implements Observer<UtilizatorEvent> {

    private String firstName_utilizator;
    private String lastName_utilizator;
    private UtilizatorService utilizatorService;
    private PrietenieService prietenieService;
    private CerereService cerereService;
    Stage dialogStage;
    ObservableList<String> cereri= FXCollections.observableArrayList();

    @FXML
    TextField cautare3;

    @FXML
    TextField cautare4;

    @FXML
    private Label label_rezultat;

    @FXML
    Button trimite_cerere_buton;

    @FXML
    private ListView<String> listView;

    @FXML
    public void initialize() {
        this.firstName_utilizator = LoggedIn.getFirstName();
        this.lastName_utilizator = LoggedIn.getLastName();
        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    HBox hbox = new HBox(10);
                    setText(item);
                    Button accept= new Button("Accepta cererea");
                    Button refuz= new Button("Refuza cererea");
                    accept.setOnAction(event->
                    {
                        try {
                            handleAccept(item);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    refuz.setOnAction(event->{
                        try {
                            handleRefuz(item);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    hbox.getChildren().addAll(accept,refuz);
                    setGraphic(hbox);
                }
            }
        });
        listView.setItems(cereri);
    }

    private void handleAccept(String cerere)  throws SQLException {
        String[] texte = cerere.split(" ");
        String firstName = texte[1];
        String lastName = texte[3];
        Long id_utilizator = getUtilizatorId(firstName_utilizator, lastName_utilizator);
        Long id_utilizator_prieten = getUtilizatorId(firstName,lastName);
        for(Cerere c : cerereService.getAllCereri())
            if(c.getId1().equals(id_utilizator_prieten) && c.getId2().equals(id_utilizator))
            {
                Cerere nou = new Cerere(id_utilizator_prieten,id_utilizator,c.getData_cererii(),"acceptat");
                nou.setId(c.getId());
                cerereService.updateCerere(nou);
                Prietenie p = new Prietenie();
                p.setId(new Tuple<>(id_utilizator_prieten,id_utilizator));
                p.setFriendsfrom(LocalDateTime.now());
                prietenieService.addPrietenie(p);
            }
    }

    private void handleRefuz(String cerere) throws SQLException  {
        String[] texte = cerere.split(" ");
        ObservableList<String> lista = FXCollections.observableArrayList();
        String firstName = texte[1];
        String lastName = texte[3];
        Long id_utilizator = getUtilizatorId(firstName_utilizator, lastName_utilizator);
        Long id_utilizator_prieten = getUtilizatorId(firstName,lastName);
        for (Cerere c : cerereService.getAllCereri())
            if (c.getId1().equals(id_utilizator_prieten) && c.getId2().equals(id_utilizator)) {
                Cerere nou = new Cerere(id_utilizator_prieten, id_utilizator, c.getData_cererii(), "refuzat");
                nou.setId(c.getId());
                cerereService.updateCerere(nou);
                for (Cerere c1 : cerereService.getAllCereri()) {
                    if (c1.getId2().equals(id_utilizator) && c1.getStatus().equals("trimis")) {
                        Utilizator u = utilizatorService.getOne(c1.getId1());
                        lista.add("Prenume: " + u.getFirstName() + " Nume: " + u.getLastName() + " Date: " + c.getData_cererii() + " Status: " + c.getStatus());
                    }
                }
                cereri.setAll(lista);
                listView.setItems(cereri);
            }
    }

    public void setService(UtilizatorService srv1, PrietenieService srv2,CerereService srv3, Stage dialogStage) {
        this.utilizatorService = srv1;
        this.prietenieService = srv2;
        this.cerereService = srv3;
        this.dialogStage = dialogStage;
    }

    public void setFirstNameUtilizator(String firstName_utilizator) {
        this.firstName_utilizator = firstName_utilizator;
    }
    public void setLastNameUtilizator(String lastName_utilizator) {
        this.lastName_utilizator = lastName_utilizator;
    }

    public Long getUtilizatorId(String firstname, String lastname) throws SQLException {
        Long id = null;
        Iterable<Utilizator> users = utilizatorService.getAllUtilizators();
        List<Utilizator> lista = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        for (Utilizator u : lista) {
            if (u.getFirstName().equals(firstname) && u.getLastName().equals(lastname)) {
                id = u.getId();
            }
        }
        return id;
    }

    @FXML
    public void handleCautare(ActionEvent event) throws SQLException {
        try{
            Long idUtilizatorPrieten = null;
            Long idUtilizator = null;
            String query1 = cautare3.getText().trim();
            String query2 = cautare4.getText().trim();

            if (query1.isEmpty() || query2.isEmpty()) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Date invalide incomplete");
                return;
            }

            idUtilizator = getUtilizatorId(firstName_utilizator, lastName_utilizator);
            if (idUtilizator == null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Nu exista utilizatorul");
                return;
            }

            idUtilizatorPrieten = getUtilizatorId(query1, query2);
            if (idUtilizatorPrieten == null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Nu exista utilizatorul");
                return;
            }

            boolean prietenieExistenta = false;
            for (Prietenie p : prietenieService.getAllPrietenii()) {
                if ((p.getId().getRight().equals(idUtilizator) && p.getId().getLeft().equals(idUtilizatorPrieten)) || (p.getId().getRight().equals(idUtilizatorPrieten) && p.getId().getLeft().equals(idUtilizator))) {
                    prietenieExistenta = true;
                    break;
                }
            }

            if (prietenieExistenta) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Exista deja prietenia");
            } else {
                label_rezultat.setText(query1 + " " + query2);
                trimite_cerere_buton.setVisible(true);
            }

        }catch (Exception e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, "A occurred: " + e.getMessage());
        }
    }

    @FXML
    public void handleAdaugaPrieten(ActionEvent event) throws SQLException {
        Long idUtilizatorPrieten = null;
        Long idUtilizator = null;
        String query1 = cautare3.getText().trim();
        String query2 = cautare4.getText().trim();
        idUtilizator = getUtilizatorId(firstName_utilizator, lastName_utilizator);
        idUtilizatorPrieten = getUtilizatorId(query1, query2);

        Cerere c = new Cerere(idUtilizator, idUtilizatorPrieten, LocalDateTime.now(),"trimis");
        cerereService.addCerere(c);
    }

    @FXML
    public void handleCereriPrietenii(ActionEvent event) throws SQLException {
        Long id = getUtilizatorId(firstName_utilizator, lastName_utilizator);
        ObservableList<String> lista = FXCollections.observableArrayList();
        for(Cerere c: cerereService.getAllCereri())
        {
            if(c.getId2().equals(id) && c.getStatus().equals("trimis")){
                Utilizator u = utilizatorService.getOne(c.getId1());
                lista.add("Prenume: "+ u.getFirstName() + " Nume: " + u.getLastName() + " Data: " + c.getData_cererii()+ " Status: "+ c.getStatus());
            }

        }
        cereri.setAll(lista);
        listView.setItems(cereri);

    }


    @Override
    public void update(UtilizatorEvent userEvent) {

    }
}
