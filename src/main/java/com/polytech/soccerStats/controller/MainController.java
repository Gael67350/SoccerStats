    @FXML
    private View3DController view3DController;
        view3DController.load(soccerField);
package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.SoccerField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    private LeftPaneController leftPaneController;
    private SoccerStats app;


    public void loadMatch(SoccerField soccerField) throws IOException
    {
        leftPaneController.load(soccerField);
    }

    public void initSubControllers(SoccerStats app)
    {
        this.app = app;
        leftPaneController.init(this,app);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
        view3DController.init(this);