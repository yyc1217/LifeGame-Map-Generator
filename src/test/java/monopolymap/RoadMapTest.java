package monopolymap;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import monopolymap.road.Cursor;
import monopolymap.road.Direction;
import monopolymap.road.IDirection;
import monopolymap.road.Road;
import monopolymap.road.RoadMap;

public class RoadMapTest {

    private List<IDirection> testDirections = Direction.DIRECTIONS_4;
    
    private int[][] toDirectionIndex(IDirection[][] directions) {
        
        int[][] indexes = new int[directions.length][directions[0].length];
        
        for (int row = 0; row < directions.length; row++) {
            for (int column = 0; column < directions[row].length; column++) {
                indexes[row][column] = testDirections.indexOf(directions[row][column]);
            }
        }
        
        return indexes;
    }
    
    @Test
    public void testFull() {

        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.RIGHT, Direction.TOP }
        };
        
        int[][] indexes = this.toDirectionIndex(directions);

        Road road = new Road(indexes);
        Cursor cursor = new Cursor(0, 0);
        
        RoadMap roadMap = new RoadMap(road, this.testDirections, 2, 2);
        int depth = roadMap.findMaxDepth(cursor);
        
        assertEquals(4, depth);
    }
    
    @Test
    public void testDeadEnd() {
        
        IDirection[][] directions = {
                { Direction.BOTTOM, Direction.LEFT },
                { Direction.TOP, Direction.LEFT },
        };
        
        int[][] indexes = this.toDirectionIndex(directions);

        Road road = new Road(indexes);
        Cursor cursor = new Cursor(0, 0);

        RoadMap roadMap = new RoadMap(road, testDirections, 2, 2);
        int depth = roadMap.findMaxDepth(cursor);
        
        assertEquals(2, depth);
    }
}
