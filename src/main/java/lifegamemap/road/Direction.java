package lifegamemap.road;

import java.util.Arrays;
import java.util.List;

public class Direction implements IDirection {

    public static final Direction TOP = new Direction(-1, 0, '↑');
    public static final Direction TOP_RIGHT = new Direction(-1, 1, '↗');
    public static final Direction RIGHT = new Direction(0, 1, '→');
    public static final Direction BOTTOM_RIGHT = new Direction(1, 1, '↘');
    public static final Direction BOTTOM = new Direction(1, 0, '↓');
    public static final Direction BOTTOM_LEFT = new Direction(1, -1, '↙');
    public static final Direction LEFT = new Direction(0, -1, '←');
    public static final Direction TOP_LEFT = new Direction(-1, -1, '↖');
    
    /**
     * {@link #TOP}, {@link #RIGHT}, {@link #BOTTOM}, {@link #LEFT}
     */
    public static final List<IDirection> DIRECTIONS_4 = Arrays.asList(
            TOP,
            RIGHT,
            BOTTOM,
            LEFT
    );

    /**
     * {@link #TOP}, {@link #TOP_RIGHT}, {@link #RIGHT}, {@link #BOTTOM_RIGHT},
     * {@link #BOTTOM}, {@link #BOTTOM_LEFT}, {@link #LEFT}, {@link #TOP_LEFT}
     */
    public static final List<IDirection> DIRECTIONS_8 = Arrays.asList(
            TOP,
            TOP_RIGHT,
            RIGHT,
            BOTTOM_RIGHT,
            BOTTOM,
            BOTTOM_LEFT,
            LEFT,
            TOP_LEFT
    );
    
    private int rowOffset;
    private int columnOffset;
    private char symbol;
    
    /**
     * Create a direction.
     * @param rowOffset offset of row index.
     * @param columnOffset offset of column index.
     * @param symbol Symbol which represents direction.
     */
    public Direction(int rowOffset, int columnOffset, char symbol) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
        this.symbol = symbol;
    }

    @Override
    public Cursor move(Cursor cursor) {
        int newRow = cursor.row + rowOffset;
        int newColumn = cursor.column + columnOffset;
        return new Cursor(newRow, newColumn);
    }

    public Character getSymbol() {
        return symbol;
    }
}
