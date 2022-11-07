public interface Transformable extends Entity_I {

    boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore, EntityKind kind);

}
