import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class STUMP implements Transformable{

    private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;




    public Stump(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
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

    public int getHealth() {
        return health;
    }

    @Override
    public int getHealthLimit() {
        return 0;
    }

    @Override
    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

    @Override
    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    };

    public PImage getCurrentImage() { // Turn into two overloaded methods

        return this.images.get(this.imageIndex % this.images.size());

    }








}
