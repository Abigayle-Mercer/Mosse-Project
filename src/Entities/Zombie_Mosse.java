package Entities;

import java.util.Random;

import Entity_Attributes.*;
import Pathing.AStarPathingStrategy;
import Pathing.PathingStrategy;
import Starter_Classes.*;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Zombie_Mosse extends Mosse {

 private int health;

public static final double ACTION_PERIOD = 1;
    public static final double ANIMATION_PERIOD = 0.1f;

    public Zombie_Mosse(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);

    }





    public boolean moveToZombie(WorldModel world, Entity_I target, EventScheduler scheduler, ImageStore imageStore) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            if (target instanceof Ninja_Mosse) {
                Ninja_Mosse ninja_mosse = (Ninja_Mosse) target;
                ninja_mosse.setHealth(ninja_mosse.getHealth() - 1);
                ninja_mosse.transform(world,scheduler,imageStore);
            }
            return true;
        } else {
            if (target instanceof Ninja_Mosse) {
                Ninja_Mosse ninja_mosse = (Ninja_Mosse) target;
                if (ninja_mosse.getHealth() > 0) {
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
        Optional<Entity_I> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Ninja_Mosse.class)));

        if (this.transform(world, scheduler, imageStore))
        {
            if (target.isPresent())
            {
                this.moveToZombie(world, target.get(), scheduler, imageStore);
            }
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
