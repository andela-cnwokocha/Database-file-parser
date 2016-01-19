package checkpoint.andela.log;

import org.junit.*;
import java.util.concurrent.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/17/16.
 */
public class LoggerTest {

  @Test
  public void testWritingToPath() throws Exception {
    BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(10);

    Logger logger = new Logger(logbuffer, "/home/chidi/Desktop/jocker.txt");
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
       try{
         for(int i = 0; i < 100; i++){
           logbuffer.put("This is number "+ i);
         }
       }catch(InterruptedException ie){
         System.err.print(ie.getMessage());
       }
      }
    });
    t1.start();
    logger.run();
  }

}