package com.polytech.soccerStats.model;

import com.polytech.soccerStats.controller.MainController;

import java.util.*;

public class SoccerField
{
    public static final int MIN_HEIGHT = 0;
    public static final int MIN_WIDTH = 0;

    public static final int MAX_HEIGHT = 68;
    public static final int MAX_WIDTH = 105;

    private int playbackSpeed = 1;
    private int trailLength = 5;

    private ArrayList<Player> playerListing = new ArrayList<>();

    private Player HighlightedPlayer;

    private Date beginSimulationTime;
    private Date endSimulationTime;

    private Calendar simulationTime;

    private MainController controller;

    private long waitTime = Long.MAX_VALUE;

    boolean playStatus = false;


    public int getRecordCount(Date currentDate)
    {
        int count = 0;
        for (Player current : playerListing)
        {
            count += current.getRecordCount(currentDate);
        }

        return count;
    }

    public int getRecordCount()
    {
        int count = 0;
        for (Player current : playerListing)
        {
            count += current.getRecordCount();
        }
        return count;
    }

    public List<Player> getPlayers()
    {
        return Collections.unmodifiableList(playerListing);
    }

    public Player getPlayer(int tagId)
    {
        for (Player p : playerListing)
        {
            if (p.getTagId() == tagId)
            {
                return p;
            }
        }

        return null;
    }

    public boolean addPlayer(Player player)
    {
        if (!playerListing.contains(player))
        {
            boolean succes = playerListing.add(player);

            return succes;
        }

        return false;
    }

    public void initialiseSimulation()
    {
        for (Player current : playerListing)
        {
            //updating begin time & time gap
            if (beginSimulationTime == null)
            {
                beginSimulationTime = current.getEarliestDate();
            }
            else if (current.getEarliestDate().before(beginSimulationTime))
            {
                beginSimulationTime = current.getEarliestDate();
            }

            if (endSimulationTime == null)
            {
                endSimulationTime = current.getLastestDate();
            }
            else if (endSimulationTime.before(current.getLastestDate()))
            {
                endSimulationTime = current.getLastestDate();
            }

            if (current.getTimeGap() < waitTime)
            {
                waitTime = current.getTimeGap();
            }
        }

        for (Player current : playerListing)
        {
            current.advanceToDate(beginSimulationTime);
        }

        simulationTime = Calendar.getInstance();
        simulationTime.setTime(beginSimulationTime);
    }


    public void advanceSim()
    {
        simulationTime.add(Calendar.MILLISECOND, (int)(waitTime*playbackSpeed));
        for (Player current : playerListing)
        {
            current.advanceToDate(simulationTime.getTime());
        }

        if(endSimulationTime.before(simulationTime.getTime()))
        {
            controller.reinitButtons();
            playStatus = false;
            reinitTimeline();
        }

    }

    public int getPlaybackSpeed()
    {
        return playbackSpeed;
    }

    public int getTrailLength()
    {
        return trailLength;
    }

    public void setPlaybackSpeed(int playbackSpeed)
    {
        this.playbackSpeed = playbackSpeed;
    }

    public void setTrailLength(int trailLength)
    {
        this.trailLength = trailLength;
    }

    public Player getHighlightedPlayer()
    {
        return HighlightedPlayer;
    }

    public void setHighlightedPlayer(Player highlightedPlayer)
    {
        HighlightedPlayer = highlightedPlayer;
    }

    public void setPlayerListing(ArrayList<Player> playerListing)
    {
        this.playerListing = playerListing;
    }

    public long getWaitTime()
    {
        return waitTime;
    }

    public boolean isPlaying()
    {
        return playStatus;
    }

    public void togglePlayStatus()
    {
        this.playStatus = !playStatus;
    }

    public long getPassedTime()
    {
        return simulationTime.getTimeInMillis() - beginSimulationTime.getTime();
    }

    public void reinitTimeline()
    {
        simulationTime.setTimeInMillis(beginSimulationTime.getTime());

        for (Player current:playerListing)
        {
            current.reinitTimeLinePosition();
        }
    }

    public void setController(MainController controller)
    {
        this.controller = controller;
    }

    public Date getBeginSimulationTime()
    {
        return beginSimulationTime;
    }

    public Date getEndSimulationTime()
    {
        return endSimulationTime;
    }
}
