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
  private static BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(20);
  private static BlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(20);
  private static String outputpath;
  private static String inputpath;
  private static String[] tableColumns = {"UNIQUE-ID","TYPES","COMMON-NAME","ATOM-MAPPINGS","CANNOT-BALANCE?","CITATIONS","COMMENT","COMMENT-INTERNAL","CREDITS","DATA-SOURCE","DBLINKS","DELTAG0","DOCUMENTATION","EC-NUMBER","ENZYMATIC-REACTION","ENZYMES-NOT-USED","EQUILIBRIUM-CONSTANT","HIDE-SLOT?","IN-PATHWAY","INSTANCE-NAME-TEMPLATE","LEFT","MEMBER-SORT-FN","ORPHAN?","PATHOLOGIC-NAME-MATCHER-EVIDENCE","PATHOLOGIC-PWY-EVIDENCE","PHYSIOLOGICALLY-RELEVANT?","PREDECESSORS","PRIMARIES","REACTION-DIRECTION","REACTION-LIST","REGULATED-BY","REQUIREMENTS","RIGHT","RXN-LOCATIONS","SIGNAL","SPECIES","SPONTANEOUS?","STD-REDUCTION-POTENTIAL","SYNONYMS","SYSTEMATIC-NAME","TEMPLATE-FILE"};
  ArrayList<String> columns = new ArrayList<>(Arrays.asList(tableColumns));

  public DatFileParser(String inputFilePath, String outputFilePath){
    outputpath = outputFilePath;
    inputpath = inputFilePath;
  }

  public void parseToDb() throws InterruptedException{
    DbWriterThread dbwriter = new DbWriterThread(logBuffer,fileToDbBuffer,this.columns);
    FileParser fileParser = new FileParser(inputpath,fileToDbBuffer,logBuffer);
    Logger logger = new Logger(logBuffer,outputpath);

    ExecutorService executor = Executors.newFixedThreadPool(6);
    executor.submit(fileParser);
    executor.submit(dbwriter);
    executor.submit(logger);

    executor.submit(logger);

    executor.awaitTermination(1, TimeUnit.HOURS);
    //System.out.println("Finished working");
    executor.shutdown();
  }


  public static void main(String [] args) throws InterruptedException,DbWriterException,SQLException{
    DatFileParser datfp = new DatFileParser("/home/chidi/Desktop/reactions.dat","/home/chidi/Desktop/jocker.txt");

    //Setup database and table
    ArrayList<String> columns = new ArrayList<>(Arrays.asList(tableColumns));
    DbWriter writer = new DbWriter();
    writer.createDatabase("reactions");
    writer.createDatabaseTable("reactions", "react", columns);

    Thread.sleep(100);
    // write to
    datfp.parseToDb();

  }

}
