package lifegamemap.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;

import lifegamemap.road.Cursor;
import lifegamemap.road.IDirection;
import lifegamemap.road.Road;
import lifegamemap.road.RoadMap;

public abstract class AbstractOutputStrategy implements IOutputStrategy {
    
    public static final Integer WILDERNESS = -1;
    public static final Character WILDERNESS_SYMBOL = '*';
    public static final Integer DESTINATION = -2;
    public static final Character DESTINATION_SYMBOL = 'E';
    
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
        
        int[][] originalPaths = Road.toArray(this.phenotype.getGenotype());
        
        RoadMap roadMap = new RoadMap(this.phenotype.getGenotype(), this.directionGuides);
        roadMap.findMaxDepth(new Cursor(0, 0));
        int[][] walkedPaths = roadMap.getRoad().getPaths();
        
        return this.mergeMap(originalPaths, walkedPaths);
    }
    
    public int[][] mergeMap(int[][] originalPaths, int[][] walkedPaths) {
        
        int[][] mergedMap = originalPaths.clone();
        
        for (int rows = 0; rows < originalPaths.length; rows++) {
            for (int columns = 0; columns < originalPaths[0].length; columns++) {
                
                boolean isDestination = Road.DESTINATION.equals(walkedPaths[rows][columns]);
                if (isDestination) {
                    mergedMap[rows][columns] = DESTINATION;
                    continue;
                }
                
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
    
    public List<List<Character>> toSymbols(int[][] data, List<IDirection> directionGuides) {
        
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
    
    protected Character toSymbol(int index) {
        Character symbol = WILDERNESS_SYMBOL;
        
        if (DESTINATION.equals(index)) {
            symbol = DESTINATION_SYMBOL;
            return symbol;
        }

        if (!WILDERNESS.equals(index)) {
            IDirection direction = directionGuides.get(index);
            symbol = direction.getSymbol();
        }

        return symbol;
    }
    
    @Override
    public void output() throws IOException {
        IOUtils.copy(this.inputStream(), this.outputStream);
    }
    
    protected InputStream inputStream() {
        
        List<List<Character>> symbols = this.toSymbols(this.getData(), this.getDirectionGuides());
        String input = symbols.stream()
                .map(List::toString)
                .collect(Collectors.joining(",\n"));
        
        input = "[\n" + input + "\n]";
        
        return IOUtils.toInputStream(input, Charset.forName("UTF-8"));
    }
}
