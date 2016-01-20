package checkpoint.andela.main;

import checkpoint.andela.db.*;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by chidi on 1/17/16.
 */
public class DatFileParserTest {

  @Test
  public void testParseFileToDb() throws Exception {
    DatFileParser datfp = new DatFileParser("/home/chidi/Desktop/reactions.dat","/home/chidi/Desktop/jocker.txt");


    String[] tableColumns = {"UNIQUE-ID","TYPES","COMMON-NAME","ATOM-MAPPINGS","CANNOT-BALANCE?","COMMENT","COMMENT-INTERNAL","CREDITS","DELTAG0","EC-NUMBER","ENZYMATIC-REACTION","IN-PATHWAY","LEFT","MEMBER-SORT-FN","ORPHAN?","PHYSIOLOGICALLY-RELEVANT?","PREDECESSORS","PRIMARIES","REACTION-DIRECTION","REACTION-LIST","RIGHT","RXN-LOCATIONS","SPONTANEOUS?","STD-REDUCTION-POTENTIAL","SYNONYMS","SYSTEMATIC-NAME"};
    ArrayList<String> columns = new ArrayList<>(Arrays.asList(tableColumns));

    // set up database
    DbWriter writer = new DbWriter();
    writer.createDatabase("reactions");
    writer.createDatabaseTable("reactions", "react", columns);

    datfp.parseToDb();
  }
}