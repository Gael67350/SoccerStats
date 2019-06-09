package com.polytech.soccerStats.controller;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.PlayerCursor;
import com.polytech.soccerStats.model.Position;
import com.polytech.soccerStats.model.SoccerField;
import com.polytech.soccerStats.utils.CameraManager;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MeshView;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class View3DController extends DelegatedController {

    public static final double MAX_HEIGHT = 34;
    public static final double MAX_WIDTH = 52.25;

    @FXML
    private SubScene scene3D;

    @FXML
    private Pane messagePane;

    private Pane pane3D;

    private Group root3D;

    private CameraManager cameraManager;

    private HashMap<Player, PlayerCursor> playerCursors = new HashMap<>();

    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    public void addPlayer(Player player, Position position) {
        PlayerCursor cursor = new PlayerCursor(position);
        playerCursors.put(player, cursor);

        // Display the player on the soccer field
        root3D.getChildren().add(cursor);
    }

    public void init3DView() {
        // Set message pane not visible
        messagePane.setVisible(false);

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
        root3D.getChildren().add(new Group(meshViews));

        // Init camera
        Camera camera = new PerspectiveCamera(true);
        cameraManager = new CameraManager(camera, pane3D, root3D);
        cameraManager.resetCameraPosition();

        scene3D.setCamera(camera);

        // Only for debug purpose
        Player p = new Player(5);
        Position pos = new Position(new Date(), 52.25f, 34.f, (float) Math.PI / 4.f, 0, 0, 0, p);
        addPlayer(p, pos);
    }

    public static Point2D mapPosition(double x, double y) {
        if (x < SoccerField.MIN_WIDTH || x > SoccerField.MAX_WIDTH || y < SoccerField.MIN_HEIGHT || y > SoccerField.MAX_HEIGHT) {
            throw new IllegalArgumentException("These positions are outside the soccer field");
        }

        return new Point2D(x - MAX_WIDTH, y - MAX_HEIGHT);
    }
}
