import processing.core.PImage;

import java.util.List;

public interface Animates extends  Entity_I{

    List<PImage> getImages();

    int getImageIndex();
    String getId();

    EntityKind getKind();

    void setPosition(Point position);

    Point getPosition();

    void setImageIndex(int i);


    double getAnimationPeriod();

    default Action createAnimationAction( int repeatCount) {
        return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
    }




}
