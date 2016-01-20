package checkpoint.andela.main;

import checkpoint.andela.db.*;
import checkpoint.andela.log.*;
import checkpoint.andela.parser.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class DatFileParser {
  private static BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(10);
  private static BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(10);
  private static String outputpath;
  private static String inputpath;
  private static String[] tableColumns = {"UNIQUE-ID","TYPES","COMMON-NAME","ATOM-MAPPINGS","CANNOT-BALANCE?","COMMENT","COMMENT-INTERNAL","CREDITS","DELTAG0","EC-NUMBER","ENZYMATIC-REACTION","IN-PATHWAY","LEFT","MEMBER-SORT-FN","ORPHAN?","PHYSIOLOGICALLY-RELEVANT?","PREDECESSORS","PRIMARIES","REACTION-DIRECTION","REACTION-LIST","RIGHT","RXN-LOCATIONS","SPONTANEOUS?","STD-REDUCTION-POTENTIAL","SYNONYMS","SYSTEMATIC-NAME"};
  ArrayList<String> columns = new ArrayList<>(Arrays.asList(tableColumns));

  public DatFileParser(String inputFilePath, String outputFilePath){
    outputpath = outputFilePath;
    inputpath = inputFilePath;
  }

  public void parseToDb() throws InterruptedException{
    DbWriterThread dbwriter = new DbWriterThread(logBuffer,fileToDbBuffer,this.columns);
    FileParser fileParser = new FileParser(inputpath,fileToDbBuffer,logBuffer);
    Logger logger = new Logger(logBuffer,outputpath);

    ExecutorService executor = Executors.newFixedThreadPool(4);


    executor.submit(dbwriter);
    executor.submit(fileParser);
    executor.submit(logger);

    executor.awaitTermination(1, TimeUnit.MINUTES);
    executor.shutdown();
  }
}
