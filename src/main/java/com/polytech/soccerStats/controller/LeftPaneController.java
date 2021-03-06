package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LeftPaneController extends DelegatedController
{
    //top controls
    @FXML
    private Button openBtn;

    @FXML
    private Button topViewBtn;

    //parameters section
    @FXML
    private VBox parametersPane;

    @FXML
    private Spinner playbackSpeedSpinner;

    @FXML
    private Spinner trailLengthSpinner;

    //player section
    @FXML
    private VBox playerDataPane;


    //player display

    @FXML
    private Label idValue;
    @FXML
    private Label totDistanceValue;
    @FXML
    private Label headingValue;
    @FXML
    private Label directionValue;
    @FXML
    private Label energyValue;

    //heatmap management

    @FXML
    private ToggleGroup playerHeatmapToggleGroup;

    @FXML
    RadioButton noneRD;
    @FXML
    RadioButton colorRD;
    @FXML
    RadioButton histogramRD;

    private SoccerField currentMatch;

    public void load(SoccerField currentMatch)
    {
        this.currentMatch = currentMatch;
        enableParametersSection();
    }

    @Override
    public void init(MainController controller, SoccerStats LDapp)
    {
        this.mainController = controller;
        this.app = LDapp;

        //heatmap handler

        playerHeatmapToggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            if (playerHeatmapToggleGroup.getSelectedToggle() != null)
            {
                if (playerHeatmapToggleGroup.getSelectedToggle().equals(noneRD))
                {
                    mainController.clearHeatMap();
                }
                else if (playerHeatmapToggleGroup.getSelectedToggle().equals(colorRD))
                {
                    mainController.display2DHeatMap(currentMatch.getHighlightedPlayer());
                }
                else if (playerHeatmapToggleGroup.getSelectedToggle().equals(histogramRD))
                {
                    mainController.display3DHeatMap(currentMatch.getHighlightedPlayer());
                }
            }
        });


        //init spinner
        playbackSpeedSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15));

        trailLengthSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500));
        //spinner event handlers

        playbackSpeedSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) ->
        {
            if (!oldValue.equals(newValue))
            {
                currentMatch.setPlaybackSpeed(Integer.parseInt(newValue));
            }
        });

        trailLengthSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) ->
        {
            if (newValue != null && !newValue.equals("") && !oldValue.equals(newValue))
            {
                currentMatch.setTrailLength(Integer.parseInt(newValue));
                mainController.clearTrail();

                if (currentMatch.getTrailLength() > 0) {
                    for (Player p : currentMatch.getPlayers()) {
                        mainController.displayTrail(p);
                    }
                }
            }
        });

        // openFile listener
        openBtn.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File selected = fileChooser.showOpenDialog(new Stage());

            if(selected != null)
            {
                if(currentMatch != null)
                {
                    unloadMatch();
                }

                app.openFile(selected.getPath());

                currentMatch.initialiseSimulation();
                mainController.initBar();
                enableParametersSection();
                topViewBtn.setDisable(false);
            }

        });

        // topViewHandler
        topViewBtn.setOnAction(event ->
        {
            mainController.launchCameraReinitialiser();
        });

        disablePlayerSection();
        disableParametersSection();
    }


    public void unloadMatch()
    {
        disablePlayerSection();
        disableParametersSection();
        topViewBtn.setDisable(true);

        playbackSpeedSpinner.getValueFactory().setValue(1);
        trailLengthSpinner.getValueFactory().setValue(1);

        currentMatch = null;
    }

    public void enablePlayerSection()
    {
        playerHeatmapToggleGroup.selectToggle(noneRD);

        playerDataPane.setDisable(false);

        idValue.setVisible(true);
        totDistanceValue.setVisible(true);
        headingValue.setVisible(true);
        directionValue.setVisible(true);
        energyValue.setVisible(true);


        idValue.setText(""+currentMatch.getHighlightedPlayer().getTagId());
        totDistanceValue.setText(""+currentMatch.getHighlightedPlayer().getTotalDistance());
        headingValue.setText(""+currentMatch.getHighlightedPlayer().getCurrentInfo().getHeading());
        directionValue.setText(""+currentMatch.getHighlightedPlayer().getCurrentInfo().getDirection());
        energyValue.setText(""+currentMatch.getHighlightedPlayer().getCurrentInfo().getEnergy());
    }

    private void enableParametersSection()
    {
        parametersPane.setDisable(false);
        playbackSpeedSpinner.getValueFactory().setValue(currentMatch.getPlaybackSpeed());
        trailLengthSpinner.getValueFactory().setValue(currentMatch.getTrailLength());
    }

    public void disablePlayerSection()
    {
        playerDataPane.setDisable(true);

        idValue.setVisible(false);
        totDistanceValue.setVisible(false);
        headingValue.setVisible(false);
        directionValue.setVisible(false);
        energyValue.setVisible(false);

        idValue.setText("");
        totDistanceValue.setText("");
        headingValue.setText("");
        directionValue.setText("");
        energyValue.setText("");
    }

    private void disableParametersSection()
    {
        parametersPane.setDisable(true);
    }

    public void updatePlayer()
    {
        if(currentMatch.getHighlightedPlayer() != null)
        {
            idValue.setText("" + currentMatch.getHighlightedPlayer().getTagId());
            totDistanceValue.setText("" + currentMatch.getHighlightedPlayer().getTotalDistance());
            headingValue.setText("" + currentMatch.getHighlightedPlayer().getCurrentInfo().getHeading());
            directionValue.setText("" + currentMatch.getHighlightedPlayer().getCurrentInfo().getDirection());
            energyValue.setText("" + currentMatch.getHighlightedPlayer().getCurrentInfo().getEnergy());

            if (playerHeatmapToggleGroup.getSelectedToggle().equals(noneRD))
            {
                mainController.clearHeatMap();
            }
            else if (playerHeatmapToggleGroup.getSelectedToggle().equals(colorRD))
            {
                mainController.display2DHeatMap(currentMatch.getHighlightedPlayer());
            }
            else if (playerHeatmapToggleGroup.getSelectedToggle().equals(histogramRD))
            {
                mainController.display3DHeatMap(currentMatch.getHighlightedPlayer());
            }
        }else{
            mainController.clearHeatMap();
        }

    }


}
