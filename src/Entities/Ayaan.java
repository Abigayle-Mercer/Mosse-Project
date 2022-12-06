package Entities;

import Entity_Attributes.Entity_I;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.List;

public class Ayaan implements Entity_I {
    @Override
    public List<PImage> getImages() {
        return null;
    }

    @Override
    public int getImageIndex() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setPosition(Point position) {

    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public void setImageIndex(int i) {

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }
}
