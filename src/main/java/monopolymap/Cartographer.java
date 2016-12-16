package monopolymap;

import java.util.List;

public class Cartographer {

    private List<IDirection> directionGuides;
    
    private IDrawStrategy drawStrategy;
    
    public static CartographerBuilder builder() {
        return new CartographerBuilder();
    }

    public void setDirectionGuides(List<IDirection> directions) {
        this.directionGuides = directions; 
    }

    public void setDrawStrategy(IDrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }

    public void draw() {
        drawStrategy.drawWith(directionGuides);
    }
}
