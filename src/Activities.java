import processing.core.PImage;

import java.util.List;



public abstract class Activities implements Entity_I{

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;

    private final double actionPeriod;


    public Activities(String id, Point position, List<PImage> images, double actionPeriod) {

        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
    }



    public String getId() {
        return id;
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

    public double getActionPeriod() {return actionPeriod;}




    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
    }




    }
