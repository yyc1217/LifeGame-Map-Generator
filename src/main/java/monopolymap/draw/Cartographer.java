package monopolymap.draw;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;

public class Cartographer {

    private List<IDirection> directionGuides;
    private IDrawStrategy drawStrategy;
    private Phenotype<IntegerGene, Integer> data;
    
    public Cartographer(List<IDirection> directionGuides, Phenotype<IntegerGene, Integer> data,
            IDrawStrategy drawStrategy) {
        this.directionGuides = directionGuides;
        this.data = data;
        this.drawStrategy = drawStrategy;
    }

    public static CartographerBuilder builder() {
        return new CartographerBuilder();
    }

    public void setDirectionGuides(List<IDirection> directions) {
        this.directionGuides = directions; 
    }

    public void setDrawStrategy(IDrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
    
    public void setData(Phenotype<IntegerGene, Integer> data) {
        this.data = data;
    }

    public void draw() {
        this.drawStrategy.setData(this.data)
                         .drawWith(this.directionGuides);
    }


}
