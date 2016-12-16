package monopolymap;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

public class CartographerBuilder {

    private Cartographer cartographer;
    
    private List<IDirection> directions;
    private IDrawStrategy drawStrategy;
    private Phenotype<IntegerGene, Integer> result;
    
    CartographerBuilder() {
        this.cartographer = new Cartographer();
    }
    
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
        this.cartographer.setDirectionGuides(directions);
        this.cartographer.setData(this.result);
        this.cartographer.setDrawStrategy(this.drawStrategy);
        return this.cartographer;
    }
}
