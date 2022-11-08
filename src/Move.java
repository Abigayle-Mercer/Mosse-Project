import processing.core.PImage;

import java.util.List;

public abstract class Move extends Activities {

    public Move(String id, Point position, List<PImage> images, double actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        Point nextPos = this.nextPosition(world, target.getPosition());

        if (!this.getPosition().equals(nextPos)) {
            world.moveEntity(this, scheduler, nextPos);
        }
        return false;
    }

    abstract public Point nextPosition(WorldModel world, Point destPos);

    public boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.executeActivity(world, imageStore, scheduler);
    }
}
