package Starter_Classes;

import Entities.*;
import Entity_Attributes.Entity_I;
import Starter_Classes.Background;
import Starter_Classes.EventScheduler;
import Starter_Classes.ImageStore;
import Starter_Classes.Point;
import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {
    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_ANIMATION_PERIOD = 0;
    private static final int OBSTACLE_NUM_PROPERTIES = 1;
    private static final String DUDE_KEY = "dude";
    private static final int DUDE_ACTION_PERIOD = 0;
    private static final int DUDE_ANIMATION_PERIOD = 1;
    private static final int DUDE_LIMIT = 2;
    private static final int DUDE_NUM_PROPERTIES = 3;

    private static final String HOUSE_KEY = "house";
    private static final int HOUSE_NUM_PROPERTIES = 0;
    private static final String FAIRY_KEY = "fairy";
    private static final int FAIRY_ANIMATION_PERIOD = 0;
    private static final int FAIRY_ACTION_PERIOD = 1;
    private static final int FAIRY_NUM_PROPERTIES = 2;
    private static final int TREE_ANIMATION_PERIOD = 0;
    private static final int TREE_ACTION_PERIOD = 1;
    private static final int TREE_HEALTH = 2;
    private static final int TREE_NUM_PROPERTIES = 3;
    private static final int PROPERTY_KEY = 0;
    private static final int PROPERTY_ID = 1;
    private static final int PROPERTY_COL = 2;
    private static final int PROPERTY_ROW = 3;

    private static final int STUMP_NUM_PROPERTIES = 0;

    private static final int ENTITY_NUM_PROPERTIES = 4;

    private static final int SAPLING_HEALTH = 0;

    private static final int SAPLING_NUM_PROPERTIES = 1;



    private int numRows;
    private int numCols;
    private Background[][] background;
    private Entity_I[][] occupancy;
    private Set<Entity_I> entities;

    public WorldModel() {

    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Set<Entity_I> getEntities() {
        return entities;
    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log() {
        List<String> list = new ArrayList<>();
        for (Entity_I entity : entities) {
            String log = entity.log();
            if (log != null) list.add(log);
        }
        return list;
    }



    public Entity_I getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    public void setOccupancyCell(Point pos, Entity_I entity) {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if (row < this.numRows) {
            int rows = Math.min(cells.length, this.numCols);
            for (int col = 0; col < rows; col++) {
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }


    public void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground) {
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while (saveFile.hasNextLine()) {
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if (line.endsWith(":")) {
                headerLine = lineCounter;
                lastHeader = line;
                switch (line) {
                    case "Backgrounds:" -> this.background = new Background[this.numRows][this.numCols];
                    case "Entities:" -> {
                        this.occupancy = new Entity_I[this.numRows][this.numCols];
                        this.entities = new HashSet<>();
                    }
                }
            } else {
                switch (lastHeader) {
                    case "Rows:" -> this.numRows = Integer.parseInt(line);
                    case "Cols:" -> this.numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> this.parseBackgroundRow(line, lineCounter - headerLine - 1, imageStore);
                    case "Entities:" -> parseEntity( line, imageStore);
                }
            }
        }
    }


    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground) {
        this.parseSaveFile(saveFile, imageStore, defaultBackground);
        if (this.background == null) {
            this.background = new Background[this.numRows][this.numCols];
            for (Background[] row : this.background)
                Arrays.fill(row, defaultBackground);
        }
        if (this.occupancy == null) {
            this.occupancy = new Entity_I[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }

    public Optional<Entity_I> getOccupant(Point pos) {
        if (this.isOccupied( pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }



    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0 && pos.getX() < this.numCols;
    }

    public void scheduleActions(EventScheduler scheduler, ImageStore imageStore) {
        for (Entity_I entity : this.entities) {
            entity.scheduleActions( scheduler, this, imageStore);
        }
    }

    public Optional<Entity_I> findNearest(Point pos, List<Class> kinds) {
        List<Entity_I> ofType = new LinkedList<>();
        for (Class kind: kinds) {
            for (Entity_I entity : this.entities) {
                if (kind.isAssignableFrom(entity.getClass()) ) {
                    ofType.add(entity);
                }
            }
        }

        return nearestEntity(ofType, pos);
    }

    public void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            SAPLING entity = new SAPLING(id, pt, imageStore.getImageList(Functions.SAPLING_KEY), health);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    public void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            DUDE_NOT_FULL entity = new DUDE_NOT_FULL(id, pt, imageStore.getImageList(DUDE_KEY), Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), 0, Integer.parseInt(properties[DUDE_LIMIT]));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            FAIRY entity = new FAIRY(id, pt, imageStore.getImageList(FAIRY_KEY), Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    public void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            TREE entity = new TREE(id, pt, imageStore.getImageList(Functions.TREE_KEY), Double.parseDouble(properties[TREE_ACTION_PERIOD]), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), 0);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    public void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            OBSTACLE entity = new OBSTACLE(id, pt, imageStore.getImageList(OBSTACLE_KEY), Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    public void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            HOUSE entity = new HOUSE(id, pt, imageStore.getImageList(HOUSE_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    public void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            STUMP entity = new STUMP(id, pt, imageStore.getImageList(Functions.STUMP_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[PROPERTY_COL]), Integer.parseInt(properties[PROPERTY_ROW]));

            properties = properties.length == ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case OBSTACLE_KEY -> parseObstacle(properties, pt, id, imageStore);
                case DUDE_KEY -> parseDude( properties, pt, id, imageStore);
                case FAIRY_KEY -> parseFairy(properties, pt, id, imageStore);
                case HOUSE_KEY -> parseHouse( properties, pt, id, imageStore);
                case Functions.TREE_KEY -> parseTree( properties, pt, id, imageStore);
                case Functions.SAPLING_KEY -> parseSapling( properties, pt, id, imageStore);
                case Functions.STUMP_KEY -> parseStump( properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity_I entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds( pos) && this.getOccupancyCell(pos) != null;
    }

    public void addEntity(Entity_I entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }



    public Optional<Entity_I> nearestEntity(List<Entity_I> entities, Point pos) { // Entity?
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity_I nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity_I other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }



    public int distanceSquared(Point p1,Point p2) { // STAY
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }

    public void moveEntity(Entity_I entity, EventScheduler scheduler, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            Optional<Entity_I> occupant = this.getOccupant(pos);
            occupant.ifPresent(target -> this.removeEntity(target, scheduler));
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity_I entity, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(entity);
        this.removeEntityAt(entity.getPosition());
    }

    public void tryAddEntity(Entity_I entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }




}