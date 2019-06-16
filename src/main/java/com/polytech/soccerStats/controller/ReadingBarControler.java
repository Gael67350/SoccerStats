package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.TimedUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;

public class ReadingBarControler extends DelegatedController
{

    Timer timer = new Timer(true);
    TimedUpdate timedEvent;

    @FXML
    private ImageView playBtn;

    @FXML
    private ImageView stopBtn;

    @FXML
    private Slider currentTimeSlider;

    @FXML
    private Label currentTimeLabel;

    private SoccerField currentMatch;

    Image playImage = new Image(getClass().getClassLoader().getResource("img/play_btn_image.png").toExternalForm());
    Image pauseImage = new Image(getClass().getClassLoader().getResource("img/pause_btn_image.png").toExternalForm());


    @Override
    public void init(MainController controller, SoccerStats app)
    {
        this.mainController = controller;
        this.app = app;

        playBtn.setOnMouseClicked(event ->
        {
            if(!currentMatch.isPlaying())
            {
                playBtn.setImage(pauseImage);
                currentMatch.togglePlayStatus();
            }
            else
            {
                playBtn.setImage(playImage);
                currentMatch.togglePlayStatus();
            }
        });
    }


    public void load(SoccerField currentMatch)
    {
        this.currentMatch = currentMatch;
        timedEvent = new TimedUpdate(currentMatch);

    }

    public void setTimerValue()
    {
        timer.cancel();
        timer.scheduleAtFixedRate(timedEvent, 0, (currentMatch.getWaitTime() / currentMatch.getPlaybackSpeed()));
    }

}
