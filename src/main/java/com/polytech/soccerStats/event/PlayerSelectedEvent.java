package com.polytech.soccerStats.event;

import com.polytech.soccerStats.model.PlayerCursor;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class PlayerSelectedEvent extends Event {
    public static final EventType<PlayerSelectedEvent> PLAYER_UPDATED = new EventType<>(Event.ANY, "PLAYER_UPDATED");

    private PlayerCursor playerCursor;

    public PlayerSelectedEvent(PlayerCursor source) {
        super(source, null, PLAYER_UPDATED);
        this.playerCursor = source;
    }

    public PlayerSelectedEvent(PlayerCursor source, EventTarget target) {
        super(source, target, PLAYER_UPDATED);
        this.playerCursor = source;
    }

    public PlayerCursor getPlayerCursor() {
        return playerCursor;
    }
}
