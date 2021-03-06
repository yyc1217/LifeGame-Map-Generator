package lifegamemap.output;

import java.io.IOException;
import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import lifegamemap.road.IDirection;

/**
 * Used to output walked result.
 * @author Yeh-Yung
 *
 */
public class Cartographer {

    private List<IDirection> directionGuides;
    private IOutputStrategy outputStrategy;
    private OutputFormat outputFormat;
    private Phenotype<IntegerGene, Long> data;
    
    public Cartographer(List<IDirection> directionGuides, Phenotype<IntegerGene, Long> result,
            IOutputStrategy outputStrategy, OutputFormat outputFormat) {
        this.directionGuides = directionGuides;
        this.data = result;
        this.outputStrategy = outputStrategy;
        this.outputFormat = outputFormat;
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
    
    public void setData(Phenotype<IntegerGene, Long> data) {
        this.data = data;
    }
    
    public void setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void output() throws IOException {
        this.outputStrategy.setPhenotype(this.data)
                         .with(this.directionGuides)
                         .format(this.outputFormat)
                         .output();
    }
}
