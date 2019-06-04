package model;

import java.util.ArrayList;
import java.util.Date;

public class SoccerField
{
    public static final int MAX_HEIGHT = 68;
    public static final int MAX_WIDTH = 105;

    ArrayList<Player> playerListing = new ArrayList<>();

    public int getRecordCount(Date currentDate)
    {
        int count = 0;
        for(Player current : playerListing)
        {
            count += current.getRecordCount(currentDate);
        }

        return count;
    }
}
