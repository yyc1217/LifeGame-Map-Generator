package lifegamemap.road;

import java.util.List;
import java.util.function.Function;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.engine.Codec;
import org.jenetics.util.Factory;

public class MapWalkerCodec implements Codec<MapWalker, IntegerGene> {

    /**
     * Possible directions of every point.
     */
    private List<IDirection> directionGuides = Direction.DIRECTIONS_4;
    private int rows = 3;
    private int columns = 3;
    
    public MapWalkerCodec() {
    }
    
    public MapWalkerCodec(List<IDirection> directions) {
        this.directionGuides = directions;
    }
    
    public MapWalkerCodec(List<IDirection> directions, int rows, int columns) {
        this.directionGuides = directions;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Factory<Genotype<IntegerGene>> encoding() {
        
        Genotype<IntegerGene> genotype = Genotype.of(
                IntegerChromosome.of(0, this.directionGuides.size() - 1, this.columns), 
                this.rows
        );
        return genotype;
    }

    @Override
    public Function<Genotype<IntegerGene>, MapWalker> decoder() {
        return geno -> new MapWalker(geno, this.directionGuides, rows, columns);
    }

}
