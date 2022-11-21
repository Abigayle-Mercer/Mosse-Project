import processing.core.PImage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Dudes extends Move implements Animates, Transformable {

    private final double animationPeriod;

    public final int resourceLimit;
    public int resourceCount;

    public Dudes(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod);
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
        PathingStrategy ps = new AStarPathingStrategy();
        List<Point> path = ps.computePath(this.getPosition(), destPos, (Point p) -> (((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == STUMP.class))),
                this::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        return path.get(0);
    }

//        PathingStrategy ps = new SingleStepPathingStrategy();
//        List<Point> path = ps.computePath(this.getPosition(), destPos,
//                (Point p) -> (((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == STUMP.class))),
//                this::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
//        return path.get(0);
//    }

//        List<Point> l = new ArrayList<>(Arrays.asList());
//
//        while (l.get(0) != destPos) {
//            PathingStrategy ps = new AStarPathingStrategy();
//            l += LinkedList<Point> path =  ps.computePath();
//        }
//        return l;
//
//        }

//
//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != STUMP.class) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != STUMP.class) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
//    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity_I dude;
        if (this instanceof DUDE_FULL) {
            dude = new DUDE_NOT_FULL(this.getId(), this.getPosition(), this.getImages(), this.getActionPeriod(), this.getAnimationPeriod(), 0, this.getResourceLimit());
        } else {
            dude = new DUDE_FULL(this.getId(), this.getPosition(), this.getImages(), this.getActionPeriod(), this.getAnimationPeriod(), 0, this.getResourceLimit());
        }

        world.removeEntity(this, scheduler);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return false;

    }

    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        return super.moveTo(world, target, scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.executeActivity(world, imageStore, scheduler);
    }

}
