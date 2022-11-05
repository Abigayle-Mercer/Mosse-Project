public abstract class Acitivites implements Entity_I{


    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }

    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
