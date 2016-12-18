package monopolymap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lifegamemap.output.AbstractOutputStrategy;
import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;
import lifegamemap.road.Road;

public class AbstractOutputStrategyTest {

    private List<IDirection> testDirections = Direction.DIRECTIONS_4;
    
    AbstractOutputStrategy strategy;
    
    @Before
    public void init() {
        
        strategy = new AbstractOutputStrategy(System.out) {
            @Override
            public void output() {
            }

            @Override
            protected InputStream inputStream() {
                return null;
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
            { Road.WALKED, 0 },
            { Road.WALKED, 0 }
        };
        
        int[][] actualMap = strategy.mergeMap(originalPaths, walkedPaths);
        
        int[][] expectedMap = new int[][] {
            { 5, AbstractOutputStrategy.WILDERNESS },
            { 5, AbstractOutputStrategy.WILDERNESS }
        };
        
        assertArrayEquals(expectedMap, actualMap);
    }

    @Test
    public void testToSymbols() {
        
        int[][] data = new int[][] {
            { 0, 1 },
            { AbstractOutputStrategy.WILDERNESS, testDirections.size() -1 }
        };
        
        List<List<Character>> expectedSymbols = new ArrayList<List<Character>>(2);
        List<Character> row1 = Arrays.asList(
                testDirections.get(data[0][0]).getSymbol(), 
                testDirections.get(data[0][1]).getSymbol()
        );
        expectedSymbols.add(row1);
        
        List<Character> row2 = Arrays.asList(
                AbstractOutputStrategy.WILDERNESS_SYMBOL,
                testDirections.get(data[1][1]).getSymbol()
        );
        expectedSymbols.add(row2);
        
        List<List<Character>> actualSymbols = strategy.toSymbols(data, testDirections);
        
        assertThat(actualSymbols, is(expectedSymbols));
    }
}
