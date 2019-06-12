package com.polytech.soccerStats.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    private LeftPaneController leftPaneController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        leftPaneController.init(this);
        leftPaneController.enablePlayerSection();
    }
}