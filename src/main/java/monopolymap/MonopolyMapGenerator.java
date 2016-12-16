package monopolymap;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

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

        Engine<IntegerGene, Integer> engine = Engine
                .builder(RoadMap.fitness(), RoadMap.codec())
                .build();

        EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<IntegerGene, Integer> result = engine
                .stream()
                .limit(bySteadyFitness(10))
                .limit(1)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(result);
        
        Cartographer cartographer = Cartographer.builder()
                .directions(RoadMap.DIRECTIONS)
                .with(new ConsoleDrawStrategy(result))
                .build();
        
        cartographer.draw();
    }

}
