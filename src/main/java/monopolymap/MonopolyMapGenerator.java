package monopolymap;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionStatistics;

public class MonopolyMapGenerator {

    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        List<IDirection> directions = Direction.DIRECTIONS_8;
        
        Engine<IntegerGene, Integer> engine = Engine
                .builder(RoadMap.FITNESS, new RoadMapCodec(directions))
                .build();

        EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<IntegerGene, Integer> result = engine
                .stream()
                .limit(100)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(result);
        
        Cartographer cartographer = Cartographer.builder()
                .directions(directions)
                .data(result)
                .build();
        
        cartographer.draw();
    }

}
