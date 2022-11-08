import processing.core.PImage;

import java.util.*;

public class SAPLING extends Plant{

    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final String STUMP_KEY = "stump";
    public static final String SAPLING_KEY = "sapling";
    public static final String TREE_KEY = "tree";


    public SAPLING(EntityKind kind, String id, Point position, List<PImage> images, int health) {
        super(kind, id, position,  images,  SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
    }


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.setHealth(this.getHealth() + 1);
        if (transform(world, scheduler, imageStore, EntityKind.TREE)) {
            super.executeActivity(world, imageStore, scheduler);
        }
    }

    @Override
    public boolean transform( WorldModel world, EventScheduler scheduler, ImageStore imageStore, EntityKind kind) {
        if (this.getHealth() <= 0) {
            return super.transform(world, scheduler, imageStore, EntityKind.STUMP);

        } else if (this.getHealth() >= this.getHealthLimit()) {
            Entity_I tree = new TREE(EntityKind.TREE, Functions.TREE_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Functions.TREE_KEY), getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), 0);

            world.removeEntity(this, scheduler);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return false;
        }

        return true;
    }


    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }


}































