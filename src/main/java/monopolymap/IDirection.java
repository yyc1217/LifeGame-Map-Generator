package monopolymap;

public interface IDirection {
    Cursor move(Cursor cursor);

    Character getSymbol();
}
