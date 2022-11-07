public class Activity extends Action{
    public Activity(Entity_I entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        switch (this.getEntity().getKind()) {
            case SAPLING:
                SAPLING sapling = (SAPLING) this.getEntity();
                sapling.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
                break;
            case TREE:
                TREE tree = (TREE) this.getEntity();
                tree.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
                break;
            case FAIRY:
                FAIRY fairy = (FAIRY) this.getEntity();
                fairy.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
                break;
            case DUDE_NOT_FULL:
                DUDE_NOT_FULL dude_not_full = (DUDE_NOT_FULL) this.getEntity();
                dude_not_full.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
                break;
            case DUDE_FULL:
                DUDE_FULL dude_full = (DUDE_FULL) this.getEntity();
                dude_full.executeActivity(this.getWorld(), this.getImageStore(), scheduler);
                break;
            default:
                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.getEntity().getKind()));
        }
    }




}
