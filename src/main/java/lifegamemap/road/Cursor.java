package lifegamemap.road;

public class Cursor {

    public int row;
    public int column;
    
    /**
     * Create a cursor.
     * @param row index of row.
     * @param column index of column
     */
    public Cursor(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[row=" + row + ",column=" + column + "]";
    }
}
