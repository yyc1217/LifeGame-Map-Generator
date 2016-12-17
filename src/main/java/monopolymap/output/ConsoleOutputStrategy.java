package monopolymap.output;

import java.util.List;
import java.util.stream.Collectors;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;
import monopolymap.road.Road;

public class ConsoleOutputStrategy extends AbstractOutputStrategy implements IOutputStrategy {

    private int[][] data;
    private List<IDirection> directionGuides;

    @Override
    public IOutputStrategy setData(Phenotype<IntegerGene, Long> data) {
        this.data = Road.toArray(data.getGenotype());
        return this;
    }
    
    @Override
    public IOutputStrategy with(List<IDirection> directionGuides) {
        this.directionGuides = directionGuides;
        return this;
    }

    @Override
    public void output() {
        
        List<List<Character>> symbols = this.toSymbols(data, directionGuides);
        String output = symbols.stream()
                .map(List::toString)
                .collect(Collectors.joining(",\n"));
        
        System.out.println("[\n" + output + "\n]");
    }
}
