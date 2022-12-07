package Entities;

import java.util.Random;

import Entity_Attributes.Animates;
import Entity_Attributes.Entity_I;
import Entity_Attributes.Move;
import Entity_Attributes.Transformable;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Pathing.SingleStepPathingStrategy;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.List;

public class Ayaan extends Move{



    public Ayaan(String id, Point position, List<PImage> images, double actionPeriod) {
        super(id, position, images, actionPeriod);

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }


    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        Random rand = new Random();
        List<Point> path = PathingStrategy.CARDINAL_NEIGHBORS.apply(this.getPosition()).filter((Point p) -> (world.withinBounds(p) && ((!world.isOccupied(p))))).toList();




         //instance of random class
        return path.size() > 0? path.get(rand.nextInt(0,path.size())) : this.getPosition();
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.moveTo(world,this,scheduler);
       scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
    }


}
