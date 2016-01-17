package checkpoint.andela.parser;

import checkpoint.andela.buffers.*;
import checkpoint.andela.db.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/16/16.
 *
 * This class is the class which parses values from a file to a Buffer, that it. The file to be parsed has properties like the row delimiter, and column delimiter. In this class, the default row delimiter is //.-
 */
public class FileParser implements Runnable{

  private String filepath;
  private HashedArray row = new HashedArray();
  private BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer;
  private BlockingQueue<String> logBuffer;

  public FileParser(String filetoberead, BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer,BlockingQueue<String> logbuffer) {
    this.filepath = filetoberead;
    this.fileToDbBuffer = fileToDbBuffer;
    this.logBuffer = logbuffer;
  }

  @Override
  public void run() {
    Path filepath = Paths.get(this.filepath);
    try{
      try(BufferedReader reader = Files.newBufferedReader(filepath, StandardCharsets.ISO_8859_1)){
        String line;
        while((line = reader.readLine()) != null){
          if(line.startsWith("#")){;
          }else if(line.startsWith("/")){
            String uniqueid = row.getUniqueId();
            //Add to buffers
            logBuffer.put(threadActivityString(uniqueid));
            fileToDbBuffer.put(this.row.getBufferRow());
          }else {
            processLine(line," - ");
          }
        }
      }
    }catch(IOException | InterruptedException ie){
      ie.getMessage();
    }
  }

  public String threadActivityString(String uniqueid) {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    String myTime = DateFormat.getTimeInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    String myDate = DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    return "FileParser thread ("+myDate+" "+myTime+") wrote UNIQUE ID "+uniqueid+" to buffer.\n";
  }

  private void processLine(String line, String delimiter) {
    String[] rowColumn = line.split(delimiter);
    row.addToBufferRow(rowColumn[0].trim(),rowColumn[1].trim());
  }


}
