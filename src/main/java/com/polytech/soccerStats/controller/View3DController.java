package com.polytech.soccerStats.controller;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.event.CameraUpdateEvent;
import com.polytech.soccerStats.event.PlayerSelectedEvent;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.PlayerCursor;
import com.polytech.soccerStats.model.Position;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.CameraManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class View3DController extends DelegatedController
{

    public static final double MAX_HEIGHT = 34;
    public static final double MAX_WIDTH = 52.5;

    @FXML
    private SubScene scene3D;

    @FXML
    private Pane messagePane;

    private Pane pane3D;

    private Group root3D;

    private CameraManager cameraManager;

    private HashMap<Player, PlayerCursor> playerCursors = new HashMap<>();

    private SoccerField currentMatch;


    public void init(MainController mainController, SoccerStats app)
    {
        this.mainController = mainController;
        this.app = app;
    }

    public void load(SoccerField soccerField) throws IOException
    {
        init3DView();

        for (Player p : soccerField.getPlayers())
        {
            // TODO : This action is not safe
            if (p.getPositions().size() > 0)
            {
                addPlayer(p, p.getPositions().get(0));
            }
        }

        currentMatch = soccerField;
        // Set message pane not visible
        messagePane.setVisible(false);
    }

    public void addPlayer(Player player, Position position) throws IOException
    {
        PlayerCursor cursor = new PlayerCursor(position);
        playerCursors.put(player, cursor);

        // Display the player on the soccer field
        root3D.getChildren().add(cursor);
        root3D.getChildren().add(cursor.getBillboard());
    }

    public Player getSelectedPlayer()
    {
        return currentMatch.getHighlightedPlayer() ;
    }

    public void setSelectedPlayer(PlayerCursor selectedPlayerCursor)
    {
        removeSelectedPlayer();
        selectedPlayerCursor.setMaterial(new PhongMaterial(Color.AQUA));
        currentMatch.setHighlightedPlayer(selectedPlayerCursor.getPlayer());
    }

    public void removeSelectedPlayer()
    {
        if ( currentMatch.getHighlightedPlayer() != null)
        {
            PlayerCursor cursor = playerCursors.get(currentMatch.getHighlightedPlayer());

            if (cursor != null)
            {
                cursor.setMaterial(new PhongMaterial(Color.ORANGE));
            }
        }
    }

    public boolean hasSelectedPlayer()
    {
        return (currentMatch.getHighlightedPlayer()  != null);
    }

    private void init3DView()
    {
        // Init 3D view
        pane3D = new Pane();
        root3D = new Group();
        pane3D.getChildren().add(root3D);
        scene3D.setRoot(pane3D);

        // Load soccerField object
        ObjModelImporter objModelImporter = new ObjModelImporter();

        try
        {
            URL objUrl = getClass().getClassLoader().getResource("obj/soccer.obj");
            objModelImporter.read(objUrl);
        }
        catch (ImportException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        MeshView[] meshViews = objModelImporter.getImport();
        Group soccerField = new Group(meshViews);
        root3D.getChildren().add(soccerField);

        // Init ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITESMOKE);
        root3D.getChildren().add(ambientLight);

        // Init camera
        Camera camera = new PerspectiveCamera(true);
        cameraManager = new CameraManager(camera, pane3D, root3D);
        cameraManager.resetCameraPosition();

        scene3D.setCamera(camera);
        scene3D.setFill(Color.gray(0.6));

        root3D.addEventHandler(CameraUpdateEvent.CAMERA_UPDATED, new EventHandler<CameraUpdateEvent>()
        {
            @Override
            public void handle(CameraUpdateEvent event)
            {
                playerCursors.forEach((player, cursor) -> cursor.getBillboard().update(camera));
            }
        });

        root3D.addEventHandler(PlayerSelectedEvent.PLAYER_UPDATED, new EventHandler<PlayerSelectedEvent>()
        {
            @Override
            public void handle(PlayerSelectedEvent event)
            {
                if (event.getPlayerCursor().getPlayer().getCurrentInfo() != null)
                {
                    setSelectedPlayer(event.getPlayerCursor());
                    mainController.higlightPlayer();
                }
            }
        });

        soccerField.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (hasSelectedPlayer())
                {
                    mainController.disableHighlighting();
                    pane3D.fireEvent(new PlayerSelectedEvent(playerCursors.get(currentMatch.getHighlightedPlayer() )));
                    removeSelectedPlayer();
                }
            }
        });
    }

    public static Point2D mapPosition(double x, double y)
    {
        if (x < SoccerField.MIN_WIDTH || x > SoccerField.MAX_WIDTH || y < SoccerField.MIN_HEIGHT || y > SoccerField.MAX_HEIGHT)
        {
            throw new IllegalArgumentException("These positions are outside the soccer field");
        }

        return new Point2D(x - MAX_WIDTH, y - MAX_HEIGHT);
    }

    public static Point2D mapPosition(Point2D position)
    {
        return mapPosition(position.getX(), position.getY());
    }

    public void reinitCamera()
    {
        cameraManager.resetCameraPosition();
    }
}
