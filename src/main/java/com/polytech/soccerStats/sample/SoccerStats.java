package com.polytech.soccerStats.sample;

import com.polytech.soccerStats.controller.MainController;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.DataImporter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SoccerStats extends Application {
    private static MainController mainController;

    private SoccerField currentGame;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/home.fxml"));

        BorderPane root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SoccerStats");
        primaryStage.show();

        // For test purpose only
        mainController = loader.getController();
        openFile("./2013-11-03_tromso_stromsgodset_first.csv");

        try {
            mainController.loadMatch(currentGame);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openFile(String loadingPath) {
        DataImporter importer = new DataImporter(loadingPath);

        try {
            currentGame = importer.loadData();

        } catch (Exception e) {
            System.err.print("An error occured while loading input data");
            e.printStackTrace();
        }
    }

    //methods for test

    public int getGameRecordCount() {
        return currentGame.getRecordCount();
    }

    public List<Player> getPlayers() {
        return currentGame.getPlayers();
    }


}
