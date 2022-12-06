package Entity_Attributes;

public interface Transformable extends Entity_I {

    boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

}
