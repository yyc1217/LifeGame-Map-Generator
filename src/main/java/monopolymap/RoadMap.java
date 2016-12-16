package monopolymap;

import java.util.Arrays;
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
    
    public static final List<IDirection> DIRECTIONS = Arrays.asList(
            Direction.TOP,
            Direction.RIGHT,
            Direction.BOTTOM,
            Direction.LEFT
    );
    
    public static final int ROWS = 5;
    public static final int COLUMNS = 3;
    
    private Road road;
    
    RoadMap(Genotype<IntegerGene> genotype) {
        this.road = new Road(genotype);
    }
    
    public Road getRoad() {
        return this.road;
    }
    
    private static Integer getDepth(Road road, Cursor currentCursor, int depth) {
        
        logger.debug("Road: \n{},\nCursor: {}, depth: {}", road, currentCursor, depth);
        
        if (depth > ROWS * COLUMNS) {
            logger.error("深度 {} 超出預期大小 {}", depth, ROWS * COLUMNS);
            throw new IndexOutOfBoundsException();
        }
        
        IDirection direction = DIRECTIONS.get(road.getDirectionIndex(currentCursor));
        
        Cursor nextCursor = direction.move(currentCursor);
        
        // 超出範圍
        if (!road.isInBoundOf(nextCursor)) {
            return depth;
        }
        
        // 已訪過
        if (road.isVisited(nextCursor)) {
            return depth;
        }
        
        road.mark(currentCursor);

        return getDepth(road, nextCursor, depth + 1);
    }

    private static Function<RoadMap, Integer> FITNESS = roadMap -> {
        return getDepth(roadMap.getRoad(), new Cursor(0, 0), 0);
    };
    
    public static final Function<RoadMap, Integer> fitness() {
        return FITNESS;
    }

    public static final Codec<RoadMap, IntegerGene> codec() {
        Genotype<IntegerGene> genotype = Genotype.of(
                IntegerChromosome.of(0, DIRECTIONS.size() - 1, COLUMNS), 
                ROWS
        );
    
        Codec<RoadMap, IntegerGene> codec = Codec.of(
                genotype,
                RoadMap::new
        );

        return codec;
    }
}
