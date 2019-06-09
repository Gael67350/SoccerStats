package com.polytech.soccerStats.model;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.controller.View3DController;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;

public class PlayerCursor extends Group {

    public PlayerCursor(Position position) {
        getChildren().addAll(loadMeshs());

        Point2D mappedPosition = View3DController.mapPosition(position.getPosX(), position.getPosY());
        setTranslateX(mappedPosition.getX());
        setTranslateZ(mappedPosition.getY());
        setTranslateY(-0.75);

        setScaleX(2);
        setScaleY(2);
        setScaleZ(2);

        getTransforms().add(new Rotate(Math.toDegrees(position.getHeading()), Rotate.Y_AXIS));
    }

    public void moveTo(Position position) {
        throw new NotImplementedException();
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
