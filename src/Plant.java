import processing.core.PImage;

import java.util.List;

public abstract class Plant extends Activities implements Transformable, Animates {

    private final int health;
    private final int healthLimit;


    public Plant(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod, int health, int healthLimit) {
        super(kind, id, position, images, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this, scheduler);

            world.addEntity(stump);

            return true;
        }
        return false;
    }
}