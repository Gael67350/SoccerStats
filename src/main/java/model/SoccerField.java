package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SoccerField {
    public static final int MAX_HEIGHT = 68;
    public static final int MAX_WIDTH = 105;

    private ArrayList<Player> playerListing = new ArrayList<>();

    public int getRecordCount(Date currentDate) {
        int count = 0;
        for (Player current : playerListing) {
            count += current.getRecordCount(currentDate);
        }

        return count;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(playerListing);
    }

    public Player getPlayer(int tagId) {
        for (Player p : playerListing) {
            if (p.getTagId() == tagId) {
                return p;
            }
        }

        return null;
    }

    public boolean addPlayer(Player player) {
        if (!playerListing.contains(player)) {
            return playerListing.add(player);
        }

        return false;
    }
}
