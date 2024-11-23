package com.example.lab6fx.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.domain.validator.ValidationException;
import com.example.lab6fx.service.CerereService;
import com.example.lab6fx.service.PrietenieService;
import com.example.lab6fx.service.UtilizatorService;

import java.awt.*;
import java.sql.SQLException;

public class AddUtilizatorController {

    @FXML
    private TextField textId;

    @FXML
    private TextField textLastName;

    @FXML
    private TextField textFirstName;

    private UtilizatorService utilizatorService;
    private PrietenieService prietenieService;
    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    private void initialize() {
    }

    public void setService(UtilizatorService utilizatorService, PrietenieService prietenieService,  Stage dialogStage, Utilizator utilizator) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.dialogStage=dialogStage;
        this.utilizator =utilizator;
        if (null != utilizator) {
            setFields(utilizator);
        }
    }

    @FXML
    public void handleSave(){
        String id=textId.getText();
        String firstName = textFirstName.getText();
        String lastName = textLastName.getText();
        String password = textId.getText();
        Utilizator utilizator=new Utilizator(firstName,lastName,password);
        utilizator.setId(Long.valueOf(id));
        if (null == this.utilizator)
            saveUtilizator(utilizator);
    }

    private void saveUtilizator(Utilizator utilizator) {
        try
        {
            Utilizator ut=this.utilizatorService.addUtilizator(utilizator);
            if(ut==null)
                dialogStage.close();
        }
        catch (ValidationException e)
        {
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dialogStage.close();
    }

    private void setFields(Utilizator utilizator) {
        textId.setText(utilizator.getId().toString());
        textFirstName.setText(utilizator.getFirstName());
        textLastName.setText(utilizator.getLastName());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
