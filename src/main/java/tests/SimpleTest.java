package tests;

import static org.junit.Assert.*;

import model.Player;
import model.SoccerField;
import org.junit.Before;
import org.junit.Test;
import sample.SoccerStats;

public class SimpleTest
{

    SoccerStats dataStructure;

    @Before
    public void setUp() throws Exception
    {
        dataStructure = new SoccerStats();
        dataStructure.openFile("./2013-11-03_tromso_stromsgodset_first.csv");
    }

    @Test
    public void loadTest()
    {
    }

    @Test
    public void recordNumberTest()
    {
        assertEquals(56660, dataStructure.getGameRecordCount());
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
        System.out.println(toCheck);
        assertEquals( 65.57721,toCheck.getRecord(10000).getPosX(),0.01);
        //RÃ©cupÃ©rer l'enregistrement pour l'index 10000 et vÃ©rifier que la position en x du joueur avec l'id 5 est Ã©gale Ã  65.57721
    }

    @Test
    public void playerHeatMapMaxValueTest()
    {
        //vÃ©rifier que le joueur #14 a Ã©tÃ© enregistrÃ© au maximum 314 fois dans la mÃªme zone d'un mÃ¨tre carrÃ©
    }

    @Test
    public void playerHeatMapCornerTest()
    {
        //vÃ©rifier que le joueur #14 n'a jamais Ã©tÃ© dans le coin de corner le plus proche de l'origine du repÃ¨re des enregistrements
    }

}
