package com.polytech.soccerStats.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private View3DController view3DController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view3DController = new View3DController();
        view3DController.injectMainController(this);
    }
}
