package checkpoint.andela.db;

import checkpoint.andela.buffers.*;
import java.sql.*;
import java.util.*;

/**
 * Created by chidi on 1/15/16.
 *
 *
 * This class is for connecting a Mysql database, given the connction, and it extending the DbUtils interface. This class therefore carries out connecting to the database, and doing all the CRUD functions on the database given the row that was provided to the appropriate method. This class assumes the user is a root user, with password 1234567890 by default (which can be changed).
 */
public class DbWriter implements DbUtils {

  private String dbUrl = "jdbc:mysql://localhost/";
  private String dbUser = "root";
  private String dbPassword = "exo14141";

  private String dbname;
  private Connection connection = null;
  private String  MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  private ArrayList<String> availableDatabases = new ArrayList<String>();
  private ArrayList<String> columnFields = new ArrayList<String>();

  public DbWriter(){

  }


  private Connection establishConnection(String connectionDriver) throws SQLException {
    registerDriver(connectionDriver);
    this.connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    return this.connection;
  }

  @Override
  public void registerDriver(String driver){
    try{
      Class.forName(driver);
    }catch(ClassNotFoundException cnf){
      System.err.print(cnf.getMessage());
      System.exit(1);
    }
  }

  @Override
  public Connection connectToDb(String databasename) throws SQLException{
    registerDriver(MYSQL_DRIVER);
    Connection connection = DriverManager.getConnection(this.dbUrl+databasename, this.dbUser, this.dbPassword);
    return connection;
  }

  @Override
  public boolean isDatabaseExist(String dbName) throws SQLException {
    return getAvailableDatabases().contains(dbName);
  }

  public ArrayList<String> getAvailableDatabases() throws SQLException{
    establishConnection(MYSQL_DRIVER);
    ArrayList<String> allDatabases = new ArrayList<>();
    ResultSet databases = this.connection.getMetaData().getCatalogs();
    while(databases.next()){
      allDatabases.add(databases.getString(1));
      this.availableDatabases = allDatabases;
    }
    return allDatabases;
  }

  @Override
  // The database already exists.
  public boolean isDatabaseTableExist(String dbName, String tableName){
    boolean availability = false;
    try{
      DatabaseMetaData dbMetadata = connectToDb(dbName).getMetaData();
      ResultSet allTables = dbMetadata.getTables(null,null,tableName,null);
      availability =  allTables.next();
    }catch(SQLException sqconnect){
      System.err.print(sqconnect.getMessage());
    }
    return availability;
  }

  @Override
  public boolean createDatabase(String databasename) throws SQLException, DbWriterException{
    int update = 0;
    if(!isDatabaseExist(databasename)){
      Statement statement = this.connection.createStatement();
      update = statement.executeUpdate("Create database "+databasename);
      closeResources(statement);
    }else {
      throw new DbWriterException("Exception: Database " + databasename + " already exists!");
    }
    return update == 1;
  }

  @Override
  public boolean deleteDatabase(String databasename) throws SQLException {
    Statement statement = this.connection.createStatement();
    return (statement.executeUpdate("Drop Database "+databasename) == 0);

  }

  @Override
  public boolean createDatabaseTable(String databasename, String tablename, ArrayList<String> tableFields) throws SQLException {
    setAttributeList(tableFields);
    Statement statement = connectToDb(databasename).createStatement();
    int success = statement.executeUpdate(createTableQuery(tableFields,tablename,"text"));

    closeResources(statement);
    return success == 0;
  }

  @Override
  public Connection insertToDatabaseTable(HashMap<String, ArrayList<String>> row, String databasename, String tablename,ArrayList<String> rowSize) throws SQLException {

    Connection conn = connectToDb(databasename);
   PreparedStatement preparedstatement = conn.prepareStatement(insertIntoTableString(rowSize.size(),databasename,tablename));
    ArrayList<String> allKeys = rowSize;

    for(int column = 0; column < rowSize.size(); column++){
      preparedstatement.setString(column+1,inserter(allKeys.get(column),row));
    }
    preparedstatement.executeUpdate();
    return conn;
  }

  private String inserter(String key, HashMap<String,ArrayList<String>> row){
    String value = null;
    if(row.containsKey(key)){
      value = row.get(key).toString();
    }
    return value;
  }

  @Override
  public void closeResources(Statement statement) throws SQLException{
    if(this.connection != null || statement != null){
      this.connection.close();
      statement.close();
    }
  }

  private String createTableQuery(ArrayList<String> fields, String tablename, String datatype){
    String query = "Create Table "+tablename+"(id int auto_increment,";
    for(String field:fields){
      query+="`"+field+"`"+" "+datatype+",";
    }
    return (query.substring(0, query.length() - 1).concat(", primary key(id))"));

  }

  public String insertIntoTableString(int fieldsSize, String databasename, String tablename){
    String update = "Insert Into "+databasename+"."+tablename+" values(default,";
    for(int field = 0; field < fieldsSize; field++){
      update+="?,";
    }
    return update.substring(0,update.length() - 1).concat(")");
  }

  public void setAttributeList(ArrayList<String> columns){
   this.columnFields = columns;
  }

  private void setDbUrl(String dburl){
    this.dbUrl = dburl;
  }

  private void setDbUser(String dbuser){
    this.dbUrl = dbuser;
  }

  private void setDbPassword(String dbPassword){
    this.dbUrl = dbPassword;
  }

}
