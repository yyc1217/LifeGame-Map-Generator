package lifegamemap.road;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoadMap {

    private static final Logger logger = LoggerFactory.getLogger(RoadMap.class);

    private Road road;
    private List<IDirection> directions;
    private int rows;
    private int columns;
    
    public RoadMap(Genotype<IntegerGene> genotype, List<IDirection> directions) {
        this(new Road(genotype), directions);
    }
    
    public RoadMap(Road road, List<IDirection> directions) {
        this(road, directions, road.getPaths().length, road.getPaths()[0].length);
    }
    
    public RoadMap(Genotype<IntegerGene> genotype, List<IDirection> directions, int rows, int columns) {
        this(new Road(genotype), directions, rows, columns);
    }
    
    public RoadMap(Road road, List<IDirection> directions, int rows, int columns) {
        this.road = road;
        this.directions = directions;
        this.rows = rows;
        this.columns = columns;
    }
    
    public Road getRoad() {
        return this.road;
    }
    
    public List<IDirection> getDirections() {
        return this.directions;
    }
    
    public Long getMaxDepth() {
        
        int[][] paths = road.getPaths();
        
        Long sum =  Arrays.stream(paths)
                .mapToLong(x -> Arrays.stream(x)
                        .filter(path -> Road.WALKED.equals(path) || Road.DESTINATION.equals(path))
                        .count())
                .sum();
        
        return sum;
    }
    
    private Integer expectedMaxDepth() {
        return this.rows * this.columns;
    }
    
    protected void walk(Cursor currentCursor, int depth) {
        
        int expectedMaximumDepth = expectedMaxDepth();
        
        if (depth > expectedMaximumDepth) {
            logger.error("Depth {} excced expected maximum size {}.", depth, expectedMaximumDepth);
            throw new IndexOutOfBoundsException();
        }
        
        IDirection direction = this.directions.get(this.road.getDirectionIndex(currentCursor));
        Cursor nextCursor = direction.move(currentCursor);

        if (!this.road.isInBoundOf(nextCursor)) {
            return;
        }
        this.road.mark(currentCursor);
        
        if (this.road.isVisited(nextCursor)) {
            this.road.endAt(currentCursor);
            return;
        }
 
        logger.debug("\nRoad: \n{},\nCurrent Cursor:{}, Depth:{}, Direction：{}, Next Cursor：{}", this.road, currentCursor, depth, direction.getSymbol(), nextCursor);
        
        this.walk(nextCursor, depth + 1);
    }

    public static Function<RoadMap, Long> FITNESS = roadMap -> {
        return roadMap.findMaxDepth(new Cursor(0, 0));
    };
    
    public Long findMaxDepth(Cursor startAt) {
        this.walk(startAt, 1);
        return this.getMaxDepth();
    }
}
