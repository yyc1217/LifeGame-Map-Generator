package monopolymap;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoadMapTest {

    @Test
    public void testFull() {

        int[][] paths = { { 2, 3 }, { 1, 0 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);

        int depth = RoadMap.getDepth(road, Direction.DIRECTIONS_4, cursor, 1);
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        int[][] paths = { { 2, 3 }, { 0, 3 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);

        int depth = RoadMap.getDepth(road, Direction.DIRECTIONS_4, cursor, 1);
        assertEquals(2, depth);
    }
}
