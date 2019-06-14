package com.polytech.soccerStats.model;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.controller.View3DController;
import com.polytech.soccerStats.utils.Fx3DGroup;
import javafx.geometry.Point2D;
import javafx.scene.paint.Material;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.net.URL;

public class PlayerCursor extends Fx3DGroup {

    private Position currentPosition;
    private Billboard billboard;
    private MeshView[] meshViews;

    public PlayerCursor(Position position) throws IOException {
        currentPosition = position;
        billboard = new Billboard(this);
        meshViews = loadMeshs();

        // Display player cursor
        getChildren().addAll(meshViews);
        set3DRotate(0, Math.toDegrees(position.getHeading()), 0);

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

    public Billboard getBillboard() {
        return billboard;
    }

    public void setMaterial(Material material) {
        for (MeshView m : meshViews) {
            m.setMaterial(material);
        }
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
