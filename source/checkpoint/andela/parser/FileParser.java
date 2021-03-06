package checkpoint.andela.parser;

import checkpoint.andela.buffers.*;
import checkpoint.andela.log.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/16/16.
 *
 * This class is the class which parses values from a file to a Buffer, that it. The file to be parsed has properties like the row delimiter, and column delimiter. In this class, the default row delimiter is //.-
 */
public class FileParser implements Runnable, ThreadActivityString{
  private String filepath;
  private ReactionArray row = new ReactionArray();
  ReactionSingleton reactionSingleton = ReactionSingleton.getInstance();
  private BlockingQueue<String> logbuffer = reactionSingleton.getLogBuffer();
  private BlockingQueue<HashMap<String,ArrayList<String>>> filetodbBuffer = reactionSingleton.getFileToDbBuffer();

  public FileParser(String filetoberead) {
    this.filepath = filetoberead;

  }

  @Override
  public void run() {
   Path filepath = Paths.get(this.filepath);
    try {
      try(BufferedReader reader = Files.newBufferedReader(filepath, StandardCharsets.ISO_8859_1)) {
        String line;
        while((line = reader.readLine()) != null) {
          if(!line.startsWith("#") && !line.startsWith("/")){
            processLine(line," - ");
          } else if(line.startsWith("/")){
            String uniqueid = row.getUniqueId();
            logbuffer.put(threadActivity(uniqueid, this));
            filetodbBuffer.put(row.getBufferRow());
          }
        }
      }
    } catch(IOException | InterruptedException ie){
    }
  }

  private void processLine(String line, String delimiter) {
    String[] rowColumn = line.split(delimiter);
    row.addToBufferRow(rowColumn[0].trim(),rowColumn[1].trim());
  }

  @Override
  public String toString(){
    return getClass().getSimpleName();
  }

}
