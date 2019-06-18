package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayerSelectionController extends DelegatedController {

    @FXML
    private BorderPane playerSelectionListPane;

    @FXML
    private VBox playerSelectionList;

    private HashMap<CheckBox, Player> checkBoxMap = new HashMap<>();

    @Override
    public void init(MainController controller, SoccerStats app) {
        playerSelectionListPane.setDisable(true);
    }

    public void load(SoccerField soccerField) {
        ArrayList<Player> players = new ArrayList<>(soccerField.getPlayers());
        Collections.sort(players);

        for (Player p : players) {
            CheckBox checkBox = new CheckBox("Player " + p.getTagId());
            checkBox.setSelected(true);

            checkBoxMap.put(checkBox, p);
            playerSelectionList.getChildren().add(checkBox);

            // Handle checkbox check action
            checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getSource() instanceof CheckBox) {
                        CheckBox c = (CheckBox) event.getSource();
                        Player associatedPlayer = checkBoxMap.get(c);

                        if (associatedPlayer != null) {
                            associatedPlayer.setVisible(c.isSelected());
                        }
                    }
                }
            });
        }

        playerSelectionListPane.setDisable(false);
    }
}
