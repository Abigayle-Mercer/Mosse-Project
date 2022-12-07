package Entities;

import java.util.Random;

import Entity_Attributes.*;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import Starter_Classes.WorldModel;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ninja_Mosse extends Mosse {



    public Ninja_Mosse(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public boolean moveToNinja(WorldModel world, Entity_I target, EventScheduler scheduler,ImageStore imageStore) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            if (target instanceof Zombie_Mosse) {
                Zombie_Mosse zombieMosse = (Zombie_Mosse) target;
                zombieMosse.setHealth(zombieMosse.getHealth() - 1);
//                zombieMosse.transform(world,scheduler,imageStore);
            }
            return true;
        } else {
            if (target instanceof Zombie_Mosse) {
                Zombie_Mosse zombieMosse = (Zombie_Mosse) target;
                if (zombieMosse.getHealth() > 0) {
                    return super.moveTo(world, target, scheduler);
                } else {
                    return false;
                }
            }
        }
        return false;
    }



    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Zombie_Mosse.class)));
        //this.transform(world,scheduler,imageStore);

        if (this.transform(world, scheduler, imageStore))
        {
            if (target.isPresent())
            {
                this.moveToNinja(world, target.get(), scheduler,imageStore);
            }
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
