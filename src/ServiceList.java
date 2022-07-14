/**
 * Stores Services. Can be accessed by anyone accessing any data about a Service.
 * Internally, the service data is stored in a text file
 *
 * @author Liam Tucker
 * @version 1.2
 */

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp; // For setting the current time
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//import javax.swing.text.Document;

/**
 * Stores Services in a .txt file

 * @author Liam Tucker
 * @version = 1.3
 *
 */
public class ServiceList {
	
  private String filename;
	
  // Empty Constructor
  public ServiceList() {
    filename = "./Stored Data/ServiceList.txt";
  }
	
  //Constructor
  public ServiceList(String filename) {
  //Ensuring filename ends with a .
    filename = filename.trim();
    if (filename.indexOf('.') != -1) filename = filename.substring(0, filename.indexOf('.'));
    filename += ".txt";
    this.filename = filename;
  }
	
  //Getter; no setter because we should never externally update filename
  public String getFilename() {
    return this.filename;
  }	

  
  //Code adapted from https://www.w3schools.com/java/java_files_create.asp
  //Checks for error, meaning there's no need for 
  //TODO: refactor report methods to use this method
  /**
   * Creates a file with passed as the filename.
   * Called in addSerice, report methods

   * @return true if file is created successfully, false if file isn't created or already exists
   */
  private boolean createFile(String filename) {
    //Ensuring filename ends with a .
    filename = filename.trim();
    //if (filename.indexOf('.') != -1) filename = filename.substring(0, filename.indexOf('.'));
    //filename += ".txt";

    //Creating empty file
    try {
      File myObj = new File(filename);
      if (myObj.createNewFile()) {
        //System.out.println("File created: " + myObj.getName());
      } else {
        //System.out.println("File already exists in program");
      }
    } catch (IOException e) { //TODO: update catch statement to account for expected errors
      //System.out.println("Could not create file");
      e.printStackTrace();
      return false;
    }

    //Updating this ServiceList's file name
    //this.filename = filename;
    return true;
  }
  
  private boolean createFolder(String f) {
    File tempFile = new File("");
    String currentPath = tempFile.getAbsolutePath();
    String folderName = ".\\" + f;
    File file = new File(folderName);
    boolean bool = file.mkdir();
    if (! bool) {
      //System.out.println("Could not make directory");
      return false;
    }
    try {
     Files.createDirectories(Paths.get(System.getProperty("user.dir")));
   } catch (IOException e) {
     // TODO Auto-generated catch block
     //System.out.println("Could not make directory");
     e.printStackTrace();
     return false;
   }
    
    return true;
  }

  
  /**
   * Finds which week the date being considered is in.
   * Called in add service, createReports methods

   * @param today is the current date (or the date we're comparing)
   * @return the weekNum value we're storing
   */
  public int findWeekNum(String now) {
    LocalDate today = LocalDate.parse(now);
    LocalDate startDate = LocalDate.parse("2022-03-27");
    long daysBetween = ChronoUnit.DAYS.between(startDate, today);
    int weekNumber = 1 + (int) (daysBetween / 7);
    return weekNumber;    
  }
  
  
  /**
   * Returns an ArrayList of services for a member.
   * Helper method for createMemberReports 

   * @param membNum is the member in question's member number
   * @return every service they have had in our data
   */
  private ArrayList<String> getServices(String num, boolean isMember) {
    File file = new File(this.filename);
    
    ArrayList<String> services = new ArrayList<String>();

    //Looking for entry in file
    try {
      Scanner scanner = new Scanner(file);
      
      //ArrayList<String> services = new ArrayList<>();

      //now read the file line by line...
      while (scanner.hasNextLine()) {
        
        
        String line = scanner.nextLine();
        if (isMember) {
          if (line.substring(38, 47).compareTo(num) == 0) { // Possible source of error
            services.add(line);
          }
        }
        else { // isProvider
          if (line.substring(29, 38).compareTo(num) == 0) { // Possible source of error
            services.add(line);
          }
        }
      }
      scanner.close();
      
      //Sorting services by date
      Collections.sort(services, (a, b) -> {
        return (a.substring(6, 10) + a.substring(0, 2) + a.substring(3, 5)).compareTo(b.substring(6, 10) + b.substring(0, 2) + b.substring(3, 5));
        //Sorts by date (simplifies to YYYYMMDD)
      });
      return services;
    } catch (FileNotFoundException e) { 
      //System.out.println("File not found.");
      return services;
    }    
  }
  
