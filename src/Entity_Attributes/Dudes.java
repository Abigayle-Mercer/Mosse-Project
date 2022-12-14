package Entity_Attributes;

import Entities.DUDE_FULL;
import Entities.DUDE_NOT_FULL;
import Entities.STUMP;
import Entities.Zombie_Mosse;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Starter_Classes.*;
import processing.core.PImage;

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
        List<Point> path = ps.computePath(this.getPosition(), destPos, (Point p) -> (world.withinBounds(p) && ((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == STUMP.class))),
                Move::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        return path.get(0);
    }

//        Pathing.PathingStrategy ps = new Pathing.SingleStepPathingStrategy();
//        List<Starter_Classes.Point> path = ps.computePath(this.getPosition(), destPos,
//                (Starter_Classes.Point p) -> (((!world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() == Entities.STUMP.class))),
//                this::adjacent, Pathing.PathingStrategy.CARDINAL_NEIGHBORS);
//        return path.get(0);
//    }

//        List<Starter_Classes.Point> l = new ArrayList<>(Arrays.asList());
//
//        while (l.get(0) != destPos) {
//            Pathing.PathingStrategy ps = new Pathing.AStarPathingStrategy();
//            l += LinkedList<Starter_Classes.Point> path =  ps.computePath();
//        }
//        return l;
//
//        }

//
//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Starter_Classes.Point newPos = new Starter_Classes.Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Entities.STUMP.class) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Starter_Classes.Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Entities.STUMP.class) {
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

    public boolean transformToZombieMosse(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity_I moose;
        moose = new Zombie_Mosse(this.getId(), this.getPosition(), imageStore.getImageList(Functions.ZOMBIE_KEY), Zombie_Mosse.ACTION_PERIOD, Zombie_Mosse.ANIMATION_PERIOD);

        world.removeEntity(this, scheduler);

        world.addEntity(moose);
        moose.scheduleActions(scheduler, world, imageStore);
        return false;
    }



    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        return super.moveTo(world, target, scheduler);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.executeActivity(world, imageStore, scheduler);
    }

}
