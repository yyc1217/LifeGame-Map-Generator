package lifegamemap.road;

public interface IDirection {
    
    /**
     * Move to next position according to {@code cursor}.
     * @param cursor
     * @return {@link Cursor} Next position that {@code cursor} pointed.
     */
    Cursor move(Cursor cursor);

    Character getSymbol();
}
