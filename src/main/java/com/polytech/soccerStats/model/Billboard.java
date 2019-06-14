package com.polytech.soccerStats.model;

import com.polytech.soccerStats.utils.Fx3DGroup;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Billboard extends Fx3DGroup {

    private PlayerCursor relatedPlayerCursor;

    public Billboard(PlayerCursor playerCursor) throws IOException {
        this.relatedPlayerCursor = playerCursor;

        // Display player billboard
        NumberFormat formatter = new DecimalFormat("00");
        String formattedPlayerTagId = formatter.format(relatedPlayerCursor.getPlayer().getTagId());

        Box billboardBox = new Box(1, 1, 0.001);
        InputStream billboardImgIS = getClass().getClassLoader().getResourceAsStream("obj/soccer/" + formattedPlayerTagId + ".png");

        if (billboardImgIS == null) {
            throw new IOException("Unable to load the billboard for the player with the number " + formattedPlayerTagId);
        }

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(billboardImgIS));
        billboardBox.setMaterial(material);

        getChildren().add(billboardBox);
        set3DTranslate(0, -2, 0);
        set3DScale(2);
    }

    public void update(Camera camera) {
        Point3D to = camera.localToScene(Point3D.ZERO);
        Point3D yDir = new Point3D(0, 1, 0);
        Point3D billboardOffset = new Point3D(0, -1, 0);

        Point3D from = relatedPlayerCursor.localToScene(billboardOffset);

        Affine affine = new Affine();
        affine.append(lookAt(from, to, yDir));
        getTransforms().setAll(affine);
    }

}
