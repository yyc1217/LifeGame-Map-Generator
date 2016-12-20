package lifegamemap.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import com.google.gson.Gson;

import lifegamemap.road.Cursor;
import lifegamemap.road.IDirection;
import lifegamemap.road.Map;
import lifegamemap.road.MapWalker;

public abstract class AbstractOutputStrategy implements IOutputStrategy {
    
    public static final Integer WILDERNESS = -1;
    public static final Character WILDERNESS_SYMBOL = '*';
    public static final Integer DESTINATION = -2;
    public static final Character DESTINATION_SYMBOL = 'E';
    
    private static final Gson GSON = new Gson();
    
    private OutputStream outputStream;
    
    private Phenotype<IntegerGene, Long> phenotype;
    private List<IDirection> directionGuides;
    
    public AbstractOutputStrategy(OutputStream target) {
        this.outputStream = target;
    }

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
        
        int[][] originalPaths = Map.toArray(this.phenotype.getGenotype());
        
        MapWalker mapWalker = new MapWalker(this.phenotype.getGenotype(), this.directionGuides);
        mapWalker.findMaxDepth(new Cursor(0, 0));
        int[][] walkedPaths = mapWalker.getRoad().getDirectionIndexes();
        
        return this.mergeMap(originalPaths, walkedPaths);
    }
    
    public int[][] mergeMap(int[][] originalPaths, int[][] walkedPaths) {
        
        int[][] mergedMap = originalPaths.clone();
        
        for (int rows = 0; rows < originalPaths.length; rows++) {
            for (int columns = 0; columns < originalPaths[0].length; columns++) {
                
                boolean isDestination = Map.DESTINATION.equals(walkedPaths[rows][columns]);
                if (isDestination) {
                    mergedMap[rows][columns] = DESTINATION;
                    continue;
                }
                
                boolean notWalked = !Map.WALKED.equals(walkedPaths[rows][columns]) ;
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
    
    /**
     * Convert {@code directionIndexes} to symbols based on {@code directionGuides}
     * @param directionIndexes
     * @param directionGuides
     * @return Character[][]
     */
    public Character[][] toSymbols(int[][] directionIndexes, List<IDirection> directionGuides) {
        
        Character[][] symbols = new Character[directionIndexes.length][];
        
        for (int row = 0; row < directionIndexes.length; row++) {
            
            symbols[row] = new Character[directionIndexes[row].length];
            
            for (int column = 0; column < directionIndexes[row].length; column++) {
                
                int directionIndex = directionIndexes[row][column];
                Character symbol = toSymbol(directionIndex);
                symbols[row][column] = symbol;
            }
        }
        
        return symbols;
    }
    
    protected Character toSymbol(int directionIndex) {
        if (DESTINATION.equals(directionIndex)) {
            return DESTINATION_SYMBOL;
        }

        if (!WILDERNESS.equals(directionIndex)) {
            IDirection direction = directionGuides.get(directionIndex);
            return direction.getSymbol();
        }

        return WILDERNESS_SYMBOL;
    }
    
    @Override
    public void output() throws IOException {
        IOUtils.copy(this.inputStream(), this.outputStream);
    }
    
    protected InputStream inputStream() {

        Character[][] symbols = this.toSymbols(this.getData(), this.getDirectionGuides());
        String input = Arrays.stream(symbols).map(GSON::toJson).collect(Collectors.joining(",\n", "[", "]"));
        
        return IOUtils.toInputStream(input, Charset.forName("UTF-8"));
    }
}
