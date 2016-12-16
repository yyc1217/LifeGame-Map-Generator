package monopolymap;

import java.util.List;
import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.engine.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoadMap {

    private static final Logger logger = LoggerFactory.getLogger(RoadMap.class);

    public static final int ROWS = 3;
    public static final int COLUMNS = 3;
    public static final int MAX_DEPTH = ROWS * COLUMNS;
    
    private Road road;
    private List<IDirection> directions;
    
    RoadMap(Genotype<IntegerGene> genotype, List<IDirection> directions) {
        this.road = new Road(genotype);
        this.directions = directions;
    }
    
    public Road getRoad() {
        return this.road;
    }
    
    public List<IDirection> getDirections() {
        return this.directions;
    }
    
    protected static Integer getDepth(Road road, List<IDirection> directions, Cursor currentCursor, int depth) {
        
        if (depth > MAX_DEPTH) {
            logger.error("Depth {} excced expected maximum size {}.", depth, MAX_DEPTH);
            throw new IndexOutOfBoundsException();
        }
        
        IDirection direction = directions.get(road.getDirectionIndex(currentCursor));
        Cursor nextCursor = direction.move(currentCursor);

        if (!road.isInBoundOf(nextCursor)) {
            return depth;
        }
        
        if (road.isVisited(nextCursor)) {
            return depth;
        }
        
        road.mark(currentCursor);

        logger.debug("\nRoad: \n{},\nCurrent Cursor:{}, Depth:{}, Direction：{}, Next Cursor：{}", road, currentCursor, depth, direction.getSymbol(), nextCursor);
        
        return getDepth(road, directions, nextCursor, depth + 1);
    }

    private static Function<RoadMap, Integer> FITNESS = roadMap -> {
        return getDepth(roadMap.getRoad(), roadMap.getDirections(), new Cursor(0, 0), 1);
    };
    
    public static final Function<RoadMap, Integer> fitness() {
        return FITNESS;
    }
}
