import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class TREE implements Transformable, Animates{
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

    private final double actionPeriod;
    private final double animationPeriod;
    private int health;

    public Tree(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
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

    public int getHealth() {
        return health;
    }

    @Override
    public int getHealthLimit() {
        return 0;
    }

    public EntityKind getKind() {
        return kind;
    }
    public String getId() {
        return id;
    }

    public void setPosition(Point position) {
        this.position = position;
    }



    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Functions.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(stump, scheduler);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

    @Override
    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

    public PImage getCurrentImage() { // Turn into two overloaded methods

        return this.images.get(this.imageIndex % this.images.size());

    }


    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }


    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    };





}
