package monopolymap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;

public interface IOutputStrategy {
    void with(List<IDirection> directionGuides);
    IOutputStrategy setData(Phenotype<IntegerGene, Integer> data);
}