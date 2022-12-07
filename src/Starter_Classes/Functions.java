package Starter_Classes;

import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {

    public static final Random rand = new Random();

    public static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));
    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final String STUMP_KEY = "stump";
    public static final String SAPLING_KEY = "sapling";
    public static final String TREE_KEY = "tree";

    public static final String AYAAN_KEY = "ayaan";

    public static final String NINJA_KEY = "ninja";
    public static final String ZOMBIE_KEY = "zombie";


 /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */






    // don't technically need resource count ... full




//    public static PImage getCurrentImage(Object object) { // Turn into two overloaded methods
//        if (object instanceof Starter_Classes.Background background) {
//            return background.images.get(background.imageIndex);
//        } else if (object instanceof Entity entity) {
//            return entity.images.get(entity.imageIndex % entity.images.size());
//        } else {
//            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
//        }
//    }





    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */














}
