package Entities;

import Entity_Attributes.Dudes;
import Entity_Attributes.Entity_I;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DUDE_FULL extends Dudes {


    public DUDE_FULL(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }

    @Override
    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return super.transform(world, scheduler, imageStore);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(HOUSE.class)));

        if (fullTarget.isPresent() && moveTo( world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent( this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }
}
