package com.polytech.soccerStats.controller;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.polytech.soccerStats.utils.CameraManager;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class View3DController extends DelegatedController {

    @FXML
    private SubScene scene3D;

    @FXML
    private Pane messagePane;

    private Pane pane3D;

    private Group root3D;

    private CameraManager cameraManager;

    public void init(MainController mainController) {
        this.mainController = mainController;

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
        Group soccerField = new Group(meshViews);
        root3D.getChildren().add(soccerField);

        // Init camera
        Camera camera = new PerspectiveCamera(true);
        cameraManager = new CameraManager(camera, pane3D, root3D);
        cameraManager.resetCameraPosition();

        scene3D.setCamera(camera);
    }
}
