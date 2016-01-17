package checkpoint.andela.db;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chidi on 1/17/16.
 */
public class DbWriterThread extends DbWriter implements Runnable {
  private BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(10);
  private BlockingQueue<HashMap<String,ArrayList<String>>> filetodbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(20);
  private ArrayList<String> rowSize = new ArrayList<>();

  public DbWriterThread(BlockingQueue<String> logbuffer, BlockingQueue<HashMap<String,ArrayList<String>>>
      fileToDbBuffer, ArrayList<String> rowSize){
    this.logbuffer = logbuffer;
    this.filetodbBuffer = fileToDbBuffer;
    this.rowSize = rowSize;
  }

  @Override
  public void run(){
    while(true){
      try{
        HashMap<String,ArrayList<String>> row = filetodbBuffer.take();
        Connection conn = insertToDatabaseTable(row,"reactions","react",this.rowSize);
        conn.close();
      }catch(InterruptedException | SQLException sie){
        System.out.print(sie.getMessage());
      }
    }
  }
}
