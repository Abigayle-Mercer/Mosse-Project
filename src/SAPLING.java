import processing.core.PImage;

import java.util.*;

public class SAPLING extends Plant{




    public SAPLING(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(kind, id, position,  images,  actionPeriod, animationPeriod, health, healthLimit);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.setHealth(this.getHealth() + 1);
        if (!transform(world, scheduler, imageStore)) {
            super.executeActivity(world, imageStore, scheduler);
        }
    }

    @Override
    public boolean transform( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (super.transform(world, scheduler, imageStore)) {

        } else if (this.getHealth() >= this.getHealthLimit()) {
            Entity tree = Functions.createTree(Functions.TREE_KEY + "_" + this.getId(), this.getPosition(), getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this, scheduler);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        switch (this.kind) {
            case DUDE_FULL:
                scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction( 0), this.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction( 0), this.getAnimationPeriod());
                break;

            case OBSTACLE:
                scheduler.scheduleEvent(this, createAnimationAction( 0), this.getAnimationPeriod());
                break;

            case FAIRY:
                scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, createAnimationAction( 0), this.getAnimationPeriod());
                break;

            case SAPLING:
                scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.actionPeriod);
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
