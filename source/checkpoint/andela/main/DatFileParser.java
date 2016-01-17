package checkpoint.andela.main;

import checkpoint.andela.db.*;
import checkpoint.andela.parser.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class DatFileParser {
  private static BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(10);
  private static ArrayList<String>test = new ArrayList<>();

  private static BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(10);

  private static String outputpath;
  private static String inputpath;

  public DatFileParser(String inputFilePath, String outputFilePath){
    outputpath = outputFilePath;
    inputpath = inputFilePath;
  }

  public static void main(String [] args) throws InterruptedException{
    DbWriterThread dbwriter = new DbWriterThread(logBuffer, test);
    FileParser fileParser = new FileParser(inputpath,fileToDbBuffer,logBuffer);

    ExecutorService executor = Executors.newFixedThreadPool(4);
    executor.submit(dbwriter);
    executor.submit(fileParser);
    executor.shutdown();
  }

}
