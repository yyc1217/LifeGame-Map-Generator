package lifegamemap.output;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleOutputStrategy extends AbstractOutputStrategy implements IOutputStrategy {

    @Override
    public void output() {
        
        List<List<Character>> symbols = this.toSymbols(this.getData(), this.getDirectionGuides());
        String output = symbols.stream()
                .map(List::toString)
                .collect(Collectors.joining(",\n"));
        
        System.out.println("[\n" + output + "\n]");
    }
}
