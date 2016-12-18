package lifegamemap;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

import java.io.IOException;
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
import lifegamemap.road.MapWalker;
import lifegamemap.road.MapWalkerCodec;

public class LifeGameGenerator {

    private static final Logger logger = LoggerFactory.getLogger(LifeGameGenerator.class);
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        // prepare possible directions
        List<IDirection> directionGuides = Direction.DIRECTIONS_4;
        
        // prepare genetic algorithm engine
        Engine<IntegerGene, Long> engine = Engine
                .builder(MapWalker.FITNESS, new MapWalkerCodec(directionGuides, 5, 7))
                .build();

        // prepare genetic algorithm statistic
        EvolutionStatistics<Long, ?> statistics = EvolutionStatistics.ofNumber();

        // start engine
        Phenotype<IntegerGene, Long> result = engine
                .stream()
                .limit(500)
                .peek(statistics)
                .collect(toBestPhenotype());

        logger.info("\n" + statistics.toString());
        logger.info("Best fitness: {}", result.getFitness());
        
        // output result
        Cartographer cartographer = Cartographer.builder()
                .directions(directionGuides)
                .data(result)
                .build();
        
        cartographer.output();
    }

}
