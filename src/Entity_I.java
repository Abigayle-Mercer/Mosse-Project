import java.util.Random;

public interface Entity_I {

    String getId();
    EntityKind getKind();
    void setPosition(Point position);
    Point getPosition();
    int getImageIndex();
    void setImageIndex(int i);

    // Default

    default String log(){
        return this.getId().isEmpty() ? null :
                String.format("%s %d %d %d", this.getId(), this.getPosition().getX(), this.getPosition().getY(), this.getImageIndex());
    }

    default void nextImage() {
        this.setImageIndex( this.getImageIndex() + 1);
    };

    default boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }


    default int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    default double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }




}
