package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.model.SoccerField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private View3DController view3DController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view3DController.init(this);
    }

    public void loadMatch(SoccerField soccerField) throws IOException {
        view3DController.load(soccerField);
    }
}
