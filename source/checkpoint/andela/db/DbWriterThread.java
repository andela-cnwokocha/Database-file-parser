package checkpoint.andela.db;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.*;


public class DbWriterThread extends DbWriter implements Runnable {
  private BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(10);
  private BlockingQueue<HashMap<String,ArrayList<String>>> filetodbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(10);
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
        String uniqueId = row.get("UNIQUE-ID").toString();
        logbuffer.put(threadActivityString(uniqueId));
        conn.close();
      }catch(InterruptedException | SQLException sie){
        System.out.print(sie.getMessage());
      }
    }
  }

  public String threadActivityString(String uniqueid) {
    Date currentTime = new Date();
    Locale myLocale = new Locale("en");
    String myTime = DateFormat.getTimeInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    String myDate = DateFormat.getDateInstance(DateFormat.DEFAULT, myLocale).format(currentTime);
    return "Database Writer thread ("+myDate+" "+myTime+") wrote UNIQUE-ID -> "+uniqueid+" to buffer.\n";
  }
}
