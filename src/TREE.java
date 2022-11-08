import processing.core.PImage;

import java.util.*;

public class TREE extends Plant {





    public TREE(EntityKind kind, String id, Point position, List<PImage> images,double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position,  images,  actionPeriod, animationPeriod, health, healthLimit);
    }



    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (transform(world, scheduler, imageStore, EntityKind.STUMP)) {
            super.executeActivity(world, imageStore, scheduler);
        }
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore, EntityKind kind) {
       return super.transform(world, scheduler, imageStore, EntityKind.STUMP);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }



}
