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

    DbWriter writer = new DbWriter();
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


    DbWriter writer = new DbWriter();

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

    DbWriter writer = new DbWriter();
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

    DbWriter writer = new DbWriter();

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

    DbWriter writer = new DbWriter();

    assertFalse(writer.isDatabaseExist("reactions"));

    writer.createDatabase("reactions");

    assertTrue(writer.isDatabaseExist("reactions"));

    writer.deleteDatabase("reactions");

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

    DbWriter writer = new DbWriter();

    writer.createDatabase("reactions");
    assertFalse(writer.isDatabaseTableExist("reactions","react"));
    writer.createDatabaseTable("reactions","react", fields);
    assertTrue(writer.isDatabaseTableExist("reactions","react"));

  }


  @Test
  public void testInsertToDatabaseTable() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");


    DbWriter writer = new DbWriter();
    ReactionArray reactionArray = new ReactionArray();

    reactionArray.addToBufferRow("UNIQUE-ID","Chidiebere");
    reactionArray.addToBufferRow("CLASS", "Andela");
    reactionArray.addToBufferRow("UNIQUE-ID","Nwokocha");
    reactionArray.addToBufferRow("CLASS", "Youth tech");
    reactionArray.addToBufferRow("LEFT","handed");
    reactionArray.addToBufferRow("RIGHT", "Food");
    reactionArray.addToBufferRow("RIGHT","CLeft hand");
    reactionArray.addToBufferRow("OCTRO", "kwamdle");

    writer.createDatabase("Alonso");
    writer.createDatabaseTable("Alonso", "friends", fields);
    HashMap<String, ArrayList<String>> row = reactionArray.getBufferRow();

    writer.insertToDatabaseTable(row,"Alonso","friends",fields);

  }

  @Test
  public void testInsertIntoDbString() throws Exception {
    ArrayList<String> fields = new ArrayList<>();
    fields.add("UNIQUE-ID");
    fields.add("CLASS");
    fields.add("LEFT");
    fields.add("RIGHT");
    fields.add("OCTRO");

    DbWriter writer = new DbWriter();

    assertTrue(writer.insertIntoTableString(fields.size(),"Bulldoze", "cars").equals("Insert Into Bulldoze.cars values(default,?,?,?,?,?)"));
  }

}