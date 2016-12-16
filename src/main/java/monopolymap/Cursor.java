package monopolymap;

public class Cursor {

    public int x;
    public int y;
    
    Cursor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[x=" + x + ",y=" + y + "]";
    }
}
