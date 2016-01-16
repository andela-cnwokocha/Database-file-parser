package checkpoint.andela.buffers;

import checkpoint.andela.db.*;
import java.util.*;

/**
 * Created by chidi on 1/14/16.
 * This is the data structure use across the application to hold the data. It is a key-valued. Consisting of a
 */
public class HashedArray {

  private HashMap<String, ArrayList<String>> hashes = new HashMap<String, ArrayList<String>>();

  public HashedArray(){

  }

  public boolean rowHasKey(String key){
    return hashes.containsKey(key);
  }

  @Override
  public String toString() {
    return String.format(" %s [HashMap< %s, ArrayList<%s> >]", getClass().getSimpleName(),"Attributes","Values");
  }

  public String getUniqueId(){
    String uniqueId = this.hashes.get("UNIQUE-ID").toString();
    return uniqueId;
  }

  public HashMap<String, ArrayList<String>> getBufferRow(){
    HashMap<String, ArrayList<String>> hashed = new HashMap<String, ArrayList<String>>();
    hashed = this.hashes;
    this.hashes = new HashMap<String, ArrayList<String>>();
    return hashed;
  }

  public int getRowKeyValSize(String key) throws DbWriterException {
    int size;
    if(rowHasKey(key)){
      size = hashes.get(key).size();
    }else{
      throw new DbWriterException("Exception: No such key");
    }
    return size;
  }

  public void addToBufferRow(String key, String value){
    if(rowHasKey(key)){
      hashes.get(key).add(value);
    }else{
      ArrayList<String> newkeyValues = new ArrayList<String>();
      newkeyValues.add(value);
      hashes.put(key,newkeyValues);
    }
  }



}
