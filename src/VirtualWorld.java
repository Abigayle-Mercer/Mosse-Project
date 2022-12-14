import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Entities.*;
import Entity_Attributes.Dudes;
import Entity_Attributes.Entity_I;
import Entity_Attributes.Plant;
import Starter_Classes.*;
import processing.core.*;

public final class VirtualWorld extends PApplet {
    private static String[] ARGS;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;
    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private String loadFile = "world.sav";
    private long startTimeMillis = 0;
    private double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        parseCommandLine(ARGS);
        loadImages(IMAGE_LIST_FILE_NAME);
        loadWorld(loadFile, imageStore);

        view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        scheduler = new EventScheduler();
        startTimeMillis = System.currentTimeMillis();
        world.scheduleActions(scheduler, imageStore);
    }

    public void draw() {
        double appTime = (System.currentTimeMillis() - startTimeMillis) * 0.001;
        double frameTime = (appTime - scheduler.getCurrentTime())/timeScale;
        update(frameTime);
        view.drawViewport();
    }

    public void update(double frameTime){
        scheduler.updateOnTime(frameTime);
    }


    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate

    public void overlayArea(Point p)
    {
        for(int x =-2; x < 3; x++)
        {
            for( int y =-2; y<3; y++)
            {
                Point tp = new Point(p.getX()+x, p.getY()+y);
                if(world.withinBounds(tp))
                {
                    world.setBackgroundCell(tp,new Background("tmp", imageStore.getImageList("tmp")));
                }
            }
        }
    }

    public void overlayAyaan(Point p)
    {
        for(int x =-2; x < 2; x++)
        {
            for( int y =-2; y<2; y++)
            {
                Point tp = new Point(p.getX()+x, p.getY()+y);
                if(world.withinBounds(tp))
                {
                    String s = Integer.toString((x+2)+ ((y+2)*4));
                    world.setBackgroundCell(tp,new Background("ayaan"+s, imageStore.getImageList("ayaan"+s)));
                }
            }
        }
    }
    public void mousePressed() {
        Point pressed = mouseToPoint();
        System.out.println("CLICK! " + pressed.getX() + ", " + pressed.getY());

        Optional<Entity_I> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent() && entityOptional.get() instanceof Ayaan) {

            Ayaan a = (Ayaan) entityOptional.get();
            a.transform(world, scheduler, imageStore);


            overlayAyaan(pressed);

            Point position = new Point(pressed.getX() + 2, pressed.getY() + 2);
            Ninja_Mosse n = new Ninja_Mosse("01",position,imageStore.getImageList("ninja"),1,1);
            world.addEntity(n);
            n.scheduleActions(scheduler,world,imageStore);

            for(int i = 0; i < 5; i++)
            {
                Optional<Entity_I> entity_temp = world.findNearest(pressed,new ArrayList<>(List.of(FAIRY.class, Dudes.class)));
                if (entity_temp.isPresent())
                {
                    if(entity_temp.get() instanceof Dudes){
                        Dudes d = ((Dudes) entity_temp.get());
                        d.transformToZombieMosse(world,scheduler,imageStore);
                    } else if(entity_temp.get() instanceof FAIRY){
                        FAIRY f = ((FAIRY) entity_temp.get());
                        f.transformToZombieMosse(world,scheduler,imageStore);
                    }
                }
            }

//            Optional<Entity_I> fairyTarget
//            world.setBackgroundCell(pressed,new Background("tmp", imageStore.getImageList("tmp")));
//            Entity_I entity = entityOptional.get();
            // activate!
//            if (entityOptional.get() instanceof Ayaan) {
//
//
//                System.out.println(entity.getId() + ": " + entity.getClass() + " : " + plant.getHealth());
//            }
        }

    }


    private Point mouseToPoint() {
        return view.getViewport().viewportToWorld(mouseX / TILE_WIDTH, mouseY / TILE_HEIGHT);
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP -> dy -= 1;
                case DOWN -> dy += 1;
                case LEFT -> dx -= 1;
                case RIGHT -> dx += 1;
            }
            view.shiftView(dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    public void loadImages(String filename) {
        imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in,this);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadWorld(String file, ImageStore imageStore) {
        this.world = new WorldModel();
        try {
            Scanner in = new Scanner(new File(file));
            world.load( in, imageStore, createDefaultBackground(imageStore));
        } catch (FileNotFoundException e) {
            Scanner in = new Scanner(file);
            world.load( in, imageStore, createDefaultBackground(imageStore));
        }
    }

    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                default -> loadFile = arg;
            }
        }
    }
    public static void main(String[] args) { // DID I PUT THIS HERE????
        VirtualWorld.ARGS = args;
        PApplet.main(VirtualWorld.class);
    }


    public static List<String> headlessMain(String[] args, double lifetime){ // DID I PUT THIS HERE??
        VirtualWorld.ARGS = args;

        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime);

        return virtualWorld.world.log();
    }
}
