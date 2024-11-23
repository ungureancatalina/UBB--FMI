package com.example.lab6fx;

import javafx.fxml.FXML;

import java.awt.*;

public class HelloController {

    @FXML
    private Label text;

    @FXML
    protected void onHelloButtonClicked() {
        text.setText("Hello!");
    }
}
