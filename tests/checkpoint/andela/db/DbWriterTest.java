package checkpoint.andela.db;

import checkpoint.andela.buffers.*;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/15/16.
 */
public class DbWriterTest {

  @Test
  public void testIsDatabaseExist() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("Belatio",fields);
    assertFalse(writer.isDatabaseExist("Belatio"));
    assertTrue(writer.isDatabaseExist("mysql"));
  }

  @Test
  public void testGetAvailableDatabases() throws Exception {

    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");


    DbWriter writer = new DbWriter("Horatio", fields);

    assertTrue(writer.getAvailableDatabases().contains("mysql"));
    assertFalse(writer.getAvailableDatabases().contains("Holey"));

  }

  @Test
  public void testIsDatabaseTableExist() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("Belatio",fields);
    assertFalse(writer.isDatabaseExist("Holey"));
    writer.createDatabase("Holey");
    assertTrue(writer.isDatabaseExist("Holey"));
    assertFalse(writer.isDatabaseTableExist("Holey","Moley"));
    writer.createDatabaseTable("Holey","Moley", fields);
    assertTrue(writer.isDatabaseTableExist("Holey","Moley"));


  }

  @Test
  public void testCreateDatabase() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("reactions", fields);

    writer.createDatabase("reactions");

    assertTrue( writer.isDatabaseExist("reactions"));
  }

  @Test
  public void testDeleteDatabase() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("reactions", fields);

    assertFalse(writer.isDatabaseExist("reactions"));

    writer.createDatabase("reactions");

    assertTrue(writer.isDatabaseExist("reactions"));

    writer.deleteDatabase("reactions");

    //
    assertFalse(writer.isDatabaseExist("reactions"));

  }

  @Test
  public void testCreateDatabaseTable() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("Ration", fields);

    writer.createDatabase("Ration");
    assertFalse(writer.isDatabaseTableExist("Ration","ratio"));
    writer.createDatabaseTable("Ration","ratio", fields);
    assertTrue(writer.isDatabaseTableExist("Ration","ratio"));

  }


  @Test
  public void testInsertToDatabaseTable() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");


    DbWriter writer = new DbWriter("Alonso", fields);
    HashedArray hashedArray = new HashedArray();

    hashedArray.addToBufferRow("UNIQUE-ID","Chidiebere");
    //hashedArray.addToBufferRow("CLASS", "Andela");
    hashedArray.addToBufferRow("UNIQUE-ID","Nwokocha");
    //hashedArray.addToBufferRow("CLASS", "Youth tech");
    //hashedArray.addToBufferRow("LEFT","handed");
    hashedArray.addToBufferRow("RIGHT", "Food");
    hashedArray.addToBufferRow("RIGHT","CLeft hand");
    hashedArray.addToBufferRow("OCTRO", "kwamdle");

    writer.createDatabase("Alonso");
    writer.createDatabaseTable("Alonso", "friends", fields);

    writer.insertToDatabaseTable(hashedArray,"Alonso","friends");

  }

  @Test
  public void testInsertIntoDbString() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter("Bulldoze", fields);

    assertTrue(writer.insertIntoTableString(fields.size(),"Bulldoze", "cars").equals("Insert Into Bulldoze.cars values(default,?,?,?,?,?)"));
  }
}