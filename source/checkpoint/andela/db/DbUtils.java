package checkpoint.andela.db;

import checkpoint.andela.buffers.*;
import java.sql.*;
import java.util.*;


import checkpoint.andela.buffers.*;
/**
 *
 * About: This interface is implemented by any relational database type. It just holds methods for connecing to a database, and other CRUD functions.
 */
public interface DbUtils {

  boolean isDatabaseExist(String databasename) throws SQLException;

  boolean isDatabaseTableExist(String databasename, String tablename) throws SQLException;

  boolean createDatabase(String databasename) throws SQLException;

  boolean deleteDatabase(String databasename) throws SQLException;

  boolean createDatabaseTable(String databasename, String tablename, ArrayList<String> databaseFields) throws SQLException;

  boolean insertToDatabaseTable(HashedArray bufferedRow, String databasename, String tablename);

  Connection establishConnection();

}
