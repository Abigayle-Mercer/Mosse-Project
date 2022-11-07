/**
 * An action that can be taken by an entity
 */
public final class Action {
    private final ActionKind kind;
    private final Entity_I entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;

    public Action(ActionKind kind, Entity_I entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAnimationAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            Animates entity = (Animates) this.entity;
            scheduler.scheduleEvent( this.entity, entity.createAnimationAction(Math.max(this.repeatCount - 1, 0)), entity.getAnimationPeriod());
        }
    }

    public void executeAction(EventScheduler scheduler) {
        switch (this.kind) {
            case ACTIVITY:
                executeActivityAction(scheduler);
                break;

            case ANIMATION:
                this.executeAnimationAction(scheduler);
                break;
        }
    }

    public void executeActivityAction(EventScheduler scheduler) {
        switch (this.entity.getKind()) {
            case SAPLING:
                SAPLING sapling = (SAPLING) this.entity;
                sapling.executeActivity(this.world, this.imageStore, scheduler);
                break;
            case TREE:
                TREE tree = (TREE) this.entity;
                tree.executeActivity(this.world, this.imageStore, scheduler);
                break;
            case FAIRY:
                FAIRY fairy = (FAIRY) this.entity;
                fairy.executeActivity(this.world, this.imageStore, scheduler);
                break;
            case DUDE_NOT_FULL:
                DUDE_NOT_FULL dude_not_full = (DUDE_NOT_FULL) this.entity;
                dude_not_full.executeActivity(this.world, this.imageStore, scheduler);
                break;
            case DUDE_FULL:
                DUDE_FULL dude_full = (DUDE_FULL) this.entity;
                dude_full.executeActivity(this.world, this.imageStore, scheduler);
                break;
            default:
                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
        }
    }



}
