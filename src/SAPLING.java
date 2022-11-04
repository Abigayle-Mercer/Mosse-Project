import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class SAPLING implements Transformable, Animates{

    private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;

    private final int healthLimit;

    private final double actionPeriod;
    private final double animationPeriod;
    private int health;




    public Sapling(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
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

    @Override
    public int getImageIndex() {
        return 0;
    }

    @Override
    public void setImageIndex(int i) {

    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    };

    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent( this, createActivityAction( world, imageStore), this.actionPeriod);
        }
    }



    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public int getHealthLimit() {
        return 0;
    }

    @Override
    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }


    public boolean transformSapling( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this, scheduler);

            world.addEntity(stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            Entity tree = Functions.createTree(Functions.TREE_KEY + "_" + this.id, this.position, getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this, scheduler);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public PImage getCurrentImage() { // Turn into two overloaded methods

        return this.images.get(this.imageIndex % this.images.size());

    }



    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }









}
