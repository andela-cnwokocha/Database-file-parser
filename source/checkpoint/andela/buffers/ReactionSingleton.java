package checkpoint.andela.buffers;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/26/16.
 */
public class ReactionSingleton {
  private static BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(10);
  private static BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(10);

  private static ReactionSingleton ourInstance = null;

  public ReactionSingleton() {
  }

  public BlockingQueue<HashMap<String,ArrayList<String>>> getFileToDbBuffer () {
    return fileToDbBuffer;
  }

  public BlockingQueue<String> getLogBuffer() {
    return logBuffer;
  }

  public static ReactionSingleton getInstance() {
    if (ourInstance == null){
      ourInstance = new ReactionSingleton();
    }
    return ourInstance;
  }
}
