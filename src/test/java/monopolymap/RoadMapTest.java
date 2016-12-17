package monopolymap;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lifegamemap.road.Cursor;
import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;
import lifegamemap.road.Road;
import lifegamemap.road.RoadMap;

public class RoadMapTest {

    private static final Logger logger = LoggerFactory.getLogger(RoadMapTest.class);
    
    private List<IDirection> testDirections = Direction.DIRECTIONS_4;
    
    @Test
    public void testFull() {

        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.RIGHT, Direction.TOP }
        };
        
        int[][] indexes = DirectionHelper.toDirectionIndex(directions, testDirections);

        Road road = new Road(indexes);
        Cursor cursor = new Cursor(0, 0);
        
        RoadMap roadMap = new RoadMap(road, this.testDirections, 2, 2);
        long depth = roadMap.findMaxDepth(cursor);
        
        logger.debug(road.toString());
        
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        
        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.TOP, Direction.LEFT },
        };
        
        int[][] indexes = DirectionHelper.toDirectionIndex(directions, testDirections);

        Road road = new Road(indexes);
        Cursor cursor = new Cursor(0, 0);

        RoadMap roadMap = new RoadMap(road, testDirections, 2, 2);
        long depth = roadMap.findMaxDepth(cursor);
        
        logger.debug(road.toString());
        
        assertEquals(2, depth);
    }
}
