import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DUDE_FULL extends Dudes {


    public DUDE_FULL(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(kind, id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
    }

    @Override
    public boolean moveTo(WorldModel world, Entity_I target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore, EntityKind kind) {
        return super.transform(world, scheduler, imageStore, EntityKind.DUDE_NOT_FULL);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && moveTo( world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore, EntityKind.STUMP);
        } else {
            scheduler.scheduleEvent( this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
