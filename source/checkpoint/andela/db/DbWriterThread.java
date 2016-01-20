package checkpoint.andela.db;

import checkpoint.andela.log.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.*;


public class DbWriterThread extends DbWriter implements Runnable, ThreadActivityString {
  private BlockingQueue<String> logbuffer = new ArrayBlockingQueue<String>(5);
  private BlockingQueue<HashMap<String,ArrayList<String>>> filetodbBuffer = new ArrayBlockingQueue<HashMap<String, ArrayList<String>>>(5);
  private ArrayList<String> rowSize = new ArrayList<>();

  public DbWriterThread(BlockingQueue<String> logbuffer, BlockingQueue<HashMap<String,ArrayList<String>>>
      fileToDbBuffer, ArrayList<String> rowSize) {
    this.logbuffer = logbuffer;
    this.filetodbBuffer = fileToDbBuffer;
    this.rowSize = rowSize;
  }

  @Override
  public void run() {
    while(true) {
      try {
        HashMap<String,ArrayList<String>> row = filetodbBuffer.take();
        Connection conn = insertToDatabaseTable(row,DbConstants.DATABASE.toString(),
            DbConstants.TABLE.toString(),
            rowSize);
        String uniqueId = row.get(DbConstants.UNIQUEKEY.toString()).toString();
        logbuffer.put(threadActivity(uniqueId, this));
        conn.close();
      } catch(InterruptedException | SQLException sie) {
      }
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
