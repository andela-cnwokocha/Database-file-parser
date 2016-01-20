package checkpoint.andela.log;

import java.io.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class Logger implements Runnable{
  private BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(5);
  private String writeToPath;

  public Logger(BlockingQueue<String> logbuffer, String outputPath){
    this.logbuffer = logbuffer;
    this.writeToPath = outputPath;
  }

  public void run() {
    while(true) {
      try {
        logActivity();
      } catch(IOException | InterruptedException ie) {
      }
    }
  }

  private void logActivity() throws IOException,InterruptedException {
      try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(writeToPath, true)))) {
        String activity = logbuffer.take();
        out.println(activity);
      } catch (IOException e) {
      }
  }

}
