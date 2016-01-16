package checkpoint.andela.db;

import checkpoint.andela.buffers.*;
import java.sql.*;
import java.util.*;


/**
 *
 * About: This interface is implemented by any relational database type. It just holds methods for connecing to a database, and other CRUD functions.
 */
public interface DbUtils {

  // done
  boolean isDatabaseExist(String databasename) throws SQLException;

  // done
  boolean isDatabaseTableExist(String databasename, String tablename);

  //done
  boolean createDatabase(String databasename) throws SQLException,DbWriterException;

  //done
  boolean deleteDatabase(String databasename) throws SQLException;

  // done
  boolean createDatabaseTable(String databasename, String tablename, ArrayList<String> databaseFields) throws SQLException, DbWriterException;

  // done
  boolean insertToDatabaseTable(HashedArray bufferedRow, String databasename, String tablename) throws SQLException;

  //done
  Connection connectToDb(String connectionType) throws SQLException;

  //done
  void registerDriver(String driver);


  void closeResources(Statement stmt) throws SQLException;

}
