package com.polytech.soccerStats.controller;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.Application.SoccerStats;
import com.polytech.soccerStats.event.CameraUpdateEvent;
import com.polytech.soccerStats.event.PlayerSelectedEvent;
import com.polytech.soccerStats.model.*;
import com.polytech.soccerStats.utils.CameraManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class View3DController extends DelegatedController {
    public final double HEATMAP_BAR_MAX_HEIGHT = 25.0;
    public final double HEATMAP_COLOR_ORANGE_THRESHOLD = HEATMAP_BAR_MAX_HEIGHT * 0.5;
    public final double HEATMAP_COLOR_RED_THRESHOLD = HEATMAP_BAR_MAX_HEIGHT * 0.9;

    public static final double MAX_HEIGHT = 34;
    public static final double MAX_WIDTH = 52.5;

    @FXML
    private SubScene scene3D;

    @FXML
    private Pane messagePane;

    private Pane pane3D;

    private Group root3D;

    private Group heatMap;

    private CameraManager cameraManager;

    private HashMap<Player, PlayerCursor> playerCursors = new HashMap<>();

    private SoccerField currentMatch;


    public void init(MainController mainController, SoccerStats app) {
        this.mainController = mainController;
        this.app = app;
    }

    public void load(SoccerField soccerField) throws IOException {
        init3DView();

        for (Player p : soccerField.getPlayers()) {
            // TODO : This action is not safe
            if (p.getPositions().size() > 0) {
                addPlayer(p, p.getPositions().get(0));
            }
        }

        currentMatch = soccerField;
        // Set message pane not visible
        messagePane.setVisible(false);
    }

    public void addPlayer(Player player, Position position) throws IOException {
        PlayerCursor cursor = new PlayerCursor(position);
        playerCursors.put(player, cursor);

        // Display the player on the soccer field
        root3D.getChildren().add(cursor);
        root3D.getChildren().add(cursor.getBillboard());
    }

    public Player getSelectedPlayer() {
        return currentMatch.getHighlightedPlayer();
    }

    public void setSelectedPlayer(PlayerCursor selectedPlayerCursor) {
        removeSelectedPlayer();
        selectedPlayerCursor.setMaterial(new PhongMaterial(Color.AQUA));
        currentMatch.setHighlightedPlayer(selectedPlayerCursor.getPlayer());

        // Debug only
        display3DHeatMap(selectedPlayerCursor.getPlayer());
    }

    public void removeSelectedPlayer() {
        if (currentMatch.getHighlightedPlayer() != null) {
            PlayerCursor cursor = playerCursors.get(currentMatch.getHighlightedPlayer());

            if (cursor != null) {
                cursor.setMaterial(new PhongMaterial(Color.ORANGE));
                clearHeatMap();
            }
        }
    }

    public boolean hasSelectedPlayer() {
        return (currentMatch.getHighlightedPlayer() != null);
    }

    public void display3DHeatMap(Player player) {
        player.advanceToDate(player.getPositions().get(player.getPositions().size() - 5).getTimestamp());
        HeatMap heatMapModel = player.getHeatMap();
        clearHeatMap();

        for (int i = 0; i < SoccerField.MAX_WIDTH + 1; i++) {
            for (int j = 0; j < SoccerField.MAX_HEIGHT + 1; j++) {
                // Compute the height of the bar
                double height = (heatMapModel.getHeadPoint(i, j) * HEATMAP_BAR_MAX_HEIGHT) / (heatMapModel.getMaximumValue() * 1.f);

                // Define the box and add the color
                Box b = new Box(1, height, 1);

                if (height > HEATMAP_COLOR_RED_THRESHOLD) {
                    b.setMaterial(new PhongMaterial(new Color(0.47843137f, 0.0f, 0.0f, 0.375)));
                } else if (height > HEATMAP_COLOR_ORANGE_THRESHOLD) {
                    b.setMaterial(new PhongMaterial(new Color(0.84705882f, 0.26274510f, 0.08235294f, 0.375)));
                } else {
                    b.setMaterial(new PhongMaterial(new Color(0.01f, 0.24f, 0.04f, 0.375)));
                }

                // Set bar position
                Point2D mappedPosition = mapPosition(i, j);
                b.setTranslateX(mappedPosition.getX());
                b.setTranslateZ(mappedPosition.getY());
                b.setTranslateY(-height / 2);

                heatMap.getChildren().add(b);
            }
        }
    }

    public void clearHeatMap() {
        heatMap.getChildren().clear();
    }

    private void init3DView() {
        // Init 3D view
        pane3D = new Pane();
        root3D = new Group();
        pane3D.getChildren().add(root3D);
        scene3D.setRoot(pane3D);

        // Load soccerField object
        ObjModelImporter objModelImporter = new ObjModelImporter();

        try {
            URL objUrl = getClass().getClassLoader().getResource("obj/soccer.obj");
            objModelImporter.read(objUrl);
        } catch (ImportException e) {
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

        root3D.addEventHandler(CameraUpdateEvent.CAMERA_UPDATED, new EventHandler<CameraUpdateEvent>() {
            @Override
            public void handle(CameraUpdateEvent event) {
                playerCursors.forEach((player, cursor) -> cursor.getBillboard().update(camera));
            }
        });

        root3D.addEventHandler(PlayerSelectedEvent.PLAYER_UPDATED, new EventHandler<PlayerSelectedEvent>() {
            @Override
            public void handle(PlayerSelectedEvent event) {
                if (event.getPlayerCursor().getPlayer().getCurrentInfo() != null) {
                    setSelectedPlayer(event.getPlayerCursor());
                    mainController.HiglightPlayer();
                }
            }
        });

        soccerField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (hasSelectedPlayer()) {
                    mainController.DisableHighlighting();
                    pane3D.fireEvent(new PlayerSelectedEvent(playerCursors.get(currentMatch.getHighlightedPlayer())));
                    removeSelectedPlayer();
                }
            }
        });

        // Init heatMap
        heatMap = new Group();
        heatMap.setFocusTraversable(true);
        root3D.getChildren().add(heatMap);
    }

    public static Point2D mapPosition(double x, double y) {
        if (x < SoccerField.MIN_WIDTH || x > SoccerField.MAX_WIDTH || y < SoccerField.MIN_HEIGHT || y > SoccerField.MAX_HEIGHT) {
            throw new IllegalArgumentException("These positions are outside the soccer field");
        }

        return new Point2D(x - MAX_WIDTH, y - MAX_HEIGHT);
    }

    public static Point2D mapPosition(Point2D position) {
        return mapPosition(position.getX(), position.getY());
    }

    public void reinitCamera() {
        cameraManager.resetCameraPosition();
    }
}
