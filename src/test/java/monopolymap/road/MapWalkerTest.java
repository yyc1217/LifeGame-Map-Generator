package monopolymap.road;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lifegamemap.road.Cursor;
import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;
import lifegamemap.road.Map;
import lifegamemap.road.MapWalker;

public class MapWalkerTest {

    private static final Logger logger = LoggerFactory.getLogger(MapWalkerTest.class);
    
    private List<IDirection> testDirections = Direction.DIRECTIONS_4;
    
    @Test
    public void testFull() {

        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.RIGHT, Direction.TOP }
        };
        
        int[][] indexes = DirectionHelper.toDirectionIndex(directions, testDirections);

        Map map = new Map(indexes);
        Cursor cursor = new Cursor(0, 0);
        
        MapWalker mapWalker = new MapWalker(map, this.testDirections, 2, 2);
        long depth = mapWalker.findMaxDepth(cursor);
        
        logger.debug(map.toString());
        
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        
        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.TOP, Direction.LEFT },
        };
        
        int[][] indexes = DirectionHelper.toDirectionIndex(directions, testDirections);

        Map map = new Map(indexes);
        Cursor cursor = new Cursor(0, 0);

        MapWalker mapWalker = new MapWalker(map, testDirections, 2, 2);
        long depth = mapWalker.findMaxDepth(cursor);
        
        logger.debug(map.toString());
        
        assertEquals(2, depth);
    }
}
