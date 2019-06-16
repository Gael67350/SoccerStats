package com.polytech.soccerStats.utils;

import com.polytech.soccerStats.model.SoccerField;

import java.util.TimerTask;

public class TimedUpdate extends TimerTask
{

    private SoccerField currentGame;

    public TimedUpdate(SoccerField currentGame)
    {
        this.currentGame = currentGame;
    }

    @Override
    public void run()
    {
        if(currentGame.isPlaying())
            currentGame.advanceSim();
    }
}
