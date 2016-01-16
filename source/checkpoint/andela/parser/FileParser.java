package checkpoint.andela.parser;

import checkpoint.andela.buffers.*;

/**
 * Created by chidi on 1/16/16.
 *
 * This class is the class which parses values from a file to a Buffer, that it. The file to be parsed has properties like the row delimiter, and column delimiter. In this class, the default row delimiter is //.-
 */
public class FileParser {

  private String filepath;
  private String rowDelimiter;
  private String delimiter;
  private int numberOfRows = 0;

  public FileParser(String filepath) {
    this.filepath = filepath;
  }

  public void setFilePath(String filepath){
    this.filepath = filepath;
  }

  public String getFilePath(){
    return this.filepath;
  }
  public void setRowDelimiter(String rowDelimiter){
    this.rowDelimiter = rowDelimiter;
  }

  public HashedArray readFile(String rowDelimiter, String delimiter, String toignore){

    HashedArray hashed = new HashedArray();
    return hashed;
  }
  public int numberOfRows(){

    return 0;
  }

  public void createFile(String filePath) {

  }
}
