package model;

import java.util.ArrayList;
import java.util.Date;

public class Player
{
    private final int tagId;
    private float totalDistance;
    private HeatMap heatMap = new HeatMap();

    public Player(int tagId)
    {
        this.tagId = tagId;
        totalDistance = 0;
    }

    ArrayList<Position> positions = new ArrayList<>();

    public int getRecordCount(Date currentDate)
    {
        int count = 0;

        for(Position current : positions)
        {
            if(current.happensOn(currentDate))
                count ++;
        }

        return count;
    }

    public Position getRecord(int index)
    {
        return positions.get(index);
    }

    public HeatMap getHeatMap()
    {
        return heatMap;
    }
}
