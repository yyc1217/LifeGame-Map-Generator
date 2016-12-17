package monopolymap.output;

import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.IDirection;
import monopolymap.road.Road;

public class ConsoleOutputStrategy extends AbstractOutputStrategy implements IOutputStrategy {

    private int[][] data;

    @Override
    public IOutputStrategy setData(Phenotype<IntegerGene, Integer> data) {
        this.data = Road.toArray(data.getGenotype());
        return this;
    }
    
    @Override
    public void with(List<IDirection> directionGuides) {
    
        List<List<Character>> symbols = this.toSymbols(data, directionGuides);
        
        for(List<Character> rows : symbols) {
            System.out.println(rows);
        }
    }
}
