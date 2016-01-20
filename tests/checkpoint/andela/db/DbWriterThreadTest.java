package checkpoint.andela.db;

import checkpoint.andela.parser.*;
import org.junit.*;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/19/16.
 */
public class DbWriterThreadTest {

  @Test
  public void testThreadActivityString() throws Exception {
    BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(10);
    BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(10);
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    /*
    DbWriterThread dbWriterThread = new DbWriterThread(logbuffer,fileToDbBuffer,fields);
    System.out.println(dbWriterThread.threadActivityString("WYUID-2994"));
    //can't be exactly the same due to changing seconds
    assertTrue(dbWriterThread.threadActivityString("WYUID-2994").equals("Database Writer thread (Jan 19, 2016 6:43:19 AM) wrote UNIQUE-ID -> WYUID-2994 to buffer."));
    */
  }


}