package lifegamemap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import lifegamemap.road.Direction;
import lifegamemap.road.IDirection;

public class CartographerBuilder {

    private Cartographer cartographer;
    
    private List<IDirection> directions = Direction.DIRECTIONS_4;
    private OutputFormat outputFormat = OutputFormat.JSON_LIST;
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
    
    public CartographerBuilder format(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }
    
    public Cartographer build() {
        this.cartographer = new Cartographer(this.directions, this.result, this.outputStrategy, this.outputFormat);
        return this.cartographer;
    }
}
