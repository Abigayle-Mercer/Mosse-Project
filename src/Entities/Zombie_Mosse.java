package Entities;

import Entity_Attributes.*;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Starter_Classes.*;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Zombie_Mosse extends Move implements Animates, Transformable {
    private final double animationPeriod;
    private int health;


    public Zombie_Mosse(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.health = health;

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int i) {
        health = i;
    }



    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }



    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy ps = new AStarPathingStrategy();
        List<Point> path = ps.computePath(this.getPosition(), destPos, (Point p) -> (((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == STUMP.class))),
                Move::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        return path.get(0);
    }

    @Override
    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            if (target instanceof Ninja_Mosse) {
                Ninja_Mosse ninja_mosse = (Ninja_Mosse) target;
                ninja_mosse.setHealth(ninja_mosse.getHealth() - 1);
            }
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {

            world.removeEntity(this, scheduler);
            return false;
        }
        return true;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Ninja_Mosse.class)));


        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
