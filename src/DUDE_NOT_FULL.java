import processing.core.PImage;

import java.util.*;

public class DUDE_NOT_FULL extends Dudes{


    public DUDE_NOT_FULL(EntityKind kind, String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit) {
        super(kind, id, position, images, actionPeriod, animationPeriod, resourceCount, resourceLimit);
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
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
