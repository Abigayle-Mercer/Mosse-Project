package Entities;

import java.util.ArrayList;
import java.util.Optional;
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

abstract public class Ayaan extends Move implements Transformable{



    public Ayaan(String id, Point position, List<PImage> images, double actionPeriod) {
        super(id, position, images, actionPeriod);
    }


    // i am seggsy

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {

        Random rand = new Random();
        int x = rand.nextInt(0,4);
        int y = rand.nextInt(0, 4);
        Point newp = new Point(x, y);
        PathingStrategy ps = new SingleStepPathingStrategy();
        List<Point> path = ps.computePath(this.getPosition(), destPos, (Point p) -> (world.withinBounds(p) && ((!world.isOccupied(p)))),
                Move::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);


        if (path.size() == 0) {
            return this.getPosition();
        }
        int index = rand.nextInt(0, path.size());
        return path.get(index);
    }

    public boolean ayaanMoveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            target.setPosition(target.getPosition());
            return super.moveTo(world, target, scheduler);
        }
    }


}
