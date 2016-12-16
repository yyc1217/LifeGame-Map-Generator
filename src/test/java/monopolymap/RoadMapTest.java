package monopolymap;

import static org.junit.Assert.*;

import org.junit.Test;

import monopolymap.road.Cursor;
import monopolymap.road.Direction;
import monopolymap.road.Road;
import monopolymap.road.RoadMap;

public class RoadMapTest {

    @Test
    public void testFull() {

        int[][] paths = { { 2, 3 }, { 1, 0 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);
        
        RoadMap roadMap = new RoadMap(road, Direction.DIRECTIONS_4, 2, 2);
        int depth = roadMap.findMaxDepth(cursor);
        
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        
        int[][] paths = { { 2, 3 }, { 0, 3 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);

        RoadMap roadMap = new RoadMap(road, Direction.DIRECTIONS_4, 2, 2);
        int depth = roadMap.findMaxDepth(cursor);
        
        assertEquals(2, depth);
    }
}
