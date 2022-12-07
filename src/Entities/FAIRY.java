package Entities;

import Entities.SAPLING;
import Entities.STUMP;
import Entity_Attributes.Animates;
import Entity_Attributes.Entity_I;
import Entity_Attributes.Move;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Starter_Classes.*;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FAIRY extends Move implements Animates {

    // this is a comment haha
    private final double animationPeriod;


    public FAIRY(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }


    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy ps = new AStarPathingStrategy();
        List<Point> path = ps.computePath(
                this.getPosition(),
                destPos,
                (Point p) -> world.withinBounds(p) && !world.isOccupied(p),
                Move::adjacent,
                PathingStrategy.CARDINAL_NEIGHBORS
        );
        return path.get(0);
    }

//
//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Starter_Classes.Point newPos = new Starter_Classes.Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Starter_Classes.Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
//    }

    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target, scheduler);
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> fairyTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(STUMP.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {

                SAPLING sapling = new SAPLING(Functions.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(Functions.SAPLING_KEY), 0);

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        super.executeActivity(world, imageStore, scheduler);
    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }

    public boolean transformToZombieMosse(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity_I moose;
        moose = new Zombie_Mosse(this.getId(), this.getPosition(), imageStore.getImageList(Functions.ZOMBIE_KEY), Zombie_Mosse.ACTION_PERIOD, Zombie_Mosse.ANIMATION_PERIOD);

        world.removeEntity(this, scheduler);

        world.addEntity(moose);
//        moose.scheduleActions(scheduler, world, imageStore);
        return false;

    }



}