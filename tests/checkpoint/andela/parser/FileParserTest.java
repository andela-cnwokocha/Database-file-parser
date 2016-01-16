package checkpoint.andela.parser;

import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/16/16.
 */
public class FileParserTest {

  @Test
  public void testPareFile() throws Exception {
    FileParser fp = new FileParser("/home/chidi/Desktop/writeTo.log");

    fp.readFile("/", " - ","#");
    assertTrue(fp.numberOfRows() == 20);

  }

  @Test
  public void testThatDataIsProperlyMappedByRows() throws Exception {
    FileParser fp = new FileParser("/home/chidi/Desktop/writeTo.log");

    assertFalse(fp.getLogBufferSize() == 20);

    fp.readFile("/", " - ","#");

    assertTrue(fp.getLogBufferSize() == 20);
  }


}