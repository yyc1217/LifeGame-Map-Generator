package monopolymap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;

public class Cartographer {

    private List<IDirection> directionGuides;
    private IOutputStrategy outputStrategy;
    private Phenotype<IntegerGene, Integer> data;
    
    public Cartographer(List<IDirection> directionGuides, Phenotype<IntegerGene, Integer> data,
            IOutputStrategy outputStrategy) {
        this.directionGuides = directionGuides;
        this.data = data;
        this.outputStrategy = outputStrategy;
    }

    public static CartographerBuilder builder() {
        return new CartographerBuilder();
    }

    public void setDirectionGuides(List<IDirection> directions) {
        this.directionGuides = directions; 
    }

    public void setDrawStrategy(IOutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }
    
    public void setData(Phenotype<IntegerGene, Integer> data) {
        this.data = data;
    }

    public void output() {
        this.outputStrategy.setData(this.data)
                         .with(this.directionGuides);
    }


}