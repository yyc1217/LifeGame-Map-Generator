package lifegamemap.road;

import java.util.List;
import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.engine.Codec;
import org.jenetics.util.Factory;

public class RoadMapCodec implements Codec<RoadMap, IntegerGene> {

    private List<IDirection> directions = Direction.DIRECTIONS_4;
    private int rows = 3;
    private int columns = 3;
    
    public RoadMapCodec() {
    }
    
    public RoadMapCodec(List<IDirection> directions) {
        this.directions = directions;
    }
    
    public RoadMapCodec(List<IDirection> directions, int rows, int columns) {
        this.directions = directions;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Factory<Genotype<IntegerGene>> encoding() {
        
        Genotype<IntegerGene> genotype = Genotype.of(
                IntegerChromosome.of(0, this.directions.size() - 1, this.columns), 
                this.rows
        );
        return genotype;
    }

    @Override
    public Function<Genotype<IntegerGene>, RoadMap> decoder() {
        return geno -> new RoadMap(geno, this.directions, rows, columns);
    }

}
