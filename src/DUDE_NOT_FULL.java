import processing.core.PImage;

import java.util.*;

public class DUDE_NOT_FULL extends Dudes{


    public DUDE_NOT_FULL(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }

    public void setResourceCount(int i) {
        this.resourceCount = i;
    }


    @Override
    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            this.resourceCount += 1;
            if (target instanceof TREE) {
                TREE tree = (TREE) target;
                tree.setHealth(tree.getHealth() - 1);
            }
            if (target instanceof SAPLING) {
                SAPLING sapling = (SAPLING) target;
                sapling.setHealth(sapling.getHealth() - 1);
            }
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            scheduler.unscheduleAllEvents(this);
            return super.transform(world, scheduler, imageStore);
        }

        return false;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(TREE.class, SAPLING.class)));


        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());

    }


}
