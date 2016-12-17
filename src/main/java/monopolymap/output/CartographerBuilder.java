package monopolymap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.Direction;
import monopolymap.road.IDirection;

public class CartographerBuilder {

    private Cartographer cartographer;
    
    private List<IDirection> directions = Direction.DIRECTIONS_4;
    private IOutputStrategy outputStrategy = new ConsoleOutputStrategy();
    private Phenotype<IntegerGene, Long> result;
    
    public CartographerBuilder directions(List<IDirection> directions) {
        this.directions = directions;
        return this;
    }

    public CartographerBuilder with(IOutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
        return this;
    }

    public CartographerBuilder data(Phenotype<IntegerGene, Long> result) {
        this.result = result;
        return this;
    }
    
    public Cartographer build() {
        this.cartographer = new Cartographer(this.directions, this.result, this.outputStrategy);
        return this.cartographer;
    }
}
