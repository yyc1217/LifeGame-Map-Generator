package monopolymap.out;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import lifegamemap.output.OutputFormat;

public class OutputFormatTest {

    @Test
    public void testJsonList() throws IOException {
        OutputFormat format = OutputFormat.JSON_LIST;
        
        Character[][] symbols = { {'→'}, {'*'} };
        
        String expectedOutput = "[[{\"row\":0,\"column\":0,\"symbol\":\"→\"}],[{\"row\":1,\"column\":0,\"symbol\":\"*\"}]]";
        String actualOutput = IOUtils.toString(format.inputStream(symbols), Charset.forName("UTF-8"));
        
        assertEquals(expectedOutput, actualOutput);
    }

}
