package com.polytech.soccerStats.model;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.controller.View3DController;
import com.polytech.soccerStats.event.PlayerSelectedEvent;
import com.polytech.soccerStats.utils.Fx3DGroup;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Material;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.net.URL;

public class PlayerCursor extends Fx3DGroup
{

    private Position currentPosition;
    private Billboard billboard;
    private MeshView[] meshViews;
    private Camera camera;

    public PlayerCursor(Position position, Camera camera) throws IOException
    {
        currentPosition = position;
        billboard = new Billboard(this);
        meshViews = loadMeshs();
        this.camera = camera;

        // Display player cursor
        getChildren().addAll(meshViews);
        set3DRotate(0, Math.toDegrees(position.getHeading()), 0);

        // Set cursor position
        Point2D mappedPosition = View3DController.mapPosition(position.getPos());
        set3DTranslate(mappedPosition.getX(), -0.75, mappedPosition.getY());
        set3DScale(2);

        // Handle mouse click event
        PlayerCursor that = this;

        setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                fireEvent(new PlayerSelectedEvent(that));
            }
        });
    }

    public void moveTo(Position target)
    {
        if(getPlayer().getCurrentPositionIndex() == 1 && !getPlayer().isVisible()) {
            getPlayer().setVisible(true);
        }

        Point2D mappedPosition = View3DController.mapPosition(target.getPos());

        set3DTranslate(mappedPosition.getX(), -0.75, mappedPosition.getY());
        set3DRotate(0, Math.toDegrees(target.getHeading()), 0);

        currentPosition = target;

        this.getBillboard().update(camera);
        this.setVisible(target.getRelatedPlayer().isVisible());
        this.getBillboard().setVisible(target.getRelatedPlayer().isVisible());
    }

    public Player getPlayer()
    {
        return currentPosition.getRelatedPlayer();
    }

    public Billboard getBillboard()
    {
        return billboard;
    }

    public void setMaterial(Material material)
    {
        for (MeshView m : meshViews)
        {
            m.setMaterial(material);
        }
    }

    private static MeshView[] loadMeshs()
    {
        // Load cursor object
        ObjModelImporter objModelImporter = new ObjModelImporter();

        try
        {
            URL objUrl = PlayerCursor.class.getClassLoader().getResource("obj/player.obj");
            objModelImporter.read(objUrl);
        }
        catch (ImportException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        MeshView[] meshViews = objModelImporter.getImport();
        objModelImporter.close();

        return meshViews;
    }

    public Position getCurrentPosition()
    {
        return currentPosition;
    }
}
