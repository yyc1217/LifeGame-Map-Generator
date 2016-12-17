package monopolymap;

import java.util.List;

import lifegamemap.road.IDirection;

public class DirectionHelper {
    
    public static int[][] toDirectionIndex(IDirection[][] directions, List<IDirection> testDirections) {

        int[][] indexes = new int[directions.length][directions[0].length];

        for (int row = 0; row < directions.length; row++) {
            for (int column = 0; column < directions[row].length; column++) {
                indexes[row][column] = testDirections.indexOf(directions[row][column]);
            }
        }

        return indexes;
    }
}
