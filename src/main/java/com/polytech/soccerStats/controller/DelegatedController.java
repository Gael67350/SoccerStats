package com.polytech.soccerStats.controller;

public abstract class DelegatedController
{

    protected MainController mainController;

    public abstract void init(MainController controller);
}