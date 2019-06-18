package com.polytech.soccerStats.utils;

import com.polytech.soccerStats.controller.MainController;
import com.polytech.soccerStats.model.SoccerField;

import java.util.TimerTask;

public class TimedUpdate extends TimerTask
{

    private SoccerField currentGame;
    private MainController parentControler;

    public TimedUpdate(SoccerField currentGame,MainController parentControler)
    {
        super();
        this.currentGame = currentGame;
        this.parentControler = parentControler;
    }

    @Override
    public void run()
    {
        if (currentGame.isPlaying())
        {
            System.out.println(currentGame.getPassedTime());
            currentGame.advanceSim();
        }
    }
}
