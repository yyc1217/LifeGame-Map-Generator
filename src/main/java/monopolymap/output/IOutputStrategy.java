package monopolymap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;

public interface IOutputStrategy {
    IOutputStrategy with(List<IDirection> directionGuides);
    IOutputStrategy setPhenotype(Phenotype<IntegerGene, Long> data);
    void output();
}
