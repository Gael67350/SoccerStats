package com.polytech.soccerStats.event;

import com.polytech.soccerStats.utils.CameraManager;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class CameraUpdateEvent extends Event {
    public static final EventType<CameraUpdateEvent> CAMERA_UPDATED = new EventType<>(Event.ANY, "CAMERA_UPDATED");

    private CameraManager cameraManager;

    public CameraUpdateEvent(CameraManager cameraManager) {
        super(CAMERA_UPDATED);
        this.cameraManager = cameraManager;
    }

    public CameraUpdateEvent(CameraManager source, EventTarget target) {
        super(source, target, CAMERA_UPDATED);
        this.cameraManager = source;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }
}
