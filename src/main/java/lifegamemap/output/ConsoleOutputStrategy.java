package lifegamemap.output;

/**
 * Output to console.
 * @author Yeh-Yung
 *
 */
public class ConsoleOutputStrategy extends AbstractOutputStrategy implements IOutputStrategy {

    public ConsoleOutputStrategy() {
        super(System.out);
    }
}
