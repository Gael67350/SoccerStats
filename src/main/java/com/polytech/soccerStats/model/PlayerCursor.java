package com.polytech.soccerStats.model;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.controller.View3DController;
import com.polytech.soccerStats.utils.Fx3DGroup;
import javafx.geometry.Point2D;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class PlayerCursor extends Fx3DGroup {

    private Position currentPosition;
    private Fx3DGroup cursor = new Fx3DGroup();

    public PlayerCursor(Position position) {
        currentPosition = position;

        getChildren().add(cursor);

        // Display player cursor
        cursor.getChildren().addAll(loadMeshs());
        cursor.set3DRotate(0, Math.toDegrees(position.getHeading()), 0);

        // Set cursor position
        Point2D mappedPosition = View3DController.mapPosition(position.getPos());
        set3DTranslate(mappedPosition.getX(), -0.75, mappedPosition.getY());
        set3DScale(2);
    }

    public void moveTo(Position target) {
        Point2D targetedMappedPosition = View3DController.mapPosition(target.getPos());
        Point2D currentMappedPosition = View3DController.mapPosition(currentPosition.getPos());
        Point2D newPos = targetedMappedPosition.subtract(currentMappedPosition);

        set3DTranslate(newPos.getX(), -0.75, newPos.getY());
        currentPosition = target;
    }

    public Player getPlayer() {
        return currentPosition.getRelatedPlayer();
    }

    private static MeshView[] loadMeshs() {
        // Load cursor object
        ObjModelImporter objModelImporter = new ObjModelImporter();

        try {
            URL objUrl = PlayerCursor.class.getClassLoader().getResource("obj/player.obj");
            objModelImporter.read(objUrl);
        } catch (ImportException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        MeshView[] meshViews = objModelImporter.getImport();
        objModelImporter.close();

        return meshViews;
    }
}
