package lifegamemap.road;

public interface IDirection {
    Cursor move(Cursor cursor);

    Character getSymbol();
}
