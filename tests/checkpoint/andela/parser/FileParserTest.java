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
  public void testCheckingFIleExistence() throws Exception {
    FileParser fp = new FileParser("/home/chidi/Desktop/writeTo.log");

    fp.createFile("/home/chidi/Desktop/donkey.txt");

    File file = new File("/home/chidi/Desktop/donkey.txt");
    assertTrue(file.exists());
  }

}