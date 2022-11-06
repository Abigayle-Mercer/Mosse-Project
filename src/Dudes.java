import processing.core.PImage;

import java.util.List;

public abstract class Dudes extends Move implements Animates, Transformable{

    private final double animationPeriod;

    private final int resourceLimit;
    public int resourceCount;
    public Dudes(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(kind, id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }


    public int getResourceLimit() {
        return resourceLimit;
    }

    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied( newPos) && world.getOccupancyCell(newPos).getKind() != EntityKind.STUMP) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied( newPos) && world.getOccupancyCell(newPos).getKind() != EntityKind.STUMP) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = Functions.createDudeNotFull(this.getId(), this.getPosition(), this.getActionPeriod(), this.animationPeriod, this.getResourceLimit(), this.getImages());

        world.removeEntity(this, scheduler);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);

    }

//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        return super.moveTo(world, target, scheduler);
//    }
}
