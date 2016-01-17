package checkpoint.andela.log;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class Logger implements Runnable{
  private BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(20);
  private String writeToPath;

  public Logger(BlockingQueue<String> logbuffer, String outputPath){
    this.logbuffer = logbuffer;
    this.writeToPath = outputPath;
  }

  public void run() {
    while(true){
      try{
        logActivity();
      }catch(IOException | InterruptedException ie){
        System.err.println(ie.getMessage());
      }
    }
  }

  private void logActivity() throws IOException,InterruptedException{
      try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.writeToPath, true)))) {
        String activity = logbuffer.take();
        out.println(activity);
      }catch (IOException e) {
        System.err.println(e);
      }
  }

}
