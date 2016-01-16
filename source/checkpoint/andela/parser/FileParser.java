package checkpoint.andela.parser;

import checkpoint.andela.buffers.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/16/16.
 *
 * This class is the class which parses values from a file to a Buffer, that it. The file to be parsed has properties like the row delimiter, and column delimiter. In this class, the default row delimiter is //.-
 */
public class FileParser {

  private String filepath;
  private String rowDelimiter;
  private String delimiter;
  private int numberOfRows = 0;
  private HashedArray row = new HashedArray();

  private BlockingQueue<HashMap<String, ArrayList<String>>> fileBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(20);

  private BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(20);


  public FileParser(String filetoberead) {
    this.filepath = filetoberead;
  }

  public void setFilePath(String filepath){
    this.filepath = filepath;
  }

  public String getFilePath(){
    return this.filepath;
  }

  public void setRowDelimiter(String rowDelimiter){
    this.rowDelimiter = rowDelimiter;
  }

  public void readFile(String rowDelimiter, String delimiter, String toignore) throws IOException, InterruptedException{

    Path filepath = Paths.get(getFilePath());
    try(BufferedReader reader = Files.newBufferedReader(filepath, StandardCharsets.ISO_8859_1)){
      String line;
      while((line = reader.readLine()) != null ){
        if(line.startsWith(toignore)){
          ;
        }else if(line.startsWith(rowDelimiter)){
          String uniqueId = row.getUniqueId();
          logBuffer.put(threadActivityString(uniqueId));
          System.out.println(threadActivityString(uniqueId));
          fileBuffer.put(this.row.getBufferRow());
          System.out.println(numberOfRows++);
        }else {
          processLine(line,delimiter);
          System.out.println("reading .....");
          System.out.println(numberOfRows);
        }
      }
    }
  }
  public int numberOfRows(){
    return this.numberOfRows;
  }

  public void createFile(String filePath) {

  }

  public String threadActivityString(String uniqueid) {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    String myTime = DateFormat.getTimeInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    String myDate = DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    return "FileParser thread ("+myDate+" "+myTime+") wrote UNIQUE ID "+uniqueid+" to buffer.\n";
  }

  public void processLine(String line, String delimiter) {
    String[] rowColumn = line.split(delimiter);
    row.addToBufferRow(rowColumn[0].trim(),rowColumn[1].trim());
  }
}
