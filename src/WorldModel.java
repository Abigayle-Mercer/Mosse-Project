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

    public int numRows;
    public int numCols;
    public Background[][] background;
    public Entity[][] occupancy;
    public Set<Entity> entities;

    public WorldModel() {

    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log() {
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = entity.log();
            if (log != null) list.add(log);
        }
        return list;
    }



    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
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
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
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
                        this.occupancy = new Entity[this.numRows][this.numCols];
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
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied( pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }



    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0 && pos.x < this.numCols;
    }

    public void scheduleActions(EventScheduler scheduler, ImageStore imageStore) {
        for (Entity entity : this.entities) {
            entity.scheduleActions( scheduler, this, imageStore);
        }
    }

    public Optional<Entity> findNearest(Point pos, List<EntityKind> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (EntityKind kind : kinds) {
            for (Entity entity : this.entities) {
                if (entity.kind == kind) {
                    ofType.add(entity);
                }
            }
        }

        return nearestEntity(ofType, pos);
    }

    public void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = Functions.createSapling(id, pt, imageStore.getImageList(Functions.SAPLING_KEY), health);
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    public void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = Functions.createDudeNotFull(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]), imageStore.getImageList(DUDE_KEY));
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Entity entity = createFairy(id, pt, Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]), imageStore.getImageList(FAIRY_KEY));
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    public void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Entity entity = Functions.createTree(id, pt, Double.parseDouble(properties[TREE_ACTION_PERIOD]), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), imageStore.getImageList(Functions.TREE_KEY));
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    public void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Entity entity = createObstacle(id, pt, Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]), imageStore.getImageList(OBSTACLE_KEY));
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    public void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            Entity entity = createHouse(id, pt, imageStore.getImageList(HOUSE_KEY));
            entity.tryAddEntity(this);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    public void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Entity entity = Functions.createStump(id, pt, imageStore.getImageList(Functions.STUMP_KEY));
            entity.tryAddEntity(this);
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
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.position = new Point(-1, -1);
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

    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.position)) {
            this.setOccupancyCell(entity.position, entity);
            this.entities.add(entity);
        }
    }

    public static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }

    public Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
    }

    public Entity createHouse(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) { // Entity?
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.position, pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }



    public int distanceSquared(Point p1,Point p2) { // STAY
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

}