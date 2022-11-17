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

        PriorityQueue<WorldNode> OpenList = new PriorityQueue<WorldNode>(Comparator.comparing(WorldNode::getFvalue));
        HashSet<WorldNode> ClosedList = new HashSet<WorldNode>( );
        HashSet<WorldNode> ParralellOpenList = new HashSet<>();
        WorldNode s = new WorldNode(start, end, start, null);
        OpenList.add(s);
        ParralellOpenList.add(s);

        while (!OpenList.isEmpty()) {
            WorldNode current = OpenList.peek();
            if (withinReach.test(current.Position, end)) {
                break;
            }
            ParralellOpenList.remove(OpenList.peek());
            OpenList.remove(OpenList.peek());
            if (ClosedList.contains(current)) {
                // ignore it?
            }
            else {
                ClosedList.add(current);
                List<WorldNode> neighbors = potentialNeighbors.apply(current.Position).filter(canPassThrough).filter( (Point p) -> (!ClosedList.contains(p))).map(p -> new WorldNode(start, end, p, current))
                        .collect(Collectors.toList());
                for (WorldNode w : neighbors) {
                    if (!ParralellOpenList.contains(w)) {
                        OpenList.add(w);
                        ParralellOpenList.add(w);
                    }
                    else {
                        OpenList.removeIf((WorldNode p) -> (p.Position == w.Position) && (p.Fvalue > w.Fvalue));
                    }

                }

            }
        }
        WorldNode pointer = OpenList.peek();
        List<Point> path = new ArrayList<Point>();
        while (pointer.Position != start) {
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
