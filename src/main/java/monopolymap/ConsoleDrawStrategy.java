package monopolymap;

import java.util.ArrayList;
import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

public class ConsoleDrawStrategy implements IDrawStrategy {

    private int[][] data;

    @Override
    public IDrawStrategy setData(Phenotype<IntegerGene, Integer> data) {
        this.data = Road.toArray(data.getGenotype());
        return this;
    }
    
    @Override
    public void drawWith(List<IDirection> directionGuides) {
    
        List<List<Character>> symbols = new ArrayList<List<Character>>(data.length);
        
        for (int row = 0; row < data.length; row++) {
            
            symbols.add(new ArrayList<Character>(data[row].length));
            
            for (int column = 0; column < data[row].length; column++) {
                int index = data[row][column];
                IDirection direction = directionGuides.get(index);
                symbols.get(row).add(direction.getSymbol());
            }
        }
        
        for(List<Character> rows : symbols) {
            System.out.println(rows);
        }
    }
}
