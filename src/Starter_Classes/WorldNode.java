package Starter_Classes;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WorldNode {
    public int EstimatedDistanceToEnd;
    public int DistanceFromStart;
    public Point Position;
    public WorldNode Previous;
    public int Fvalue;


    public WorldNode(Point start, Point end, Point current, WorldNode Previous) {
        this.EstimatedDistanceToEnd = distanceFunc(end, current);
        this.DistanceFromStart = distanceFunc(current, start);
        this.Position = current;
        this.Previous = Previous;
        this.Fvalue = distanceFunc(end, current) + distanceFunc(current, start);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }


        if (other instanceof WorldNode) {
            WorldNode worldNode = (WorldNode) other;
            return this.Position.equals(worldNode.Position);
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.Position);
    }

    public int getFvalue() {
        return Fvalue;
    }


    private int distanceFunc(Point p1, Point p2) {
        return Math.abs(p2.getY() - p1.getY()) + Math.abs(p2.getX() - p1.getX());
    }

    public List<Point> trace()
    {
        LinkedList<Point> result = new LinkedList<Point>();
        result.add(this.Position);
        WorldNode temp = this.Previous;
        WorldNode temp_prior = this;
        while(temp != null)
        {
//                if(temp_prior.getPos().adjacent(temp.getPos()))
            if(temp.Previous != null)
                result.add(0,temp.Position);
            temp = temp.Previous;
        }
        return result.stream().toList();
    }

}

