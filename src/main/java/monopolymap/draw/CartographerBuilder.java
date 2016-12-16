package monopolymap.draw;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.Direction;
import monopolymap.road.IDirection;

public class CartographerBuilder {

    private Cartographer cartographer;
    
    private List<IDirection> directions = Direction.DIRECTIONS_4;
    private IDrawStrategy drawStrategy = new ConsoleDrawStrategy();
    private Phenotype<IntegerGene, Integer> result;
    
    public CartographerBuilder directions(List<IDirection> directions) {
        this.directions = directions;
        return this;
    }

    public CartographerBuilder with(IDrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
        return this;
    }

    public CartographerBuilder data(Phenotype<IntegerGene, Integer> result) {
        this.result = result;
        return this;
    }
    
    public Cartographer build() {
        this.cartographer = new Cartographer(this.directions, this.result, this.drawStrategy);
        return this.cartographer;
    }
}
