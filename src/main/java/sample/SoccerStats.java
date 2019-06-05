package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SoccerField;
import utils.DataImporter;

public class SoccerStats extends Application
{
    private SoccerField currentGame;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    private void openFile(String loadingPath)
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


}
