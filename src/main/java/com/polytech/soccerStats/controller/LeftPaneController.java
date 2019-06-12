package com.polytech.soccerStats.controller;

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

    public void enablePlayerSection()
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

    public void disablePlayerSection()
    {
        playerDataPane.setDisable(true);

        idValue.setVisible(false);
        totDistanceValue.setVisible(false);
        headingValue.setVisible(false);
        directionValue.setVisible(false);
        energyValue.setVisible(false);
    }

    @Override
    public void init(MainController controller)
    {
        playerHeatmapToggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) ->
        {
            if (playerHeatmapToggleGroup.getSelectedToggle() != null)
            {
                if(playerHeatmapToggleGroup.getSelectedToggle().equals(noneRD))
                {
                    System.out.println("noneRD");
                }
                else if(playerHeatmapToggleGroup.getSelectedToggle().equals(colorRD))
                {
                    System.out.println("colorRD");
                }
                else if(playerHeatmapToggleGroup.getSelectedToggle().equals(histogramRD))
                {
                    System.out.println("histogramRD");
                }
            }
        });

        disablePlayerSection();
    }
}
