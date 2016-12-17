package lifegamemap.road;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;

public class Road {

    public static final Integer WALKED = -99;
    
    private int[][] paths;
    
    public Road(int[][] directions) {
        this.paths = directions;
    }
    
    public Road(Genotype<IntegerGene> genotype) {
        this(toArray(genotype));
    }

    public int[][] getPaths() {
        return this.paths;
    }
    
    public int getDirectionIndex(Cursor cursor) {
        return this.paths[cursor.row][cursor.column];
    }

    public boolean isInBoundOf(Cursor cursor) {
        int x = cursor.row;
        int y = cursor.column;
        
        boolean xIsInBound = 0 <= x && x < this.paths.length;
        boolean yIsInBound = xIsInBound && 0 <= y && y < this.paths[x].length;
        return xIsInBound && yIsInBound;
    }

    public boolean isVisited(Cursor cursor) {
        return this.paths[cursor.row][cursor.column] < 0;
    }

    public void mark(Cursor cursor) {
        this.paths[cursor.row][cursor.column] = WALKED;
    }
    
    public static final int[][] toArray(Genotype<IntegerGene> genotype) {
        return genotype.toSeq().stream()
        .map(IntegerChromosome.class::cast)
        .map(IntegerChromosome::toArray)
        .toArray(int[][]::new);
    }

    @Override
    public String toString() {
        return Stream.of(this.paths).map(Arrays::toString).collect(Collectors.joining("\n"));
    }
}
