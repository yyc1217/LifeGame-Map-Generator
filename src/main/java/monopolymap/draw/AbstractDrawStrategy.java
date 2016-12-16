package monopolymap.draw;

import java.util.ArrayList;
import java.util.List;

import monopolymap.road.IDirection;

public abstract class AbstractDrawStrategy implements IDrawStrategy {

    protected List<List<Character>> toSymbols(int[][] data, List<IDirection> directionGuides) {
        
        List<List<Character>> symbols = new ArrayList<List<Character>>(data.length);
        
        for (int row = 0; row < data.length; row++) {
            
            symbols.add(new ArrayList<Character>(data[row].length));
            
            for (int column = 0; column < data[row].length; column++) {
                int index = data[row][column];
                IDirection direction = directionGuides.get(index);
                symbols.get(row).add(direction.getSymbol());
            }
        }
        
        return symbols;
    }
}
