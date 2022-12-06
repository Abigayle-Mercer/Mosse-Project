package Entities;

import java.util.*;

import Entity_Attributes.Entity_I;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;


public class HOUSE implements Entity_I {


    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;


    public HOUSE(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }


    public String getId() {
        return id;
    }



    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public List<PImage> getImages() { return images;}

    public int getImageIndex() {return imageIndex;}

    public void setImageIndex(int i) { imageIndex = i;}


    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {}



}

