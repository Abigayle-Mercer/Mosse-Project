import processing.core.PImage;

import java.util.*;

public class SAPLING extends Plant{

    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;


    private static final int TREE_HEALTH_MAX = 3;

    private static final int TREE_HEALTH_MIN = 1;
    private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private int resourceCount;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;

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


    public String getId() {
        return id;
    }

    public EntityKind getKind() {
        return kind;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent( this, createActivityAction( world, imageStore), this.actionPeriod);
        }
    }

    public boolean transform( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (super.transform(world, scheduler, imageStore)) {

        } else if (this.health >= this.healthLimit) {
            Entity tree = Functions.createTree(Functions.TREE_KEY + "_" + this.id, this.position, getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

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
