package com.polytech.soccerStats.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Camera;

public class CameraUpdateEvent extends Event {
    public static final EventType<CameraUpdateEvent> CAMERA_UPDATED = new EventType<>(Event.ANY, "CAMERA_UPDATED");

    public CameraUpdateEvent() {
        super(CAMERA_UPDATED);
    }

    public CameraUpdateEvent(Camera source, EventTarget target) {
        super(source, target, CAMERA_UPDATED);
    }
}
