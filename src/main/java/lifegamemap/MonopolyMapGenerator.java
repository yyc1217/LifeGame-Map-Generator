package lifegamemap;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lifegamemap.output.Cartographer;
import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;
import lifegamemap.road.RoadMap;
import lifegamemap.road.RoadMapCodec;

public class MonopolyMapGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MonopolyMapGenerator.class);
    
    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        List<IDirection> directions = Direction.DIRECTIONS_4;
        
        Engine<IntegerGene, Long> engine = Engine
                .builder(RoadMap.FITNESS, new RoadMapCodec(directions, 5, 7))
                .build();

        EvolutionStatistics<Long, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<IntegerGene, Long> result = engine
                .stream()
                .limit(500)
                .peek(statistics)
                .collect(toBestPhenotype());

        logger.info("\n" + statistics.toString());
        logger.info("Best fitness: {}", result.getFitness());
        
        Cartographer cartographer = Cartographer.builder()
                .directions(directions)
                .data(result)
                .build();
        
        cartographer.output();
    }

}
