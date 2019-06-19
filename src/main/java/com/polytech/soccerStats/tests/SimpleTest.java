package com.polytech.soccerStats.tests;

import static org.junit.Assert.*;

import com.polytech.soccerStats.controller.MainController;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.utils.DataImporter;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class SimpleTest
{

    SoccerStats dataStructure;

    @Before
    public void setUp() throws Exception
    {
        dataStructure = new SoccerStats();
        dataStructure.openFile("./src/main/resources/testResources/2013-11-03_tromso_stromsgodset_first.csv");
    }

    @Test
    public void loadTest()
    {
    }

    @Test
    public void recordNumberTest()
    {
        assertEquals(623517, dataStructure.getGameRecordCount());
    }

    @Test
    public void playerPositionTest()
    {
        Player toCheck = new Player(-1000) ;

        for(Player current : dataStructure.getPlayers())
        {
            if(current.getTagId() == 5)
            {
                toCheck = current;
            }
        }

        assertEquals( 54.98,toCheck.getRecord(10000).getPosX(),0.01);
        //RÃ©cupÃ©rer l'enregistrement pour l'index 10000 et vÃ©rifier que la position en x du joueur avec l'id 5 est Ã©gale Ã  65.57721
    }

    @Test
    public void playerHeatMapMaxValueTest()
    {
        //vÃ©rifier que le joueur #14 a Ã©tÃ© enregistrÃ© au maximum 314 fois dans la mÃªme zone d'un mÃ¨tre carrÃ©
        Player toCheck = new Player(-1000) ;

        for(Player current : dataStructure.getPlayers())
        {
            if(current.getTagId() == 14)
            {
                toCheck = current;
            }
        }

        try
        {
            toCheck.advanceToDate(new SimpleDateFormat(DataImporter.DATE_FORMAT).parse("2013-11-03 18:48:22"),new MainController(),false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertFalse(true);
        }


        //avancer a fond dans le temps !

        assertEquals(314,toCheck.getHeatMap().getMaximumValue());
    }

    @Test
    public void playerHeatMapCornerTest()
    {
        //vÃ©rifier que le joueur #14 n'a jamais Ã©tÃ© dans le coin de corner le plus proche de l'origine du repÃ¨re des enregistrements
        //vÃ©rifier que le joueur #14 a Ã©tÃ© enregistrÃ© au maximum 314 fois dans la mÃªme zone d'un mÃ¨tre carrÃ©

        Player toCheck = new Player(-1000) ;

        for(Player current : dataStructure.getPlayers())
        {
            if(current.getTagId() == 14)
            {
                toCheck = current;
            }
        }

        try
        {
            toCheck.advanceToDate(new SimpleDateFormat(DataImporter.DATE_FORMAT).parse("2013-11-03 18:48:22"),new MainController(),false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertFalse(true);
        }


        assertEquals(0,toCheck.getHeatMap().getHeadPoint(0,0));
    }

}
