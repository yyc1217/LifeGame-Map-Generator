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
        
        for (int x = 0; x < data.length; x++) {
            
            symbols.add(new ArrayList<Character>(data[x].length));
            
            for (int y = 0; y < data[x].length; y++) {
                int index = (data[x][y] + 1) % directionGuides.size();
                IDirection direction = directionGuides.get(index);
                symbols.get(x).add(direction.getSymbol());
            }
        }
        
        for(List<Character> rows : symbols) {
            System.out.println(rows);
        }
    }
}
