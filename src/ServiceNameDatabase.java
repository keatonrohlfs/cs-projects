/**
 * Stores service codes and servcie names in a text file
 *
 * @author Liam Tucker
 * @version 1.0
 */

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.sql.Timestamp;
//import java.lang.reflect.Member;
//import java.sql.Timestamp; // For setting the current time
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.Scanner;


/**
 * Stores service codes and service names.

 * @author letucker
 * @version 1.0
 *
 */
public class ServiceNameDatabase {
  
  private String filename;
  
  public ServiceNameDatabase() {
    filename = "./Stored Data/ServiceNameDatabase.txt";
  }
  
  /**
   * Creates a file with passed as the filename.
   * Called in addSerice, report methods

   * @return true if file is created successfully, false if file isn't created or already exists
   */
  private boolean createFile(String filename) {
    //Ensuring filename ends with a .
    filename = filename.trim();
    if (filename.indexOf('.') != -1) filename = filename.substring(0, filename.indexOf('.'));
    filename += ".txt";

    //Creating empty file
    try {
      File myObj = new File(filename);
      if (myObj.createNewFile()) {
        //System.out.println("File created: " + myObj.getName());
      } else {
        //System.out.println("File already exists in program");
      }
    } catch (IOException e) {
      //System.out.println("Could not create file");
      e.printStackTrace();
      return false;
    }

    //Updating this ServiceList's file name
    return true;
  }  
  
  /**
   * Method to get current time in format YYYY-MM-DD HH:MM:SS.
   * Called in createProviderDirectory

   * @return String with current time
   */
  private String getCurrentTime() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Format 2021-03-24 17:12:03.311
    String tempstr = timestamp.toString();
    String newTimestamp = (tempstr.substring(5, 10) + "-" + tempstr.substring(0, 4) + tempstr.substring(10, 19)).toString();
    return newTimestamp;
  }
  
  //Clears file; requires a password
  /**
   * Clears file. For testing purposes only.

   * @param pass rudimentary attempt to prevent accidental calls. Pass is "letucker".
   * @return true if file cleared, else false
   */
  public boolean clearFile(String pass) {
    //Checking that it is me running this command
    if (pass != "letucker") {
      //System.out.println("Invalid password: file '" + this.filename + "' not cleared.");
      return false;
    }
 
    //Writing "" over empty file
    try {
      FileWriter myWriter = new FileWriter(this.filename, false); // An overload. Passing true means we do not replace the file contents
      myWriter.write("");
      myWriter.close();
      //System.out.println("Successfully cleared file: " + this.filename);
      return true;
    } catch (IOException e) {
      //System.out.println("An error occurred.");
      e.printStackTrace();
      return false;
    }
 
  }
  
  public boolean isValidService(String serviceCode) {
    if (serviceCode.length() != 6) return false;
    
    File file = new File(this.filename);
    
    try {
      Scanner scanner = new Scanner(file);

      //now read the file line by line...
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (line.substring(0, 6).compareTo(serviceCode) == 0) return true;
      }
    } catch (FileNotFoundException e) {
      System.out.println("Service Name Database not found.");
      return false;
    }
    
    return false;
  }
  
  /**
   * Adds the specified service code and service name to database.
   * 
   * @param servCode is service code
   * @param servName is service name
   */
  public boolean addService(String servCode, String servName, Double fee) {    
    
    boolean worked = this.createFile(this.filename);
    if (! worked) {
      System.out.println("Could not create file");
      return false;
    }
    if (servCode.length() != 6) {
      System.out.println("Invalid service code: must be six characters long");
      return false;
    }
    if (servName.length() > 20) servName = servName.substring(0, 20);
    if (servName.length() < 20) {
      int servNameLen = servName.length();
      for (int i = servNameLen; i < 20; i++) {
        servName += " ";
      }
    }
    
    //Writing to already created file
    try {
      FileWriter myWriter = new FileWriter(this.filename, true); // An overload. Passing true means we do not replace the file contents
      String servLine = servCode + servName + fee;
      myWriter.write(servLine + "\n");
      myWriter.close();
      //System.out.println("Successfully added line to file: " + servLine);
      return true;
    } catch (IOException e) {
      System.out.println("Error: file does not exist");
      return false;
    }
  }
  
  /**
   * Finds the service name for a given service code.
   * 
   * @param servCode is specified service code
   * @return is specified service name
   */
  public String getServiceName(String servCode) {
    
    File file = new File(this.filename);
    String name = "";

    //Looking for entry in file
    try {
      Scanner scanner = new Scanner(file);

      //now read the file line by line...
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.substring(0, 6).compareTo(servCode) == 0) {
          name = line.substring(6, 26);
          scanner.close();
          return name;
        }
      }
      scanner.close();
      return "";
    } catch (FileNotFoundException e) {
      System.out.println("Service Name Database not found");
      return "";
    }
    
  }
  
  public double getServiceFee(String servCode) {
    File file = new File(this.filename);
    double fee = 0.0;
    
  //Looking for entry in file
    try {
      Scanner scanner = new Scanner(file);

      //now read the file line by line...
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.substring(0, 6).compareTo(servCode) == 0) {
          fee = Double.parseDouble(line.substring(26).trim());
          scanner.close();
          return fee;
        }
      }
      scanner.close();
      return fee;
    } catch (FileNotFoundException e) {
      System.out.println("Service Name Database not found");
      return -1.0;
    }
    
  }
  
  public boolean createProviderDirectory() {

    File file = new File(this.filename);
    String provDirectName = "Provider Directory " + this.getCurrentTime().substring(0, 10);

    boolean worked = this.createFile(provDirectName);
    if (! worked) {
      System.out.println("Could not create provider directory.");
      return false;
    }
    
    //Traversing ServiceNameDatabase
    try {
      Scanner scanner = new Scanner(file);
      
      try {
        FileWriter myWriter = new FileWriter(this.filename, false);
        
        myWriter.write("Service Code        Service Name\n\n");
        
        //now read the file line by line...
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          myWriter.write(line.substring(0, 6) + "              " + line.substring(6, 26) + "\n");
        }
        scanner.close();
        myWriter.close();
        return true;
      } catch (IOException e) { // For file writer (writing provider directory)
        System.out.println("Could not create provider directory.");
        scanner.close();
        return false;
      }

    } catch (FileNotFoundException e) { // For scanner (traversing service name database)
      System.out.println("Could not create provider directory.");
      return false;
    }
  }

}
