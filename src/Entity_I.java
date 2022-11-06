import processing.core.PImage;

import java.util.List;
import java.util.Random;

public interface Entity_I {

    List<PImage> getImages();

    int getImageIndex();
    String getId();

    EntityKind getKind();

    void setPosition(Point position);

    Point getPosition();

    void setImageIndex(int i);


    default PImage getCurrentImage() { // Turn into two overloaded methods

        return this.getImages().get(this.getImageIndex() % this.getImages().size());

    }
    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    default String log(){
        return this.getId().isEmpty() ? null :
                String.format("%s %d %d %d", this.getId(), this.getPosition().getX(), this.getPosition().getY(), this.getImageIndex());
    }

    default void nextImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    };

    default public double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    default int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        switch (this.getKind()) {
            case DUDE_FULL:
                DUDE_FULL dude_full = (DUDE_FULL) this;
                scheduler.scheduleEvent(this, dude_full.createActivityAction( world, imageStore), dude_full.getActionPeriod());
                scheduler.scheduleEvent(this, dude_full.createAnimationAction( 0), dude_full.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                DUDE_NOT_FULL dude_not_full = (DUDE_NOT_FULL) this;
                scheduler.scheduleEvent(this, dude_not_full.createActivityAction(world, imageStore), dude_not_full.getActionPeriod());
                scheduler.scheduleEvent(this, dude_not_full.createAnimationAction( 0), dude_not_full.getAnimationPeriod());
                break;

            case OBSTACLE:
                OBSTACLE obstacle = (OBSTACLE) this;
                scheduler.scheduleEvent(this, obstacle.createAnimationAction( 0), obstacle.getAnimationPeriod());
                break;

            case FAIRY:
                FAIRY fairy = (FAIRY) this;
                scheduler.scheduleEvent(this, fairy.createActivityAction( world, imageStore), fairy.getActionPeriod());
                scheduler.scheduleEvent(this, fairy.createAnimationAction( 0), fairy.getAnimationPeriod());
                break;

            case SAPLING:
                SAPLING sapling = (SAPLING) this;
                scheduler.scheduleEvent(this, sapling.createActivityAction( world, imageStore), sapling.getActionPeriod());
                scheduler.scheduleEvent(this, sapling.createAnimationAction(0), sapling.getAnimationPeriod());
                break;

            case TREE:
                TREE tree = (TREE) this;
                scheduler.scheduleEvent(this, tree.createActivityAction(world, imageStore), tree.getActionPeriod());
                scheduler.scheduleEvent(this, tree.createAnimationAction(0), tree.getAnimationPeriod());
                break;

            default:
        }
    }









}
