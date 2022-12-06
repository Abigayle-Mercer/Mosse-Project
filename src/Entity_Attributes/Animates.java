package Entity_Attributes;

import Entity_Attributes.Entity_I;
import processing.core.PImage;

import java.util.List;

public interface Animates extends Entity_I {

    List<PImage> getImages();

    int getImageIndex();
    String getId();


    void setPosition(Point position);

    Point getPosition();

    void setImageIndex(int i);


    double getAnimationPeriod();

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    default Action createAnimationAction(int repeatCount) {
        return new Animation(this, null, null, repeatCount);
    }




}
