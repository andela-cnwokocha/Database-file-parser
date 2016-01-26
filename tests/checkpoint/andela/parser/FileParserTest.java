package checkpoint.andela.parser;


import org.junit.*;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/16/16.
 */
public class FileParserTest {

  @Test
  public void testFileParseToBufferAndLog() throws Exception {
    BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(20);
    BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(20);

    FileParser fp = new FileParser("/home/chidi/Desktop/writeTo.log");
    fp.run();
    assertTrue(fileToDbBuffer.size() == 20);
    assertEquals(fileToDbBuffer.size(),20);
  }

}