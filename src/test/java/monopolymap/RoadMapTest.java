package monopolymap;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoadMapTest {

    @Test
    public void testFull() {

        int[][] paths = { { 1, 2 }, { 0, 3 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);

        int depth = RoadMap.getDepth(road, cursor, 1);
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        int[][] paths = { { 1, 2 }, { 2, 3 }, };

        Road road = new Road(paths);
        Cursor cursor = new Cursor(0, 0);

        int depth = RoadMap.getDepth(road, cursor, 1);
        assertEquals(2, depth);
    }
}
