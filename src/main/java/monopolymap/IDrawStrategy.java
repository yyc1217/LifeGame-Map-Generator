package monopolymap;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

public interface IDrawStrategy {
    void drawWith(List<IDirection> directionGuides);
    IDrawStrategy setData(Phenotype<IntegerGene, Integer> data);
}
