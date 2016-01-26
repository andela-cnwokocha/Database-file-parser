package checkpoint.andela.log;

import checkpoint.andela.buffers.*;
import java.io.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class Logger implements Runnable{
  ReactionSingleton reactionSingleton = ReactionSingleton.getInstance();
  private BlockingQueue<String> logbuffer = reactionSingleton.getLogBuffer();
  private String writeToPath;

  public Logger(String outputPath){
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
