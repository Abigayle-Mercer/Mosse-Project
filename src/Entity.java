import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Entity {
    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public double actionPeriod;
    public double animationPeriod;
    public int health;
    public int healthLimit;

    public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    };

    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent( this, Functions.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void executeTreeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void executeDudeNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = Functions.findNearest(world, this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (target.isEmpty() || !Functions.moveToNotFull(this, world, target.get(), scheduler) || !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = Functions.findNearest(world, this.position, new ArrayList<>(List.of(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
            transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Functions.adjacent(this.position, target.position)) {
            return true;
        } else {
            Point nextPos = Functions.nextPositionDude(this, world, target.position);

            if (!this.position.equals(nextPos)) {
                Functions.moveEntity(world, scheduler, this, nextPos);
            }
            return false;
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = Functions.createDudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

        Functions.removeEntity(world, scheduler, this);

        world.addEntity(dude);
        Functions.scheduleActions(dude, scheduler, world, imageStore);
    }


    public double getAnimationPeriod() {
        switch (this.kind) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return this.animationPeriod;
            default:
                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            Entity dude = Functions.createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

            Functions.removeEntity(world, scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            Functions.scheduleActions(dude, scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.kind == EntityKind.TREE) {
            return transformTree(world, scheduler, imageStore);
        } else if (this.kind == EntityKind.SAPLING) {
            return transformSapling(world, scheduler, imageStore);
        } else {
            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
        }
    }

    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(Functions.STUMP_KEY));

            Functions.removeEntity(world, scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }


    public boolean transformSapling( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(Functions.STUMP_KEY));

            Functions.removeEntity(world, scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            Entity tree = Functions.createTree(Functions.TREE_KEY + "_" + this.id, this.position, Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN), Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN), Functions.getIntFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

            Functions.removeEntity(world, scheduler, this);

            world.addEntity(tree);
            Functions.scheduleActions(tree, scheduler, world, imageStore);

            return true;
        }

        return false;
    }






}
