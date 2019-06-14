package com.polytech.soccerStats.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SoccerField
{
    public static final int MAX_HEIGHT = 68;
    public static final int MAX_WIDTH = 105;

    private int playbackSpeed = 1;
    private int trailLength = 5;

    private ArrayList<Player> playerListing = new ArrayList<>();

    private Player HighlightedPlayer;

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
        for(Player current : playerListing)
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

    public void setPlayerListing(ArrayList<Player> playerListing)
    {
        this.playerListing = playerListing;
    }
}
