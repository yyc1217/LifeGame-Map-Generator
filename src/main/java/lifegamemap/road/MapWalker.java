package lifegamemap.road;

import java.util.Arrays;

import java.util.List;
import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manipulate {@link Map} data. 
 * @author Yeh-Yung
 *
 */
public class MapWalker {

    private static final Logger logger = LoggerFactory.getLogger(MapWalker.class);

    private Map map;
    private List<IDirection> directionGuides;
    private int rows;
    private int columns;
    
    public MapWalker(Genotype<IntegerGene> genotype, List<IDirection> directions) {
        this(new Map(genotype), directions);
    }
    
    public MapWalker(Map map, List<IDirection> directions) {
        this(map, directions, map.getDirectionIndexes().length, map.getDirectionIndexes()[0].length);
    }
    
    public MapWalker(Genotype<IntegerGene> genotype, List<IDirection> directions, int rows, int columns) {
        this(new Map(genotype), directions, rows, columns);
    }
    
    public MapWalker(Map map, List<IDirection> directions, int rows, int columns) {
        this.map = map;
        this.directionGuides = directions;
        this.rows = rows;
        this.columns = columns;
    }
    
    public Map getRoad() {
        return this.map;
    }
    
    public List<IDirection> getDirections() {
        return this.directionGuides;
    }
    
    /**
     * Calculate maximum depth of walked path.
     * @return
     */
    public Long getMaxDepth() {
        
        int[][] directionIndexes = map.getDirectionIndexes();
        
        Long sum =  Arrays.stream(directionIndexes)
                .mapToLong(x -> Arrays.stream(x)
                        .filter(index -> Map.WALKED.equals(index) || Map.DESTINATION.equals(index))
                        .count())
                .sum();
        
        return sum;
    }
    
    /**
     * Maximum depth in theory.
     * @return
     */
    private Integer expectedMaxDepth() {
        return this.rows * this.columns;
    }
    
    /**
     * Determine next position from the direction of position which {@code currentCursor} pointed.
     * @param currentCursor
     * @param depth Current traversal depth.
     */
    protected void walk(Cursor currentCursor, int depth) {
        
        int expectedMaximumDepth = expectedMaxDepth();
        
        if (depth > expectedMaximumDepth) {
            logger.error("Depth {} excced expected maximum size {}.", depth, expectedMaximumDepth);
            throw new IndexOutOfBoundsException();
        }
        
        IDirection direction = this.directionGuides.get(this.map.getDirectionIndex(currentCursor));
        Cursor nextCursor = direction.move(currentCursor);

        if (!this.map.isInBoundOf(nextCursor)) {
            return;
        }
        this.map.mark(currentCursor);
        
        if (this.map.isVisited(nextCursor)) {
            this.map.isDestination(currentCursor);
            return;
        }
 
        logger.debug("\nRoad: \n{},\nCurrent Cursor:{}, Depth:{}, Direction：{}, Next Cursor：{}", this.map, currentCursor, depth, direction.getSymbol(), nextCursor);
        
        this.walk(nextCursor, depth + 1);
    }

    public static Function<MapWalker, Long> FITNESS = mapWalker -> {
        return mapWalker.findMaxDepth(new Cursor(0, 0));
    };
    
    /**
     * Find maximum depth of path start at position {@code startAt}. It is a one-way path.
     * @param startAt
     * @return
     */
    public Long findMaxDepth(Cursor startAt) {
        this.walk(startAt, 1);
        return this.getMaxDepth();
    }
}
