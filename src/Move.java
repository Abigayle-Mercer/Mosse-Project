import processing.core.PImage;

import java.util.List;

public abstract class Move extends Activities {

    public Move(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod) {
        super(kind, id, position, images, actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(this, scheduler, nextPos);
            }
            return false;
    }

    abstract public Point nextPosition(WorldModel world, Point destPos);
}
