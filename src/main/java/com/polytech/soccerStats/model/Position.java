package com.polytech.soccerStats.model;

import javafx.geometry.Point2D;

import java.util.Date;
import java.util.Objects;

public class Position implements Comparable<Position> {
    private Date timestamp;
    private Point2D pos;
    private float heading;
    private float direction;
    private float energy;
    private float speed;

    private Player relatedPlayer;

    public Position(Date timestamp, float xPos, float yPos, float heading, float direction, float energy, float speed, Player relatedPlayer) {
        this.timestamp = timestamp;
        pos = new Point2D(xPos, yPos);
        this.heading = heading;
        this.direction = direction;
        this.energy = energy;
        this.speed = speed;
        this.relatedPlayer = relatedPlayer;
    }

    @Override
    public int compareTo(Position o) {
        return this.timestamp.compareTo(o.timestamp);
    }

    public boolean happensOn(Date currentDate) {
        return (timestamp.compareTo(currentDate) == 0);
    }

    public Player getRelatedPlayer() {
        return relatedPlayer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getPosX() {
        return pos.getX();
    }

    public double getPosY() {
        return pos.getY();
    }

    public Point2D getPos() {
        return new Point2D(pos.getX(), pos.getY());
    }

    public float getHeading() {
        return heading;
    }

    public float getDirection() {
        return direction;
    }

    public float getEnergy() {
        return energy;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(timestamp, position.timestamp) &&
                Objects.equals(pos, position.pos) &&
                Objects.equals(relatedPlayer, position.relatedPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, pos, relatedPlayer);
    }
}
