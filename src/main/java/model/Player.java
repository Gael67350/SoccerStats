package model;

import model.HeatMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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

    private ArrayList<Position> positions = new ArrayList<>();

    public int getTagId()
    {
        return tagId;
    }

    public int getRecordCount(Date currentDate)
    {
        int count = 0;

        for (Position current : positions)
        {
            if (current.happensOn(currentDate))
                count++;
        }

        return count;
    }

    public int getRecordCount()
    {
        int count = 0;

        for (Position current : positions)
        {
            count++;
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

    public ArrayList<Position> getPositions()
    {
        return positions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return tagId == player.tagId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tagId);
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "tagId=" + tagId +
                '}';
    }
}
