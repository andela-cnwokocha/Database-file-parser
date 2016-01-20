package checkpoint.andela.db;

import java.sql.*;
import java.util.*;


/**
 *
 * About: This interface is implemented by any relational database type. It just holds methods for connecing to a database, and other CRUD functions.
 */
public interface DbUtils {

  boolean isDatabaseExist(String databasename) throws SQLException;

  boolean isDatabaseTableExist(String databasename, String tablename);

  boolean createDatabase(String databasename) throws SQLException,DbWriterException;

  boolean deleteDatabase(String databasename) throws SQLException;

  boolean createDatabaseTable(String databasename, String tablename, ArrayList<String> databaseFields)
      throws SQLException, DbWriterException;

  Connection insertToDatabaseTable(HashMap<String, ArrayList<String>> row, String databasename,
                                   String tablename, ArrayList<String> rowSize) throws SQLException;

  Connection connectToDb(String connectionType) throws SQLException;

  void registerDriver(String driver);

  void closeResources(Statement stmt) throws SQLException;

}
