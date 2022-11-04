public class Plant implements Transformable, Animates{
    @Override
    public String getId() {
        return null;
    }

    @Override
    public EntityKind getKind() {
        return null;
    }

    @Override
    public void setPosition(Point position) {

    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public int getImageIndex() {
        return 0;
    }

    @Override
    public void setImageIndex(int i) {

    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public int getHealthLimit() {
        return 0;
    }

    @Override
    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }

    @Override
    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }
}
