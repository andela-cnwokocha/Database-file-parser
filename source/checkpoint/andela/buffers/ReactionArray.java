package checkpoint.andela.buffers;

import java.util.*;

/**
 * Created by chidi on 1/14/16.
 * This is the data structure use across the application to hold the data. It is a key-valued. Consisting of a
 */
public class ReactionArray {
  private HashMap<String, ArrayList<String>> hashes = new HashMap<String, ArrayList<String>>();

  public ReactionArray(){
  }

  public boolean rowHasKey(String key) {
    return hashes.containsKey(key);
  }

  @Override
  public String toString() {
    return String.format(" %s [HashMap< %s, ArrayList<%s> >]", getClass().getSimpleName(),"Attributes","Values");
  }

  public String getUniqueId() {
    String uniqueId = hashes.get("UNIQUE-ID").toString();
    return uniqueId;
  }

  public HashMap<String, ArrayList<String>> getBufferRow() {
    HashMap<String, ArrayList<String>> hashed = new HashMap<String, ArrayList<String>>();
    hashed = hashes;
    hashes = new HashMap<String, ArrayList<String>>();
    return hashed;
  }

  public void addToBufferRow(String key, String value) {
    if(rowHasKey(key)) {
      hashes.get(key).add(value);
    } else{
      ArrayList<String> newkeyValues = new ArrayList<String>();
      newkeyValues.add(value);
      hashes.put(key,newkeyValues);
    }
  }

}
