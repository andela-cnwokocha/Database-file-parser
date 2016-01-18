<img src="https://dl.dropboxusercontent.com/s/cpq8wvg976bqmnf/icon.png?dl=0?raw=1" alt="book clubreadme" align="right" />


# Database-file-parser
A multi threaded program that parses data from a reaction file to a database, while providing a log file of thread activities.

####About
This program reads data from a reactions.dat file, into a MySQL database. The program assumes that the user already has MySql installed on his/her local system, and is a root user. 

##Classes and methods
### checkpoint.andela.main.DatFileParser  
``` java  
checkpoint.andela.main.DatFileParser(String inputFilePath, String outputFilePath)  
```  
This is a class is the main point of entry. has two methods; `parseToDb()` which runs the entire program, and a static `main(String [] args)`.
#####Method
`DatFileParser.parseToDb()` 
This method runs the program, It does this by calling the threaded classes and running them in together using executors.   

###checkpoint.andela.fileParser.FileParser   
```java  
FileParser(String inputFilePath, BlockingQueue<HashMap<String,ArrayList<String>>> fileToDbBuffer, BlockingQueue<String> logbuffer) 
 ```  
  
This class implements a Runnable interface to read the contents of a file, and outputs this into a buffer. The buffer used here is a HashMap of string keys, and string array values. When it reads a row, it puts it into the queue, while also logging its activity in another buffer. 
#####Methods  
`String threadActivityString(String uniqueid)` This method is used to construct the thread activity to be logged into the logbuffer.  
`void processLine(String line, String delimiter)` This method takes in a line in the file, and splits it into by the appropriate delimiter, then adds it to a class which holds each row.  

###checkpoint.andela.logger.Logger
`Logger(BlockingQueue<String> logbuffer, String outputFilePath)`   
This class implements a Runnable interface, and logs the activity of any threaded activity into the file specified in its *outputFilePath*. The activities are either that of the FileParser, or the Database writer.   
#####Methods
`void logActivity()`: This methods *takes*  from *logBuffer*, and writes it into a file.  

###checkpoint.andela.buffer.HashedArray
`HashedArray()` This class is instantiated with no argument. It is the data structure used by the entire program to hold rows of data, and do work the data being held.   
#####Methods
`boolean rowHasKey(String key)` : This methods checks if a given row has the specified key.  
`String getUniqueId()` : This method returns the 'UNIQUE-ID' value of a row.  
`HashMap<String, ArrayList<String>> getBufferRow()` This method returns a full row of data.  
`addToBufferRow(String key, String value)` : This method adds to the appropriate row the specified key and its value.


 

