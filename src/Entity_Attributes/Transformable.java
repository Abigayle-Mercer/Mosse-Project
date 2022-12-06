package Entity_Attributes;

import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.WorldModel;

public interface Transformable extends Entity_I {

    boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

}
