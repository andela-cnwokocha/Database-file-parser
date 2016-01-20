package checkpoint.andela.db;

/**
 * Created by chidi on 1/19/16.
 */
public enum DbConstants {

  DRIVER ("com.mysql.jdbc.Driver"),
  URL ("jdbc:mysql://localhost/"),
  USER ("root"),
  PASSWORD("exo14141"),
  DATABASE ("reactions"),
  TABLE ("react"),
  UNIQUEKEY ("UNIQUE-ID");

  private final String name;

  private DbConstants(String s) {
    name = s;
  }
  public String toString() {
    return this.name;
  }

}
