package com.polytech.soccerStats.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Player implements Comparable<Player> {
    private final int tagId;
    private float totalDistance;

    private int currentPositionIndex;

    private ArrayList<Position> positions = new ArrayList<>();

    private HeatMap heatMap = new HeatMap();

    private boolean visible = true;

    public Player(int tagId) {
        this.tagId = tagId;
        totalDistance = 0;
        //before the begining index placed before the first element
        currentPositionIndex = -1;
    }

    //simulation related methods

    public boolean advanceToDate(Date newDate)
    {
        if(currentPositionIndex >= positions.size()-1)
        {
            return false;
        }
        else
        {
            while(currentPositionIndex < positions.size()-1 &&( positions.get(currentPositionIndex+1).getTimestamp().before(newDate) || positions.get(currentPositionIndex+1).getTimestamp().equals(newDate)))
            {
                currentPositionIndex ++;
                if(currentPositionIndex > 0)
                {
                    totalDistance += Math.sqrt(
                                                 Math.pow(
                                                             positions.get(currentPositionIndex).getPosX()
                                                            -positions.get(currentPositionIndex-1).getPosX(),2
                                                         )
                                                +Math.pow(
                                                             positions.get(currentPositionIndex).getPosY()
                                                            -positions.get(currentPositionIndex-1).getPosY(),2
                                                         )
                                              );
                }
                else
                {
                    totalDistance += Math.sqrt(
                            Math.pow(positions.get(currentPositionIndex + 1).getPosX(), 2)
                                    + Math.pow(positions.get(currentPositionIndex + 1).getPosY(), 2)
                    );
                }
                heatMap.incrementAtPoint((int)(positions.get(currentPositionIndex).getPosX()), (int)(positions.get(currentPositionIndex).getPosY()));
            }

            return true;
        }
    }

    public boolean rewind(Date revTime) {
        totalDistance = 0;
        heatMap = new HeatMap();
        currentPositionIndex = -1;

        return advanceToDate(revTime);
    }

    public Position getCurrentInfo() {
        if (currentPositionIndex != -1)
            return positions.get(currentPositionIndex);
        else
            return null;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public int find(Position position) {
        if (!positions.contains(position)) {
            return -1;
        }

        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(position)) {
                return i;
            }
        }

        return -1;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //test methods

    public int getTagId() {
        return tagId;
    }

    public int getRecordCount(Date currentDate) {
        int count = 0;

        for (Position current : positions) {
            if (current.happensOn(currentDate))
                count++;
        }

        return count;
    }

    public int getRecordCount() {
        int count = 0;

        for (Position current : positions) {
            count++;
        }

        return count;
    }

    public Position getRecord(int index) {
        return positions.get(index);
    }

    public HeatMap getHeatMap() {
        return heatMap;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public Date getEarliestDate() {
        return positions.get(0).getTimestamp();
    }

    public Date getLastestDate()
    {
        return positions.get(positions.size()-1).getTimestamp();
    }

    public long getTimeGap()
    {
        return positions.get(1).getTimestamp().getTime() - positions.get(0).getTimestamp().getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return tagId == player.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId);
    }

    @Override
    public String toString() {
        return "Player{" +
                "tagId=" + tagId +
                '}';
    }
  
    @Override
    public int compareTo(Player o) {
        if (this.tagId > o.tagId) {
            return 1;
        }

        if (this.tagId < o.tagId) {
            return -1;
        }

        return 0;
    }

    public int getCurrentPositionIndex() {
        return currentPositionIndex;  
    }

    public void reinitTimeLinePosition()
    {
        currentPositionIndex = -1;
    }
}
