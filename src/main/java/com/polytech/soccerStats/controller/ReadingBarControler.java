package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.TimedUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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

    @FXML
    private HBox readingBar;

    private SoccerField currentMatch;

    Image playOnImage = new Image(getClass().getClassLoader().getResource("img/play_btn_image_on.png").toExternalForm());
    Image playOffImage = new Image(getClass().getClassLoader().getResource("img/play_btn_image_off.png").toExternalForm());

    Image stopOnImage = new Image(getClass().getClassLoader().getResource("img/stop_btn_image_on.png").toExternalForm());
    Image stopOffImage = new Image(getClass().getClassLoader().getResource("img/stop_btn_image_off.png").toExternalForm());

    Image pauseImage = new Image(getClass().getClassLoader().getResource("img/pause_btn_image.png").toExternalForm());


    @Override
    public void init(MainController controller, SoccerStats app)
    {
        this.mainController = controller;
        this.app = app;

        readingBar.setDisable(true);

        playBtn.setOnMouseClicked(event ->
        {
            if(!currentMatch.isPlaying())
            {
                playBtn.setImage(pauseImage);
                stopBtn.setImage(stopOnImage);
                currentMatch.togglePlayStatus();
                setTimerValue();
            }
            else
            {
                playBtn.setImage(playOnImage);
                currentMatch.togglePlayStatus();
            }
        });

        stopBtn.setOnMouseClicked(event ->
        {
            if(currentMatch.getPassedTime() > 0)
            {
                if(currentMatch.isPlaying())
                    currentMatch.togglePlayStatus();

                currentMatch.reinitTimeline();

                stopBtn.setImage(stopOffImage);
                playBtn.setImage(playOnImage);
            }
        });
    }


    public void load(SoccerField currentMatch)
    {
        this.currentMatch = currentMatch;

        readingBar.setDisable(false);

        playBtn.setImage(playOnImage);
        setTimerValue();
    }

    public void setTimerValue()
    {
        timer.cancel();
        timer = new Timer(true);
        timedEvent = new TimedUpdate(currentMatch,mainController);
        timer.scheduleAtFixedRate(timedEvent, 0, (currentMatch.getWaitTime() / currentMatch.getPlaybackSpeed()));
    }

}
