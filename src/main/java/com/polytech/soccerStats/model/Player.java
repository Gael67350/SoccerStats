package com.polytech.soccerStats.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Player
{
    private final int tagId;
    private float totalDistance;

    private int currentPositionIndex;

    private ArrayList<Position> positions = new ArrayList<>();

    private HeatMap heatMap = new HeatMap();

    public Player(int tagId)
    {
        this.tagId = tagId;
        totalDistance = 0;
        //before the begining index placed before the first element
        currentPositionIndex = -1;
    }

    //simulation related methods

    public boolean advanceToDate(Date newDate)
    {
        if(currentPositionIndex == positions.size()-1)
        {
            return false;
        }
        else
        {
            while(positions.get(currentPositionIndex+1).getTimestamp().before(newDate))
            {
                currentPositionIndex ++;
                if(currentPositionIndex > 0)
                {
                    totalDistance += Math.sqrt(
                                                 Math.pow(
                                                             positions.get(currentPositionIndex+1).getPosX()
                                                            -positions.get(currentPositionIndex).getPosX(),2
                                                         )
                                                +Math.pow(
                                                             positions.get(currentPositionIndex+1).getPosY()
                                                            -positions.get(currentPositionIndex).getPosY(),2
                                                         )
                                              );
                }
                else
                {
                    totalDistance += Math.sqrt(
                                                 Math.pow(positions.get(currentPositionIndex+1).getPosX(),2)
                                                +Math.pow(positions.get(currentPositionIndex+1).getPosY(),2)
                                              );
                }

                heatMap.incrementAtPoint((int)(positions.get(currentPositionIndex+1).getPosX()), (int)(positions.get(currentPositionIndex+1).getPosY()));
            }

            return true;
        }
    }

    public boolean rewind(Date revTime)
    {
        totalDistance = 0;
        heatMap = new HeatMap();
        currentPositionIndex = -1;

        return advanceToDate(revTime);
    }

    public Position getCurrentInfo()
    {
        return positions.get(currentPositionIndex);
    }

    public float getTotalDistance()
    {
        return totalDistance;
    }

    //test methods

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
