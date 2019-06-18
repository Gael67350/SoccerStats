package com.polytech.soccerStats.controller;

import com.polytech.soccerStats.Application.SoccerStats;

public abstract class DelegatedController
{

    protected MainController mainController;

    protected SoccerStats app;

    public abstract void init(MainController controller, SoccerStats app);
}