package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

    @Override
    public void init(MainController controller)
    {
        //heatmap handler

        playerHeatmapToggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            if (playerHeatmapToggleGroup.getSelectedToggle() != null)
            {
                if (playerHeatmapToggleGroup.getSelectedToggle().equals(noneRD))
                {
                    System.out.println("noneRD");
                }
                else if (playerHeatmapToggleGroup.getSelectedToggle().equals(colorRD))
                {
                    System.out.println("colorRD");
                }
                else if (playerHeatmapToggleGroup.getSelectedToggle().equals(histogramRD))
                {
                    System.out.println("histogramRD");
                }
            }
        });


        //init spinner
        playbackSpeedSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15));

        trailLengthSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
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
            if (!oldValue.equals(newValue))
            {
                currentMatch.setTrailLength(Integer.parseInt(newValue));
            }
        });

        disablePlayerSection();
        disableParametersSection();
        currentMatch = new SoccerField();
        enableParametersSection();
    }

    public void load(SoccerField currentMatch)
    {
        this.currentMatch = currentMatch;
        enableParametersSection();
    }

    public void unloadMatch()
    {
        disablePlayerSection();
        disableParametersSection();

        playbackSpeedSpinner.getValueFactory().setValue(null);
        trailLengthSpinner.getValueFactory().setValue(null);

        currentMatch = null;
    }

    private void enablePlayerSection()
    {
        playerHeatmapToggleGroup.selectToggle(noneRD);

        playerDataPane.setDisable(false);

        idValue.setText("");
        totDistanceValue.setText("");
        headingValue.setText("");
        directionValue.setText("");
        energyValue.setText("");

        idValue.setVisible(true);
        totDistanceValue.setVisible(true);
        headingValue.setVisible(true);
        directionValue.setVisible(true);
        energyValue.setVisible(true);
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
    }

    private void disableParametersSection()
    {
        parametersPane.setDisable(true);
    }

    public void updatePlayer(Player toDisplay)
    {
        idValue.setText("" + toDisplay.getTagId());
        totDistanceValue.setText("" + toDisplay.getTotalDistance());
        headingValue.setText("" + toDisplay.getCurrentInfo().getHeading());
        directionValue.setText("" + toDisplay.getCurrentInfo().getDirection());
        energyValue.setText("" + toDisplay.getCurrentInfo().getEnergy());
    }


}
