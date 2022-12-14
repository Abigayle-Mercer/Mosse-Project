package Pathing;

import Starter_Classes.WorldNode;

import java.util.*;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class AStarPathingStrategy
        implements PathingStrategy
{

    public List<Starter_Classes.Point> computePath(Starter_Classes.Point start, Starter_Classes.Point end, Predicate<Starter_Classes.Point> canPassThrough, BiPredicate<Starter_Classes.Point, Starter_Classes.Point> withinReach, Function<Starter_Classes.Point, Stream<Starter_Classes.Point>> potentialNeighbors) {

        //System.out.println("Hi we got here");


        if (withinReach.test(start, end)) {
            List<Starter_Classes.Point> r = new ArrayList<>();
            return r;
        }

        PriorityQueue<WorldNode> OpenList = new PriorityQueue<WorldNode>(Comparator.comparing(WorldNode::getFvalue));
        HashSet<Starter_Classes.Point> ClosedList = new HashSet<>();
        HashSet<Starter_Classes.Point> ParralellOpenList = new HashSet<>();
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
                    .filter((Starter_Classes.Point p) -> (!ClosedList.contains(p)))
                    .map(p -> new WorldNode(start, end, p, current))
                    .toList();
            for (WorldNode w : neighbors) {
                //System.out.println("2:   Start of Starter_Classes.WorldNode neighbors for loop");

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



//        WorldNode pointer = OpenList.peek();
//        List<Starter_Classes.Point> path = new ArrayList<Starter_Classes.Point>();
//        while (!pointer.Position.equals(start)) {
//            //System.out.println("6:    start of WhilePosition.equals(start) for loop");
//            path.add(0, pointer.Position);
//            pointer = pointer.Previous;
//        }
        List<Starter_Classes.Point> returnpath = new ArrayList<Starter_Classes.Point>();
        returnpath.add(OpenList.size() > 0 ? OpenList.peek().trace().get(0) : start);


        return returnpath;





    }

    // Maybe we call a loop in the next position method to calculate the path that should be taken, and then inrement it one step
    // along the path
}
