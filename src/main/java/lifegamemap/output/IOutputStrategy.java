package lifegamemap.output;

import java.io.IOException;
import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import lifegamemap.road.IDirection;

public interface IOutputStrategy {
    IOutputStrategy with(List<IDirection> directionGuides);
    IOutputStrategy setPhenotype(Phenotype<IntegerGene, Long> data);
    void output() throws IOException;
}
