package monopolymap.out;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lifegamemap.output.AbstractOutputStrategy;
import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;
import lifegamemap.road.Map;

public class AbstractOutputStrategyTest {

    private List<IDirection> testDirections = Direction.DIRECTIONS_4;
    
    AbstractOutputStrategy strategy;
    
    @Before
    public void init() {
        
        strategy = new AbstractOutputStrategy(System.out) {
            @Override
            public void output() {
            }
        };
        
        strategy.with(testDirections);
    }
    
    @Test
    public void testMergeMap() {
        
        int[][] originalPaths = new int[][] {
            { 5, 0 },
            { 5, 0 }
        };
 
        int[][] walkedPaths = new int[][] {
            { Map.WALKED, 0 },
            { Map.WALKED, Map.DESTINATION }
        };
        
        int[][] actualMap = strategy.mergeMap(originalPaths, walkedPaths);
        
        int[][] expectedMap = new int[][] {
            { 5, AbstractOutputStrategy.WILDERNESS },
            { 5, AbstractOutputStrategy.DESTINATION }
        };
        
        assertArrayEquals(expectedMap, actualMap);
    }
    

    @Test
    public void testToSymbols() {
        
        int[][] data = new int[][] {
            { 0, 1 },
            { AbstractOutputStrategy.WILDERNESS, AbstractOutputStrategy.DESTINATION }
        };
        
        Character[][] expectedSymbols = new Character[][] {
            { testDirections.get(data[0][0]).getSymbol(), testDirections.get(data[0][1]).getSymbol() },
            { AbstractOutputStrategy.WILDERNESS_SYMBOL, AbstractOutputStrategy.DESTINATION_SYMBOL }
        };
        
        Character[][] actualSymbols = strategy.toSymbols(data, testDirections);
        
        assertThat(actualSymbols, is(expectedSymbols));
    }
}
