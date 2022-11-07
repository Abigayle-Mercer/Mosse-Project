import processing.core.PImage;

import java.util.List;




public abstract class Plant extends Activities implements Transformable, Animates {


    static final double TREE_ANIMATION_MAX = 0.600;
    static final double TREE_ANIMATION_MIN = 0.050;
    static final double TREE_ACTION_MAX = 1.400;
    static final double TREE_ACTION_MIN = 1.000;


    static final int TREE_HEALTH_MAX = 3;
    static final int TREE_HEALTH_MIN = 1;

    private int health;
    private final int healthLimit;

    private final double animationPeriod;


    public double getAnimationPeriod() { return animationPeriod;}







    public Plant(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(kind, id, position, images, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
        this.animationPeriod = animationPeriod;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int i) {
        health = i;
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore, EntityKind kind) {
        if (this.health <= 0) {
            Entity_I stump = new STUMP(EntityKind.STUMP, Functions.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this, scheduler);

            world.addEntity(stump);

            return true;
        }
        return false;
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.executeActivity(world, imageStore, scheduler);
    }

}