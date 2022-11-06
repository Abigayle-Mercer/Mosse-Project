import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class STUMP implements Entity_I{
    private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;


    public STUMP(String id, Point position, List<PImage> images) {
        this.kind = EntityKind.STUMP;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
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

    public List<PImage> getImages() { return images;}

    public int getImageIndex() {return imageIndex;}

    public void setImageIndex(int i) { imageIndex = i;}



}
