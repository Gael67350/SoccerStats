package model;

import java.util.ArrayList;

public class HeatMap
{
    int data[][] = new int[SoccerField.MAX_WIDTH][SoccerField.MAX_HEIGHT];

    public HeatMap()
    {
        for(int x = 0 ; x<SoccerField.MAX_WIDTH ; x++)
        {
            for(int y = 0 ; y<SoccerField.MAX_HEIGHT ; y++)
            {
                data[x][y] = 0;
            }
        }
    }

    public void incrementAtPoint(int x,int y)
    {
        data[x][y] += 1;
    }

    public int getHeadPoint(int x,int y)
    {
        return data[x][y];
    }
}
