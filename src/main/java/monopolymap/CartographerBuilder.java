package monopolymap;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.engine.Engine.Builder;

public class CartographerBuilder {

    private Cartographer cartographer;
    
    CartographerBuilder() {
        this.cartographer = new Cartographer();
    }
    
    public CartographerBuilder directions(List<IDirection> directions) {
        this.cartographer.setDirectionGuides(directions);
        return this;
    }

    public CartographerBuilder with(IDrawStrategy drawStrategy) {
        this.cartographer.setDrawStrategy(drawStrategy);
        return this;
    }

    public Cartographer build() {
        return this.cartographer;
    }
}
