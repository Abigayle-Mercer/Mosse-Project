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

    }


    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy ps = new SingleStepPathingStrategy();
        List<Point> path = ps.computePath(this.getPosition(), destPos, (Point p) -> (world.withinBounds(p) && ((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == STUMP.class))),
                Move::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);


        Random rand = new Random(); //instance of random class
        int index = rand.nextInt(0, 3);
        return path.get(index);
    }


}
