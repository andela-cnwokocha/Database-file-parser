package checkpoint.andela.db;

import java.sql.*;
import java.util.*;


public class DbWriter implements DbUtils {

  private String dbUrl = DbConstants.URL.toString();
  private String dbUser = DbConstants.USER.toString();
  private String dbPassword = DbConstants.PASSWORD.toString();

  private Connection connection = null;
  private String  MYSQL_DRIVER = DbConstants.DRIVER.toString();
  private ArrayList<String> availableDatabases = new ArrayList<String>();
  private ArrayList<String> columnFields = new ArrayList<String>();

  public DbWriter() {
  }

  private Connection establishConnection(String connectionDriver) throws SQLException {
    registerDriver(connectionDriver);
    connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    return connection;
  }

  @Override
  public void registerDriver(String driver) {
    try {
      Class.forName(driver);
    } catch(ClassNotFoundException  cnf) {
      cnf.getMessage();
    }
  }

  @Override
  public Connection connectToDb(String databasename) throws SQLException {
    registerDriver(MYSQL_DRIVER);
    Connection connection = DriverManager.getConnection(dbUrl+databasename, dbUser, dbPassword);
    return connection;
  }

  @Override
  public boolean isDatabaseExist(String dbName) throws SQLException {
    ArrayList<String> databases =  getAvailableDatabases();
    /*chance to close this.connection*/
    return databases.contains(dbName);
  }

  public ArrayList<String> getAvailableDatabases() throws SQLException {
    establishConnection(MYSQL_DRIVER);
    ArrayList<String> allDatabases = new ArrayList<>();
    ResultSet databases = connection.getMetaData().getCatalogs();
    while(databases.next()) {
      allDatabases.add(databases.getString(1));
      availableDatabases = allDatabases;
    }
    return allDatabases;
  }

  @Override
  // The database already exists.
  public boolean isDatabaseTableExist(String dbName, String tableName) {
    boolean availability = false;
    try {
      Connection conn = connectToDb(dbName);
      DatabaseMetaData dbMetadata = conn.getMetaData();
      ResultSet allTables = dbMetadata.getTables(null,null,tableName,null);
      availability =  allTables.next();
      conn.close();
    } catch(SQLException sqconnect) {
    }
    return availability;
  }

  @Override
  public boolean createDatabase(String databasename) throws SQLException, DbWriterException {
    int update = 0;
    if(!isDatabaseExist(databasename)) {
      Statement statement = connection.createStatement();
      update = statement.executeUpdate("Create database "+databasename);
      closeResources(statement);
    } else {
      throw new DbWriterException("Exception: Database " + databasename + " already exists ...");
    }
    return update == 1;
  }

  @Override
  public boolean deleteDatabase(String databasename) throws SQLException {
    Statement statement = connection.createStatement();
    return (statement.executeUpdate("Drop Database "+databasename) == 0);
  }

  @Override
  public boolean createDatabaseTable(String databasename, String tablename,
                                     ArrayList<String> tableFields) throws SQLException {
    setAttributeList(tableFields);
    Connection conn = connectToDb(databasename);
    Statement statement = conn.createStatement();
    int success = statement.executeUpdate(createTableQuery(tableFields,tablename,"text"));
    conn.close();
    statement.close();
    return success == 0;
  }

  @Override
  public Connection insertToDatabaseTable(HashMap<String, ArrayList<String>> row, String databasename,
                                          String tablename,ArrayList<String> rowSize) throws SQLException {
    Connection conn = connectToDb(databasename);
    PreparedStatement preparedstatement = conn.prepareStatement(insertIntoTableString(rowSize.size(),
        databasename,tablename));
    ArrayList<String> allKeys = rowSize;

    for(int column = 0; column < rowSize.size(); column++) {
      preparedstatement.setString(column+1,inserter(allKeys.get(column),row));
    }
    preparedstatement.executeUpdate();
    return conn;
  }

  private String inserter(String key, HashMap<String,ArrayList<String>> row) {
    String value = null;
    if(row.containsKey(key)) {
      value = row.get(key).toString();
    }
    return value;
  }

  @Override
  public void closeResources(Statement statement) throws SQLException {
    if(connection != null || statement != null) {
      connection.close();
      statement.close();
    }
  }

  private String createTableQuery(ArrayList<String> fields, String tablename, String datatype) {
    String query = "Create Table "+tablename+"(id int auto_increment,";
    for(String field:fields) {
      query+="`"+field+"`"+" "+datatype+",";
    }
    return (query.substring(0, query.length() - 1).concat(", primary key(id))"));
  }

  public String insertIntoTableString(int fieldsSize, String databasename, String tablename) {
    String update = "Insert Into "+databasename+"."+tablename+" values(default,";
    for(int field = 0; field < fieldsSize; field++) {
      update+="?,";
    }
    return update.substring(0,update.length() - 1).concat(")");
  }

  public void setAttributeList(ArrayList<String> columns) {
    columnFields = columns;
  }

}
