package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private LeftPaneController leftPaneController;
    private SoccerStats app;

    @FXML
    private View3DController view3DController;

    @FXML
    private PlayerSelectionController playerSelectionController;
  
    @FXML
    private ReadingBarControler readingBarController;

    public void loadMatch(SoccerField soccerField) throws IOException {
        view3DController.load(soccerField);
        leftPaneController.load(soccerField);
        readingBarController.load(soccerField);
        playerSelectionController.load(soccerField);
    }

    public void initSubControllers(SoccerStats app) {
        this.app = app;
        view3DController.init(this, app);
        leftPaneController.init(this, app);
        readingBarController.init(this,app);
        playerSelectionController.init(this, app);
    }

    public void launchCameraReinitialiser() {
        view3DController.reinitCamera();
    }

    public void higlightPlayer()
    {
        leftPaneController.enablePlayerSection();
    }
    public  void disableHighlighting()
    {
        leftPaneController.disablePlayerSection();
    }

    public void display2DHeatMap(Player p) {
        view3DController.display2DHeatMap(p);
    }

    public void display3DHeatMap(Player p) {
        view3DController.display3DHeatMap(p);
    }

    public void clearHeatMap() {
        view3DController.clearHeatMap();
    }

    public void displayTrail(Player p) {
        view3DController.displayTrail(p);
    }

    public void clearTrail() {
        view3DController.clearTrail();

    public void callDisplayUpdate()
    {
        leftPaneController.updatePlayer();
        view3DController.updatePositions();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
