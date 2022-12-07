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

public class Ayaan extends Move implements Transformable{



    public Ayaan(String id, Point position, List<PImage> images, double actionPeriod) {
        super(id, position, images, actionPeriod);

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
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

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(FAIRY.class)));


        if (target.isEmpty() || !ayaanMoveTo(world, target.get(), scheduler)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        AyaanSpawner tempa = new AyaanSpawner(this.getId(), new Point(this.getPosition().getX()+1, this.getPosition().getY()), this.getImages(), this.getActionPeriod());
        world.addEntity(tempa);

        world.removeEntity(this, scheduler);

        tempa.scheduleActions(scheduler, world, imageStore);
        return false;
    }
}
