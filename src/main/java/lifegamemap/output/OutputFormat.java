package lifegamemap.output;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

public abstract class OutputFormat {
    
    private static final Gson GSON = new Gson();
    
    public static final OutputFormat JSON_LIST = new OutputFormat() {

        @Override
        public
        InputStream inputStream(Character[][] symbols) {
            
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

    public abstract InputStream inputStream(Character[][] symbols);
}
