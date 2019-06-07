package com.polytech.soccerStats.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.DataImporter;

import java.util.List;

public class SoccerStats extends Application
{
    private SoccerField currentGame;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void openFile(String loadingPath)
    {
        DataImporter importer = new DataImporter(loadingPath);

        try
        {
           currentGame = importer.loadData();
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
