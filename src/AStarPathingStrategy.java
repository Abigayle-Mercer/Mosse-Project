import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        //System.out.println("Hi we got here");


        if (withinReach.test(start, end)) {
            List<Point> r = new ArrayList<>();
            return r;
        }

        PriorityQueue<WorldNode> OpenList = new PriorityQueue<WorldNode>(Comparator.comparing(WorldNode::getFvalue));
        HashSet<Point> ClosedList = new HashSet<>();
        HashSet<Point> ParralellOpenList = new HashSet<>();
        WorldNode s = new WorldNode(start, end, start, null);
        OpenList.add(s);
        ParralellOpenList.add(s.Position);

        while (!OpenList.isEmpty()) {
            //System.out.println("1:   Start of OpenList.isEmpty() outer while loop");
            WorldNode current = OpenList.peek();
            if (withinReach.test(current.Position, end)) {
                break;
            }
            ParralellOpenList.remove(OpenList.peek().Position);
            OpenList.remove(OpenList.peek());
            ClosedList.add(current.Position);
            List<WorldNode> neighbors = potentialNeighbors.apply(current.Position)
                    .filter(canPassThrough)
                    .filter((Point p) -> (!ClosedList.contains(p)))
                    .map(p -> new WorldNode(start, end, p, current))
                    .toList();
            for (WorldNode w : neighbors) {
                //System.out.println("2:   Start of WorldNode neighbors for loop");

                if (!ParralellOpenList.contains(w.Position)) {
                    //System.out.println("3:   if ParralelleOpenList.contains(w) == true");
                    OpenList.add(w);
                        ParralellOpenList.add(w.Position);
                    } else {
                    //System.out.println("4:   if ParralelleOpenList.contains(w) == false");
                    if (OpenList.removeIf((WorldNode p) -> (p.Position.equals(w.Position)) && (p.Fvalue < w.Fvalue))) {
                            OpenList.add(w);
                            //System.out.println("5:    found an item to replace in OpenList");

                    }
                    }
            }


        }



        WorldNode pointer = OpenList.peek();
        List<Point> path = new ArrayList<Point>();
        while (!pointer.Position.equals(start)) {
            //System.out.println("6:    start of WhilePosition.equals(start) for loop");
            path.add(0, pointer.Position);
            pointer = pointer.Previous;
        }
        List<Point> returnpath = new ArrayList<Point>();
        returnpath.add(path.get(0));


        return returnpath;





    }

    // Maybe we call a loop in the next position method to calculate the path that should be taken, and then inrement it one step
    // along the path
}
