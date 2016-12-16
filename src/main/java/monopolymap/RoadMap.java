package monopolymap;

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
    
    RoadMap(Genotype<IntegerGene> genotype, List<IDirection> directions, int rows, int columns) {
        this(new Road(genotype), directions, rows, columns);
    }
    
    RoadMap(Road road, List<IDirection> directions, int rows, int columns) {
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
    
    private Integer maxDepth() {
        return this.rows * this.columns;
    }
    
    protected Integer getDepth(Cursor currentCursor, int depth) {
        
        int expectedMaximumDept = maxDepth();
        
        if (depth > expectedMaximumDept) {
            logger.error("Depth {} excced expected maximum size {}.", depth, expectedMaximumDept);
            throw new IndexOutOfBoundsException();
        }
        
        IDirection direction = this.directions.get(this.road.getDirectionIndex(currentCursor));
        Cursor nextCursor = direction.move(currentCursor);

        if (!this.road.isInBoundOf(nextCursor)) {
            return depth;
        }
        
        if (this.road.isVisited(nextCursor)) {
            return depth;
        }
        
        this.road.mark(currentCursor);

        logger.debug("\nRoad: \n{},\nCurrent Cursor:{}, Depth:{}, Direction：{}, Next Cursor：{}", this.road, currentCursor, depth, direction.getSymbol(), nextCursor);
        
        return this.getDepth(nextCursor, depth + 1);
    }

    public static Function<RoadMap, Integer> FITNESS = roadMap -> {
        return roadMap.findMaxDepth(new Cursor(0, 0));
    };
    
    protected Integer findMaxDepth(Cursor startAt) {
        return this.getDepth(startAt, 1);
    }
}
