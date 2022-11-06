import processing.core.PImage;

import java.util.*;

public class TREE extends Plant {





    public TREE(EntityKind kind, String id, Point position, List<PImage> images,double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(kind, id, position,  images,  actionPeriod, animationPeriod, health, healthLimit);
    }



    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!transform(world, scheduler, imageStore)) {
            super.executeActivity(world, imageStore, scheduler);
        }
    }


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
       return super.transform(world, scheduler, imageStore);
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        switch (this.kind) {
            case DUDE_FULL:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            case OBSTACLE:
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            case FAIRY:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            case SAPLING:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            case TREE:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
                break;

            default:
        }
    }



}
