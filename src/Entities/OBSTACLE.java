package Entities;

import Entity_Attributes.Animates;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.*;

public class OBSTACLE implements Animates {



    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;


    private final double animationPeriod;


    public OBSTACLE(String id, Point position, List<PImage> images, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.animationPeriod = animationPeriod;
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

    public double getAnimationPeriod() {
        return animationPeriod;
    }




    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }






}
