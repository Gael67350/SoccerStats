package tests;

import static org.junit.Assert.*;

import main.java.sample.SoccerStats;
import org.junit.Before;
import org.junit.Test;

public class SimpleTest
{

    SoccerStats myDataManager;

    @Before
    public void setUp() throws Exception
    {
        //Initialisation de la partie applicative
        //myDataManager = new DataManager();
    }

    @Test
    public void loadTest()
    {
        //Chargement du fichier 2013-11-03_tromso_stromsgodset_first.csv
        //assertNotEquals(myDataManager, null);

    }

    @Test
    public void recordNumberTest()
    {

        //VÃ©rifier que le nombre d'enregistrements est Ã©gal Ã  56661
        //assertEquals(new Integer(56660), myDataManager.getRecorNumber());

    }

    @Test
    public void playerPositionTest()
    {
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
