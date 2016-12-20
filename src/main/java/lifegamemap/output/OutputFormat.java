package lifegamemap.output;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

/**
 * Decide output format.
 * @author Yeh-Yung
 *
 */
public abstract class OutputFormat {
    
    private static final Gson GSON = new Gson();
    
    public abstract InputStream inputStream(Character[][] symbols);
    
    /**
     * <code>[[{"row":0,"column":0,"symbol":"↓"},{"row":0...</code>
     */
    public static final OutputFormat JSON_LIST = new OutputFormat() {

        @Override
        public InputStream inputStream(Character[][] symbols) {
            
            Symbol[][] output = new Symbol[symbols.length][];
            
            for (int row = 0; row < symbols.length; row++) {
                
                output[row] = new Symbol[symbols[row].length];
                
                for (int column = 0; column < symbols[row].length; column++) {
                    output[row][column] = new Symbol(row, column, symbols[row][column]);
                }
            }
            
            return IOUtils.toInputStream(GSON.toJson(output), Charset.forName("UTF-8"));
        }
    };

    /**
     * <code>[
              [↓, →, →, ↓, E, ←, *],
       </code>
     */
    public static final OutputFormat HUMAN_READABLE = new OutputFormat() {

        @Override
        public InputStream inputStream(Character[][] symbols) {
            
            String input = Stream.of(symbols)
                    .map(Arrays::toString)
                    .collect(Collectors.joining(",\n", "[\n", "\n]"));
            
            return IOUtils.toInputStream(input, Charset.forName("UTF-8"));
        }
    };
}
