package Starter_Classes;

import Entity_Attributes.Animates;
import Entity_Attributes.Entity_I;
import Starter_Classes.Action;

public class Animation extends Action {
    public Animation(Entity_I entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            Animates entity = (Animates) this.getEntity();
            scheduler.scheduleEvent( this.getEntity(), entity.createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)), entity.getAnimationPeriod());
        }
    }



}
