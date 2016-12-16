package monopolymap;

public class Direction implements IDirection {

    public static final Direction TOP = new Direction(0, 1, '↑');
    public static final Direction TOP_RIGHT = new Direction(1, 1, '↗');
    public static final Direction RIGHT = new Direction(1, 0, '→');
    public static final Direction RIGHT_BOTTOM = new Direction(1, -1, '↘');
    public static final Direction BOTTOM = new Direction(0, -1, '↓');
    public static final Direction LEFT_BOTTOM = new Direction(-1, -1, '↙');
    public static final Direction LEFT = new Direction(-1, 0, '←');
    public static final Direction LEFT_TOP = new Direction(-1, 1, '↖');
    
    private int xOffset;
    private int yOffset;
    private char symbol;
    
    public Direction(int xOffset, int yOffset, char symbol) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.symbol = symbol;
    }

    @Override
    public Cursor move(Cursor cursor) {
        int newX = cursor.x + xOffset;
        int newY = cursor.y + yOffset;
        return new Cursor(newX, newY);
    }

    public Character getSymbol() {
        return symbol;
    }
}
