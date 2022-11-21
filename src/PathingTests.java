import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PathingTests {

    public boolean withinBounds(Point pos, int numRows, int numCols) {
        return pos.getY() >= 0 && pos.getY() < numRows && pos.getX() >= 0 && pos.getX() < numCols;
    }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    @Test
    public void testSingleStep() {
        boolean[][] grid = { {true, true, true},
                           {true, true, true},
                           {true, true, true} };
        PathingStrategy ps = new SingleStepPathingStrategy();
        List<Point> path = ps.computePath(new Point(0,0), new Point(2, 2),
                (Point p ) -> (withinBounds(p, 3, 3) && grid[p.getY()][p.getX()] ),
                this::adjacent,
                PathingStrategy.CARDINAL_NEIGHBORS);
        assertEquals(List.of(new Point(1, 0)), path);
    }

    @Test
    public void testAStar1() {
        boolean[][] grid = { {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                 };
        PathingStrategy ps = new AStarPathingStrategy();
        List<Point> path = ps.computePath(new Point(1,1), new Point(3, 4),
                (Point p ) -> (withinBounds(p, 6, 6) && grid[p.getY()][p.getX()]),
                this::adjacent,
                PathingStrategy.CARDINAL_NEIGHBORS);

        assertEquals(List.of(new Point(2, 1)), path);


    }
    @Test
    public void testAStar2() {
        boolean[][] grid = { {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
        };

        List<Point> path = new ArrayList<>();
        PathingStrategy ps = new AStarPathingStrategy();
        Point start = new Point(1, 1);
        while (!start.equals(new Point(3,3))) {
            List<Point> nextPoint = ps.computePath(start, new Point(3, 4),
                    (Point p ) -> (withinBounds(p, 6, 6) && grid[p.getY()][p.getX()]),
                    this::adjacent,
                    PathingStrategy.CARDINAL_NEIGHBORS);
            path.add(nextPoint.get(0));
            start = nextPoint.get(0);
        }

        assertEquals(path.size(), 4);
    }

    @Test
    public void testAStar3() {
        boolean[][] grid = { {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
        };

        List<Point> path = new ArrayList<>();
        PathingStrategy ps = new AStarPathingStrategy();
        Point start = new Point(3, 0);
        while (!start.equals(new Point(3,3))) {
            List<Point> nextPoint = ps.computePath(start, new Point(3, 4),
                    (Point p ) -> (withinBounds(p, 6, 6) && grid[p.getY()][p.getX()]),
                    this::adjacent,
                    PathingStrategy.CARDINAL_NEIGHBORS);
            path.add(nextPoint.get(0));
            start = nextPoint.get(0);
        }

        assertEquals(path.size(), 3);
    }

    @Test
    public void testAStar4() {
        boolean[][] grid = { {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, true, true},
                {true, true, true, true, false, true},
        };

        List<Point> path = new ArrayList<>();
        PathingStrategy ps = new AStarPathingStrategy();
        Point start = new Point(0, 5);
        while (!start.equals(new Point(5,4))) {
            List<Point> nextPoint = ps.computePath(start, new Point(5, 5),
                    (Point p ) -> (withinBounds(p, 6, 6) && grid[p.getY()][p.getX()]),
                    this::adjacent,
                    PathingStrategy.CARDINAL_NEIGHBORS);
            path.add(nextPoint.get(0));
            start = nextPoint.get(0);
        }

        assertEquals(path.size(), 6);

    }

}
