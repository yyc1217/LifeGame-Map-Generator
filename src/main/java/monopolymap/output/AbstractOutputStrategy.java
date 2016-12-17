package monopolymap.output;

import java.util.ArrayList;
import java.util.List;

import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import monopolymap.road.Cursor;
import monopolymap.road.IDirection;
import monopolymap.road.Road;
import monopolymap.road.RoadMap;

public abstract class AbstractOutputStrategy implements IOutputStrategy {
    
    private static final Integer WILDERNESS = -1;
    private static final Character WILDERNESS_SYMBOL = '*';
    
    private Phenotype<IntegerGene, Long> phenotype;
    protected List<IDirection> directionGuides;

    @Override
    public IOutputStrategy setPhenotype(Phenotype<IntegerGene, Long> phenotype) {
        this.phenotype = phenotype; 
        return this;
    }
    
    @Override
    public IOutputStrategy with(List<IDirection> directionGuides) {
        this.directionGuides = directionGuides;
        return this;
    }

    protected int[][] getData() {
        
        int[][] originalPaths = Road.toArray(this.phenotype.getGenotype());
        
        RoadMap roadMap = new RoadMap(this.phenotype.getGenotype(), this.directionGuides);
        roadMap.findMaxDepth(new Cursor(0, 0));
        int[][] walkedPaths = roadMap.getRoad().getPaths();
        
        return this.mergeMap(originalPaths, walkedPaths);
    }
    
    private int[][] mergeMap(int[][] originalPaths, int[][] walkedPaths) {
        
        int[][] mergedMap = originalPaths.clone();
        
        for (int rows = 0; rows < originalPaths.length; rows++) {
            for (int columns = 0; columns < originalPaths[0].length; columns++) {
                boolean notWalked = !Road.WALKED.equals(walkedPaths[rows][columns]) ;
                if (notWalked) {
                    mergedMap[rows][columns] = WILDERNESS;
                }
            }
        }

        return mergedMap;
    }

    protected List<IDirection> getDirectionGuides() {
        return this.directionGuides;
    }
    
    protected List<List<Character>> toSymbols(int[][] data, List<IDirection> directionGuides) {
        
        List<List<Character>> symbols = new ArrayList<List<Character>>(data.length);
        
        for (int row = 0; row < data.length; row++) {
            
            symbols.add(new ArrayList<Character>(data[row].length));
            
            for (int column = 0; column < data[row].length; column++) {
                
                int index = data[row][column];
                symbols.get(row).add(toSymbol(index));
            }
        }
        
        return symbols;
    }
    
    private Character toSymbol(int index) {
        Character symbol = WILDERNESS_SYMBOL;
        
        if (!WILDERNESS.equals(index)) {
            IDirection direction = directionGuides.get(index);
            symbol = direction.getSymbol();
        }
        
        return symbol;
    }
}
