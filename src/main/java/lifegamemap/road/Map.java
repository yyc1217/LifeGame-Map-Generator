package lifegamemap.road;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;

/**
 * Contains direction of every point and calculate walk through result.
 * @author Yeh-Yung
 *
 */
public class Map {

    public static final Integer WALKED = -99;
    public static final Integer DESTINATION = -999;
    
    private int[][] directionIndexes;
    
    /**
     * Indexes of directions based on DirectionGuides.
     * @param directionIndexes
     * @see {@link Direction#DIRECTION_4}
     * @see {@link Direction#DIRECTION_8}
     */
    public Map(int[][] directionIndexes) {
        this.directionIndexes = directionIndexes;
    }
    
    public Map(Genotype<IntegerGene> genotype) {
        this(toArray(genotype));
    }

    public int[][] getDirectionIndexes() {
        return this.directionIndexes;
    }
    
    public int getDirectionIndex(Cursor cursor) {
        return this.directionIndexes[cursor.row][cursor.column];
    }

    /**
     * The position which {@code cursor} pointed is out of {@code paths}.
     * @param cursor
     * @return boolean
     */
    public boolean isInBoundOf(Cursor cursor) {
        
        int row = cursor.row;
        int column = cursor.column;
        
        boolean xIsInBound = 0 <= row && row < this.directionIndexes.length;
        boolean yIsInBound = xIsInBound && 0 <= column && column < this.directionIndexes[row].length;
        
        return xIsInBound && yIsInBound;
    }

    /**
     * The position which {@code cursor} pointed have been visited.
     * @param cursor
     * @return
     */
    public boolean isVisited(Cursor cursor) {
        return this.directionIndexes[cursor.row][cursor.column] < 0;
    }
    
    /**
     * The position which {@code cursor} pointed is our destination.
     * @param cursor
     */
    public void isDestination(Cursor cursor) {
        this.directionIndexes[cursor.row][cursor.column] = DESTINATION;
    }
    
    /**
     * Mark the position which {@code cursor} pointed is visited.
     * @param cursor
     */
    public void mark(Cursor cursor) {
        this.directionIndexes[cursor.row][cursor.column] = WALKED;
    }

    /**
     * 
     * @param genotype
     * @return int[][]
     */
    public static final int[][] toArray(Genotype<IntegerGene> genotype) {
        return genotype.toSeq().stream()
        .map(IntegerChromosome.class::cast)
        .map(IntegerChromosome::toArray)
        .toArray(int[][]::new);
    }

    @Override
    public String toString() {
        return Stream.of(this.directionIndexes).map(Arrays::toString).collect(Collectors.joining("\n"));
    }
}
