package monopolymap;

import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import org.jenetics.engine.Codec;
import org.jenetics.IntegerChromosome;

public class RoadMap {

    public static final int DIRECTIONS = 4;
    public static final int ROWS = 5;
    public static final int COLUMNS = 3;
    
    private int[][] road;
    
    RoadMap(Genotype<IntegerGene> genotype) {
        this.road = genotype.toSeq().stream()
        .map(IntegerChromosome.class::cast)
        .map(IntegerChromosome::toArray)
        .toArray(int[][]::new);
    }
    
    public int[][] getRoad() {
        return this.road;
    }
    
    public static final Function<RoadMap, Integer> fitness() {
        return roadMap -> 0;
    }

    public static final Codec<RoadMap, IntegerGene> codec() {
        Genotype<IntegerGene> genotype = Genotype.of(
                IntegerChromosome.of(0, DIRECTIONS - 1, COLUMNS), 
                ROWS
        );
    
        Codec<RoadMap, IntegerGene> codec = Codec.of(
                genotype,
                RoadMap::new
        );
        
        return codec;
    }
}
