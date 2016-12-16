package monopolymap;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;

public class Road {

    private static final int MARKED = -99;
    
    private int[][] directions;
    
    public Road(Genotype<IntegerGene> genotype) {
        this.directions = toArray(genotype);
    }

    public int getDirectionIndex(Cursor cursor) {
        return this.directions[cursor.x][cursor.y];
    }

    public boolean isInBoundOf(Cursor cursor) {
        int x = cursor.x;
        int y = cursor.y;
        
        boolean xIsInBound = 0 <= x && x < this.directions.length;
        boolean yIsInBound = xIsInBound && 0 <= y && y < this.directions[x].length;
        return xIsInBound && yIsInBound;
    }

    public boolean isVisited(Cursor cursor) {
        return this.directions[cursor.x][cursor.y] < 0;
    }

    public void mark(Cursor cursor) {
        this.directions[cursor.x][cursor.y] = MARKED;
    }
    
    public static final int[][] toArray(Genotype<IntegerGene> genotype) {
        return genotype.toSeq().stream()
        .map(IntegerChromosome.class::cast)
        .map(IntegerChromosome::toArray)
        .toArray(int[][]::new);
    }

    @Override
    public String toString() {
        return Stream.of(this.directions).map(Arrays::toString).collect(Collectors.joining("\n"));
    }
}