  /**
   * Method to get current time in format YYYY-MM-DD HH:MM:SS.
   * Called in addService

   * @return String with current time
   */
  private String getCurrentTime() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Format 2021-03-24 17:12:03.311
    String tempstr = timestamp.toString();
    String newTimestamp = (tempstr.substring(5, 10) + "-" + tempstr.substring(0, 4) + tempstr.substring(10, 19)).toString();
    return newTimestamp;
  }
  
  /**
   * Adds service to service list.

   * @param s Service to be added.
   */
  public boolean addService(Service s) {
    //TODO: modify to create file if it didn't already exist
    
    /*
     * This calculates the number of weeks between March 26 2022, a Saturday, and the service date;
     * and stores that as the last value in the file.
     * This allows us to easily check if a service is in the current week when it comes time to run a report
     */
    String sDate = s.getServDate().substring(6) + "-" + s.getServDate().substring(0, 5);
    int weekNumber = findWeekNum(sDate);
    //System.out.println("Week number: " + weekNumber);    
    
    // Creating time stamp
    // Would make more sense to get time stamp when creating the Service, but this is in case
    //there's a delay between creating the service and adding it
    s.setCurrTime(this.getCurrentTime());

    //Padding comments to ensure it's 100 characters long
    String comments = s.getComments();
    if (comments.length() > 100) comments = comments.substring(0, 100);
    if (comments.length() < 100) {
      int padding = 100 - comments.length();
      for (int i = 0; i < padding; i++) {
        comments += " ";
      }
    }
    
    //Creating string to be written
    String thisLine = s.getCurrTime() + s.getServDate() + s.getProvNum() + s.getMembNum() + s.getServCode() + comments + weekNumber + "\n";

    //TODO: may need to refactor
    boolean worked = this.createFile(this.filename);
    if (! worked) {
      //System.out.println("Could not create file");
      return false;
    }
    //Writing to already created file
    try {
      FileWriter myWriter = new FileWriter(this.filename, true); // An overload. Passing true means we do not replace the file contents
      myWriter.write(thisLine);
      myWriter.close();
      //System.out.println("Successfully added line to file " + this.filename);
      return true;
    } catch (IOException e) {
        //System.out.println("Error: file '" + this.filename + "' does not exist");
        //e.printStackTrace();
        return false;
    }    
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
      System.out.println("Invalid password: file '" + this.filename + "' not cleared.");
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
      System.out.println("ERROR: could not clear file.");
      e.printStackTrace();
      return false;
    }
	
  }


  /**
   * Deletes file. For testing purposes only.

   * @param pass rudimentary attempt to prevent accidental calls. Pass is "letucker".
   * @return true if file deleted, else false
   */

  public boolean deleteFile(String pass) {
    //Checking that it is me calling this command
    if (pass != "letucker") {
      //System.out.println("Invalid password: file not cleared.");
      return false;
    }	
	
    File myObj = new File(this.filename); 
    if (myObj.delete()) { 
      //System.out.println("Deleted the file: " + myObj.getName());
      this.filename = "";
      return true;
    } else {
      System.out.println("ERROR: Failed to delete the file " + myObj.getName());
      return false;
    } 
  }
	
  //TODO: Check assumption that each member only has one service on a given day
  /**
   * Finds a service with the given membNum and servDate.

   * @param membNum in the format "123456789"
   * @param servDate in the format "MM-DD-YYYY"
   * @return specified service, or empty service if not found
   */
  public Service getService(String membNum, String servDate) {
    File file = new File(this.filename);

    //Looking for entry in file
    try {
      Scanner scanner = new Scanner(file);

      //now read the file line by line...
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.substring(38, 47).compareTo(membNum) == 0 && line.substring(19, 29).compareTo(servDate) == 0) { 
          String addDate = line.substring(0, 19);
          String provNum = line.substring(29, 38);
          String servCode = line.substring(47, 53);
          String comments = line.substring(53, 153).trim();
          //Shouldn't ever create error, provided the items are added correctly
          int weekNum = Integer.parseInt(line.substring(153));          

          scanner.close();	
          Service thisService = new Service(addDate, servDate, provNum, membNum, servCode, comments, weekNum);
          return thisService;
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) { 
      //handle this
      //System.out.println("File '" + this.filename + "' not found.");
 
    }
    
    Service empty = new Service();
    return empty;
  }
  
  //TODO: Create method to create fourth report
  //TODO: Create method to create summary report
  //TODO: Create method to create provider report
  
  /**
   * Creates a memberReport for every member to have a service this week.

   * @param memberNums is a list of every member number for every member in the system
   * @return 
   */
  public boolean createMemberReports(List<String> memberNums) {
    //TODO: ensure Member has a method "public Member getMember(int membNum)"
    //TODO: add in functionality to put all member reports in a seperate folder
    boolean invalidReport = false;
    
    MemberList membList;
    try {
      membList = MemberList.instance();
    } catch (ClassNotFoundException e2) {
      // TODO Auto-generated catch block
      System.out.println("Could not create member reports.");
      return false;
    } catch (IOException e2) {
      // TODO Auto-generated catch block
      System.out.println("Could not create member reports.");
      return false;
    }
       
    //Getting the current week number
    //System.out.println(this.getCurrentTime().substring(0, 10));
    String today = this.getCurrentTime().substring(6, 10) + "-" + this.getCurrentTime().substring(0, 5);
    int weekNum = findWeekNum(today);
    
    for (int i = 0; i < memberNums.size(); i++) {
      boolean isFirstReport = true;
      
      //Getting current member
      String thisMemberNum = memberNums.get(i);
      Member thisMember;
      
      try {
        thisMember = membList.getMember(thisMemberNum);
      } catch (Exception e1) {
        System.out.println("Could not create member reports.");
        return false;
      } //TODO: ensure this method exists
      
      //Getting the list of services for this member
      ArrayList<String> memberServicesArrayList = getServices(thisMemberNum, true);
      //TODO: sort by service date
      if (memberServicesArrayList.isEmpty()) continue; // Means this member had no services this week
      String[] memberServices = new String[memberServicesArrayList.size()]; // Potential error
      for (int j = 0; j < memberServicesArrayList.size(); j++) {
        memberServices[j] = memberServicesArrayList.get(j);
      }
  
      //Finding number of services this week
      int weekServiceCount = 0;
      for (int j = 0; j < memberServices.length; j++) {
        if (memberServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
          weekServiceCount ++;
        }
      }
      
      if (weekServiceCount == 0) continue; // Means this member had no services this week
      
      String folderName = "";
      if (isFirstReport) {
        isFirstReport = false;
        folderName = "Member Reports " + today;
        boolean createdFolder = createFolder(folderName);
        //if (!createdFolder) System.out.println("Could not create folder.");
        //return false;
      }
      
      String thisMemberName = thisMember.getName().trim();
      String thisMembFileName = "./" + folderName + "/" + thisMemberName + " " + today + ".txt";

      
      //Creating text document for this user
      boolean worked = this.createFile(thisMembFileName);
      if (! worked) {
        System.out.println("Could not create Member report: " + thisMemberName);
        invalidReport = true;
      }
      
      //Writing to already created file
      try {
        FileWriter myWriter = new FileWriter(thisMembFileName, false); // An overload. Passing true means we do not replace the file contents
        myWriter.write(thisMemberName + "\n");
        myWriter.write("Member Number: " + thisMemberNum + "\n");
        myWriter.write(thisMember.getAddress() + "\n");
        myWriter.write(thisMember.getCity().trim() + ", " + thisMember.getState().trim() + ", " + thisMember.getZip().trim()  + "\n");
        myWriter.write("\n");
        myWriter.write("You had " + weekServiceCount + " services this week."  + "\n");
        myWriter.write("\n");
        myWriter.write("\n");
        
        //TODO: add individual service information
        int thisServCount = 1;
        for (int j = 0; j < memberServices.length; j++) {
          if (memberServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
            myWriter.write("Service number " + thisServCount  + "\n");
            myWriter.write("\n");
            myWriter.write("Service date: " + memberServices[j].substring(19, 29)  + "\n");
            
            ProviderList p = ProviderList.instance();
            
            String provName = p.getProvider(memberServices[j].substring(29, 38)).GetProviderName();
            myWriter.write("Provider Name: " + provName + "\n"); // Need to print out provider name too
            ServiceNameDatabase servNameDB = new ServiceNameDatabase();
            String servName = servNameDB.getServiceName(memberServices[j].substring(47, 53).trim());
            myWriter.write("Service Name: " + servName + "\n"); // Need to print out service name too
            myWriter.write("\n");
            thisServCount ++;
          }
        }
        
        myWriter.close();
      } catch (IOException | ClassNotFoundException e) {
        //System.out.println("An error occurred.");
        invalidReport = true;
        //e.printStackTrace();
      }      
    }
    return (! invalidReport);
    
  }
  
  /**
   * Creates a providerReport for every member to have a service this week.

   * @param providerNums is a list of every member number for every member in the system
   */
  public boolean createProviderReports(List<String> providerNums) {
    //TODO: ensure Member has a method "public Member getMember(int membNum)"
    //TODO: add in functionality to put all member reports in a seperate folder
    boolean invalidReport = false;
    ProviderList provList;
    try {
      provList = ProviderList.instance();
    } catch (ClassNotFoundException e1) {
      System.out.println("Could not access provider report.");
      return false;
    } catch (IOException e1) {
      System.out.println("Could not access provider report.");
      return false;
    }
       
    //Getting the current week number
    String today = this.getCurrentTime().substring(6, 10) + "-" + this.getCurrentTime().substring(0, 5);
    int weekNum = findWeekNum(today);
    
    for (int i = 0; i < providerNums.size(); i++) {
      boolean isFirstReport = true;
      
      //Getting current member
      String thisProviderNum = providerNums.get(i);
      Provider thisProvider = provList.getProvider(thisProviderNum);
      
      //Getting the list of services for this member
      ArrayList<String> providerServicesArrayList = getServices(thisProviderNum, false);
      //TODO: sort by service date
      if (providerServicesArrayList.isEmpty()) continue; // Means this member had no services this week
      String[] providerServices = new String[providerServicesArrayList.size()];
      for (int j = 0; j < providerServicesArrayList.size(); j++) {
        providerServices[j] = providerServicesArrayList.get(j);
      }
  
      //Finding number of services this week
      int weekServiceCount = 0;
      for (int j = 0; j < providerServices.length; j++) {
        if (providerServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
          weekServiceCount ++;
        }
      }
  
      if (weekServiceCount == 0) continue; // Means this member had no services this week
      
      String folderName = "";
      if (isFirstReport) {
        isFirstReport = false;
        folderName = "Provider Reports " + today;
        boolean createdFolder = createFolder(folderName);
        //if (!createdFolder) System.out.println("Could not create folder.");
        //return false;
      }
      
      //Creating text document for this user
      String thisProviderName = thisProvider.GetProviderName();
      String thisProvFileName = "./" + folderName + "/" + thisProviderName + " " + today + ".txt";
      
      boolean worked = this.createFile(thisProvFileName);
      if (! worked) {
        System.out.println("Could not create Provider report: " + thisProviderName);
        invalidReport = true;
      }
      
      //Writing to already created file
      try {
        FileWriter myWriter = new FileWriter(thisProvFileName, false); // An overload. Passing false means we replace the file contents
        myWriter.write(thisProviderName + "\n");
        myWriter.write("Provider Number: " + thisProviderNum + "\n");
        myWriter.write(thisProvider.GetProviderAddress() + "\n");
        myWriter.write(thisProvider.GetProviderCity().trim() + ", " + thisProvider.GetProviderState().trim() + thisProvider.GetProviderZip().trim() + "\n");
        myWriter.write("\n");
        myWriter.write("\n");
        
        //TODO: add individual service information
        int thisServCount = 1;
        double totalCost = 0;
        for (int j = 0; j < providerServices.length; j++) {
          if (providerServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
            myWriter.write("Service number " + thisServCount + "\n");
            myWriter.write("\n");
            myWriter.write("Service date: " + providerServices[j].substring(19, 29) + "\n");
            myWriter.write("Received by ChocAn servers on " + providerServices[j].substring(0, 19) + "\n");
            
            MemberList m;
            try {
              m = MemberList.instance();
            } catch (ClassNotFoundException e) {
              // TODO Auto-generated catch block
              //System.out.println("Could not find member.");
              return false;
            }
            String memberName = m.getMember(providerServices[j].substring(38, 47)).getName();
            
            myWriter.write("Member Name: " + memberName + "\n"); 
            myWriter.write("Member number: " + providerServices[j].substring(38, 47) + "\n");
            myWriter.write("Service code: " + providerServices[j].substring(47, 53) + "\n");
            ServiceNameDatabase s = new ServiceNameDatabase();
            double fee = Double.valueOf(s.getServiceFee(providerServices[j].substring(47, 53)));
            myWriter.write("Fee paid: $" + String.format("%.2f", fee) + "\n"); // Potential source of error
            myWriter.write("\n");
            thisServCount ++;
            totalCost += fee;
            //Increment total cost to increase by fee
          }
        }
        
        myWriter.write("\n\n");
        myWriter.write("Total consultations provided this week: " + (thisServCount - 1) + "\n");
        myWriter.write("Total fee for the week: $" + String.format("%.2f", totalCost) + "\n");
        
        myWriter.close();
        
        
      } catch (IOException e) {
        System.out.println("An error occurred.");
        invalidReport = true;
        //e.printStackTrace();
      }      
    }
    return (! invalidReport);
    
  }
  
  public boolean createSummaryReport(List<String> providerNums) {
    //TODO: ensure Member has a method "public Member getMember(int membNum)"
    boolean invalidReport = false;
    
    ProviderList provList;
    try {
      provList = ProviderList.instance();
    } catch (ClassNotFoundException e1) {
      System.out.println("Could not access provider report.");
      return false;
    } catch (IOException e1) {
      System.out.println("Could not access provider report.");
      return false;
    }
       
    //Getting the current week number
    String today = this.getCurrentTime().substring(6, 10) + "-" + this.getCurrentTime().substring(0, 5);
    int weekNum = findWeekNum(today);
    
    SimpleDateFormat reportTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Timestamp now = new Timestamp(System.currentTimeMillis());
    String reportName = "SummaryReport " + reportTime.format(now).substring(0, 8) + ".txt";
    
    boolean worked = this.createFile(reportName);
    if (! worked) {
      //System.out.println("Could not create Summary Report.");
      return false;
    }
    
    //Creating total variables
    int totalProvCount = 0;
    int totalConsultCount = 0;
    double totalFeeSum = 0.0;
       
    for (int i = 0; i < providerNums.size(); i++) {
      //Getting current member
      String thisProviderNum = providerNums.get(i);
      Provider thisProvider = provList.getProvider(thisProviderNum);
      //ProviderList
      
      //Getting the list of services for this member
      ArrayList<String> providerServicesArrayList = getServices(thisProviderNum, false);
      //TODO: sort by service date
      if (providerServicesArrayList.isEmpty()) continue; // Means this member had no services this week
      
      String[] providerServices = new String[providerServicesArrayList.size()];
      for (int j = 0; j < providerServicesArrayList.size(); j++) {
        providerServices[j] = providerServicesArrayList.get(j);
      }
  
      //Creating counting data for this provider
      int thisConsultCount = 0;
      double thisFeeSum = 0.0;
      
      //Working through this provider's data
      for (int j = 0; j < providerServices.length; j++) {        
        
        if (providerServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
          thisConsultCount ++;
          String thisServiceCode = providerServices[j].substring(47, 53);
          ServiceNameDatabase s = new ServiceNameDatabase();          
          double thisFee = s.getServiceFee(thisServiceCode);
          thisFeeSum += thisFee;
        }
      }
      
      //Updating total counts
      if (thisConsultCount != 0) totalProvCount ++; // Means this member had no services this week
      totalConsultCount += thisConsultCount;
      totalFeeSum += thisFeeSum;
      
      //Writing to this document
      try {
        FileWriter myWriter = new FileWriter(reportName, true);
        myWriter.write("Provider name: " + thisProvider.GetProviderName() + "\n");
        myWriter.write("\tNumber of services provided: " + thisConsultCount + "\n");
        myWriter.write("\tTotal fee paid to provider: " + thisFeeSum + "\n");
        myWriter.write("");
        myWriter.close();
      } catch(IOException e) {
        System.out.println("An error occurred on provider " + thisProviderNum);
        invalidReport = true;
      }  
      
     
    }
    return (! invalidReport);
  }
  
  /**
   * 
   * @param providerNums
   * @return
   */
  public boolean createEftReport(List<String> providerNums) {
    //TODO: ensure Member has a method "public Member getMember(int membNum)"
    boolean invalidReport = false;
    
    ProviderList provList;
    try {
      provList = ProviderList.instance();
    } catch (ClassNotFoundException e1) {
      System.out.println("Could not access provider report.");
      return false;
    } catch (IOException e1) {
      System.out.println("Could not access provider report.");
      return false;
    }
       
    //Getting the current week number
    String today = this.getCurrentTime().substring(6, 10) + "-" + this.getCurrentTime().substring(0, 5);
    int weekNum = findWeekNum(today);
    
    SimpleDateFormat reportTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Timestamp now = new Timestamp(System.currentTimeMillis());
    String reportName = "EFT Report " + reportTime.format(now).substring(0, 8) + ".txt";
    
    boolean worked = this.createFile(reportName);
    if (! worked) {
      System.out.println("Could not create EFT Report.");
      return false;
    }
    
    //Creating total variables
    int totalProvCount = 0;
    int totalConsultCount = 0;
    double totalFeeSum = 0.0;
   
    
    for (int i = 0; i < providerNums.size(); i++) {
      //Getting current member
      String thisProviderNum = providerNums.get(i);
      Provider thisProvider = provList.getProvider(thisProviderNum);
      //ProviderList
      
      //Getting the list of services for this member
      ArrayList<String> providerServicesArrayList = getServices(thisProviderNum, false);
      //TODO: sort by service date
      if (providerServicesArrayList.isEmpty()) continue; // Means this member had no services this week
      String[] providerServices = new String[providerServicesArrayList.size()];
      for (int j = 0; j < providerServicesArrayList.size(); j++) {
        providerServices[j] = providerServicesArrayList.get(j);
      }
  
      //Creating counting data for this provider
      int thisConsultCount = 0;
      double thisFeeSum = 0.0;
      
      //Working through this provider's data
      for (int j = 0; j < providerServices.length; j++) {        
        
        if (providerServices[j].substring(153).trim().compareTo(Integer.toString(weekNum).trim()) == 0) {
          thisConsultCount ++;
          String thisServiceCode = providerServices[j].substring(47, 53);
          ServiceNameDatabase s = new ServiceNameDatabase();          
          double thisFee = s.getServiceFee(thisServiceCode);
          thisFeeSum += thisFee;
        }
      }
      
      //Updating total counts
      if (thisConsultCount != 0) totalProvCount ++; // Means this member had no services this week
      totalConsultCount += thisConsultCount;
      totalFeeSum += thisFeeSum;
      
      //Writing to this document
      try {
        FileWriter myWriter = new FileWriter(reportName, true);
        myWriter.write(thisProvider.GetProviderID() + "\t" + thisProvider.GetProviderName() + "\t" + thisFeeSum + "\n");
        myWriter.close();        
      } catch(IOException e) {
        System.out.println("An error occurred on provider " + thisProviderNum);
        invalidReport = true;
      }  
      
     
    }
    return (! invalidReport);
  }
  

  
}