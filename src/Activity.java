import Entity_Attributes.Activities;

public class Activity extends Action{
    public Activity(Activities entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        Activities active = (Activities) this.getEntity();
        active.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
    }



}
