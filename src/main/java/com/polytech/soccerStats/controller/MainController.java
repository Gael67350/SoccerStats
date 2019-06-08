package com.polytech.soccerStats.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private View3DController view3DController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view3DController.init(this);
    }
}
