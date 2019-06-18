package com.polytech.soccerStats.tests;

import com.polytech.soccerStats.controller.View3DController;
import com.polytech.soccerStats.model.SoccerField;
import javafx.geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;

public class View3DTest {
    @Test(expected = IllegalArgumentException.class)
    public void mapPositionInvalidPosXTest() {
        Point2D pt = new Point2D(-1, 0);
        View3DController.mapPosition(pt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mapPositionInvalidPosYTest() {
        Point2D pt = new Point2D(0, -1);
        View3DController.mapPosition(pt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mapPositionInvalidPosXYTest() {
        Point2D pt = new Point2D(68.5, 106);
        View3DController.mapPosition(pt);
    }

    @Test
    public void mapPositionBottomLeftTest() {
        Point2D pt = new Point2D(SoccerField.MIN_WIDTH, SoccerField.MIN_HEIGHT);
        Point2D mapped = View3DController.mapPosition(pt);

        System.out.println("x: " + mapped.getX() + " - y: " + mapped.getY());

        Assert.assertTrue(mapped.getX() == -52.5 && mapped.getY() == -34);
    }

    @Test
    public void mapPositionBottomRightTest() {
        Point2D pt = new Point2D(SoccerField.MAX_WIDTH, SoccerField.MIN_HEIGHT);
        Point2D mapped = View3DController.mapPosition(pt);

        System.out.println("x: " + mapped.getX() + " - y: " + mapped.getY());

        Assert.assertTrue(mapped.getX() == 52.5 && mapped.getY() == -34);
    }

    @Test
    public void mapPositionTopLeftTest() {
        Point2D pt = new Point2D(SoccerField.MIN_WIDTH, SoccerField.MAX_HEIGHT);
        Point2D mapped = View3DController.mapPosition(pt);

        System.out.println("x: " + mapped.getX() + " - y: " + mapped.getY());

        Assert.assertTrue(mapped.getX() == -52.5 && mapped.getY() == 34);
    }

    @Test
    public void mapPositionTopRightTest() {
        Point2D pt = new Point2D(SoccerField.MAX_WIDTH, SoccerField.MAX_HEIGHT);
        Point2D mapped = View3DController.mapPosition(pt);

        System.out.println("x: " + mapped.getX() + " - y: " + mapped.getY());

        Assert.assertTrue(mapped.getX() == 52.5 && mapped.getY() == 34);
    }

    @Test
    public void mapPositionCenterTest() {
        Point2D pt = new Point2D(SoccerField.MAX_WIDTH / 2.f, SoccerField.MAX_HEIGHT / 2.f);
        Point2D mapped = View3DController.mapPosition(pt);

        System.out.println("x: " + mapped.getX() + " - y: " + mapped.getY());

        Assert.assertTrue(mapped.getX() == 0 && mapped.getY() == 0);
    }
}
