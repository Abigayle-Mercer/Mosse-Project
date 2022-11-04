public interface Transformable extends Entity_I {

    int getHealth();
    int getHealthLimit();
    boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
    boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    // Default
    default boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getKind() == EntityKind.TREE) {
            return transformTree(world, scheduler, imageStore);
        } else if (this.getKind() == EntityKind.SAPLING) {
            return transformSapling(world, scheduler, imageStore);
        } else {
            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
        }
    }





}
