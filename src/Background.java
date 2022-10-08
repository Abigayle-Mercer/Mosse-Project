import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background {
    private final List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.images = images;
    }

    public PImage getCurrentImage() { // Turn into two overloaded methods
        return this.images.get(this.imageIndex);
    }


}
