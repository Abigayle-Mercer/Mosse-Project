package Entities;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import Entity_Attributes.Animates;
import Entity_Attributes.Entity_I;
import Entity_Attributes.Move;
import Entity_Attributes.Transformable;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Pathing.SingleStepPathingStrategy;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.List;

public class AyaanSpawner extends Ayaan implements Transformable{



    public AyaanSpawner(String id, Point position, List<PImage> images, double actionPeriod) {
        super(id, position, images, actionPeriod);

    }

    // i am seggsy
    // i am queeen


    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(FAIRY.class)));
        Random rand = new Random();
        double newaction = rand.nextDouble(0.1, 0.5);



        Zombie_Mosse z = new Zombie_Mosse(this.getId(), new Point(world.getNumCols()-1, world.getNumRows()-1), imageStore.getImageList("zombie"), newaction, 0.1);
        world.addEntity(z);
        z.scheduleActions(scheduler, world, imageStore);

        Ninja_Mosse n = new Ninja_Mosse(this.getId(), new Point(1, 1), imageStore.getImageList("ninja"), newaction, 0.1);
        world.addEntity(n);
        n.scheduleActions(scheduler, world, imageStore);

        ayaanMoveTo(world, target.get(), scheduler);
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());

    }


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        world.removeEntity(this, scheduler);
        return false;
    }
}

