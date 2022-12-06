package Starter_Classes;

import Entity_Attributes.Activities;
import Starter_Classes.Action;

public class Activity extends Action {
    public Activity(Activities entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        Activities active = (Activities) this.getEntity();
        active.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
    }



}
