/**
 * An action that can be taken by an entity
 */
public abstract class Action {
    private final Entity_I entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;

    public Entity_I getEntity() {return entity;}

    public WorldModel getWorld() {return world;}

    public ImageStore getImageStore() {return imageStore;}

    public int getRepeatCount() {return repeatCount;}



    public Action(Entity_I entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }



    abstract void executeAction(EventScheduler scheduler);




}
