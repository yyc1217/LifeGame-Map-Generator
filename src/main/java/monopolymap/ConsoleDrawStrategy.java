package monopolymap;

import java.util.ArrayList;
import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

public class ConsoleDrawStrategy implements IDrawStrategy {

    private int[][] paths;
    
    public ConsoleDrawStrategy(Phenotype<IntegerGene, Integer> result) {
        this.paths = Road.toArray(result.getGenotype());
    }

    @Override
    public void drawWith(List<IDirection> directionGuides) {
    
        List<List<Character>> symbols = new ArrayList<List<Character>>(paths.length);
        
        for (int x = 0; x < paths.length; x++) {
            
            symbols.add(new ArrayList<Character>(paths[x].length));
            
            for (int y = 0; y < paths[x].length; y++) {
                IDirection direction = directionGuides.get(paths[x][y]);
                symbols.get(x).add(direction.getSymbol());
            }
        }
        
        for(List<Character> rows : symbols) {
            System.out.println(rows);
        }
    }
}
