package monopolymap.road;

public class Cursor {

    public int row;
    public int column;
    
    Cursor(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[row=" + row + ",column=" + column + "]";
    }
}
