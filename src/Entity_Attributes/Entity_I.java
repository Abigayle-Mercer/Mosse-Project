package Entity_Attributes;

import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.List;
import java.util.Random;

public interface Entity_I {

    List<PImage> getImages();

    int getImageIndex();
    String getId();



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
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


}
