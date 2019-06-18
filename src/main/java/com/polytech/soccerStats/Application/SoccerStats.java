package com.polytech.soccerStats.Application;

import com.polytech.soccerStats.controller.MainController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.DataImporter;

import java.sql.Time;
import java.util.List;

public class SoccerStats extends Application
{
    private SoccerField currentGame;

    private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("./view/home.fxml"));

        BorderPane root = loader.load();

        mainController = loader.getController();
        mainController.initSubControllers(this);

        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SoccerStats");
        primaryStage.show();

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            long cumulativeWait = 0;
            long prectime = startNanoTime;

            public void handle(long currentNanoTime)
            {
                cumulativeWait += (currentNanoTime - prectime)/1000;
                prectime = currentNanoTime;

                if(currentGame != null)
                {
                    if (cumulativeWait >= currentGame.getWaitTime() && currentGame.isPlaying())
                    {
                        currentGame.advanceSim();
                        mainController.callDisplayUpdate();
                        cumulativeWait = 0;
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void testLecture() throws Exception
    {
        while (1 == 1)
        {
            Thread.sleep(currentGame.getWaitTime() / currentGame.getPlaybackSpeed());
            currentGame.advanceSim();
        }
    }

    public void openFile(String loadingPath)
    {
        DataImporter importer = new DataImporter(loadingPath);

        try
        {
            currentGame = importer.loadData();
            mainController.loadMatch(currentGame);
        }
        catch (Exception e)
        {
            System.err.print("An error occured while loading input data");
            e.printStackTrace();
        }

    }

    //methods for test

    public int getGameRecordCount()
    {
        return currentGame.getRecordCount();
    }

    public List<Player> getPlayers()
    {
        return currentGame.getPlayers();
    }


}
