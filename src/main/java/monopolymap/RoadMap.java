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
    public static final int MAX_DEPTH = ROWS * COLUMNS;
    
    private Road road;
    
    RoadMap(Road road) {
        this.road = road;
    }
    
    RoadMap(Genotype<IntegerGene> genotype) {
        this.road = new Road(genotype);
    }
    
    public Road getRoad() {
        return this.road;
    }
    
    protected static Integer getDepth(Road road, Cursor currentCursor, int depth) {
        
        if (depth > MAX_DEPTH) {
            logger.error("深度 {} 超出預期大小 {}", depth, MAX_DEPTH);
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

        logger.debug("\n路徑: \n{},\n目前指標:{}, 深度:{}, 方向：{}, 下一個指標：{}", road, currentCursor, depth, direction.getSymbol(), nextCursor);
        
        return getDepth(road, nextCursor, depth + 1);
    }

    private static Function<RoadMap, Integer> FITNESS = roadMap -> {
        return getDepth(roadMap.getRoad(), new Cursor(0, 0), 1);
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
