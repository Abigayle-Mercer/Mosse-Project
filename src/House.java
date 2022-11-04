import java.util.*;

import processing.core.PImage;


public class House implements Entity_I {
    private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;


    public House(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
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
        return this.imageIndex;
    }

    @Override
    public void setImageIndex(int i) {
        this.imageIndex = i;

    }

    public PImage getCurrentImage() { // Turn into two overloaded methods

        return this.images.get(this.imageIndex % this.images.size());

    }


}

