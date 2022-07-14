/**
 * Contains the data from any given service provided to a member by a provider
 * Service constructor is called in provider, and Service data is stored in ServiceList
 * Functions only as a buffer between the .txt file data is stored in and the users accessing ServiceList
 * 

 * @author Liam Tucker
 * @version 1.0
 *
 */

//TODO: Figure out how to check if comments are valid. I don't think I can check if the data is valid inside my constructor.
// If I call the constructor from another message, then I've got to return a Service, but I don't want my user to need to check if the Service is valid
// I also don't want users to need to check in advance if the data is valid
public class Service {
	
  private String currTime; // In form MM–DD–YYYY HH:MM:SS
  private String servDate; // In form MM-DD-YYYY
  private String provNum;
  private String membNum;
  private String servCode;
  private String comments;
  private int weekNum;
	
  //Empty constructor
  /**
   * Empty constructor. Initializes a Service with the default characters. m m  ,m
   */
  public Service() {
    currTime = "MM–DD–YYYY HH:MM:SS"; // 19 spaces
    servDate = "MM-DD-YYYY"; // 10 spaces
    provNum = "123456789"; // 9 spaces
    membNum = "123456789"; // 9 spaces
    servCode = "123456"; // 6 spaces next line has 100 chars
    comments = "                                                                                                    "; // 100 spaces	
    weekNum = -1;
  }

  //Constructor w/o comments w/o currTime
  /**
  * Creates a Service w/o comments and w/o currTime.

  * @param servDate is in format MM-DD-YYYY
  * @param provNum is in format 12345678
  * @param membNum is in format 123456789
  * @param servCode is in format 123456
   */
  public Service(String servDate, String provNum, String membNum, String servCode) {
    this.currTime = "MM–DD–YYYY HH:MM:SS";
    this.servDate = servDate;
    this.provNum = provNum;
    this.membNum = membNum;
    this.servCode = servCode; // next line has 100 chars
    this.comments = "                                                                                                   "; // 100 spaces
    this.weekNum = -1;
  }
 
  //Constructor w/o comments w/o currTime
  public Service(String servDate, String provNum, String membNum, String servCode, String comments) {
    this.currTime = "MM–DD–YYYY HH:MM:SS";
    this.servDate = servDate;
    this.provNum = provNum;
    this.membNum = membNum;
    this.servCode = servCode;
    this.comments = comments;
    this.weekNum = -1;
  }
	
  //Constructor w/ comments w/ currTime
	 public Service(String currTime, String servDate, String provNum, String membNum, String servCode, String comments) {
    this.currTime = currTime;
    this.servDate = servDate;
    this.provNum = provNum;
    this.membNum = membNum;
    this.servCode = servCode;
    this.comments = comments;
    this.weekNum = -1;
  }
	 
  //Constructor w/ comments w/ currTime and w/ WeekNum
  public Service(String currTime, String servDate, String provNum, String membNum, String servCode, String comments, int weekNum) {
    this.currTime = currTime;
    this.servDate = servDate;
    this.provNum = provNum;
    this.membNum = membNum;
    this.servCode = servCode;
    this.comments = comments;
    this.weekNum = weekNum;
  }	 
	
  //Getters and Setters. May not be used at all
  public String getCurrTime() {
    return this.currTime;
  }
  
  public void setCurrTime(String currTime) {
    this.currTime = currTime;
  }
  
  public String getServDate() {
    return this.servDate;
  }

  public void setServDate(String servDate) {
    this.servDate = servDate;
  }

  public String getProvNum() {
    return this.provNum;
  }

  public void setProvNum(String provNum) {
    this.provNum = provNum;
  }

  public String getMembNum() {
    return this.membNum;
  }
  
  public void setMembNum(String membNum) {
    this.membNum = membNum;
  }

  public String getServCode() {
    return this.servCode;
  }

  public void setServCode(String servCode) {
    this.servCode = servCode;
  }

  public String getComments() {
    return this.comments;
  }
  
  public void setComments(String comments) {
    this.comments = comments;
  }
  public int getWeekNum() {
    return this.weekNum;
  }
  public void setWeekNum(int weekNum) {
    this.weekNum = weekNum;
  }

	
  //TODO: (possibly update to check form of data (i.e. 'Jan 4 2022' should not be valid)
  /**Checks if all of the data is the proper length.

  * @param servDate is in format MM-DD-YYYY
  * @param provNum is in format 12345678
  * @param membNum is in format 123456789
  * @param servCode is in format 123456
  * @return errorMessage
  */
  public String validService(String servDate, String provNum, String membNum, String servCode) {
    String errorMessage = "";
    //Checking if servDate is valid
    if (servDate.length() != 10) {
      errorMessage += "Invalid service date; Service date must be in the form 'MM-DD-YYYY'. ";
    }
    //Checking if provNum is valid
    if (provNum.length() != 9) {
      errorMessage += "Invalid Provider Number; Provider Number must be in the form 123456789. ";
    }
    //Checking if membNum is valid
    if (membNum.length() != 9) {
      errorMessage += "Invalid Member Number; Member Number must be in the form 123456789. ";
    }
    //Checking if servCode is valid
    if (servCode.length() != 6) {
      errorMessage += "Invalid Service Code; Service Code must be in the form 123456. ";
    }
    return errorMessage; // At end of validating the potential service
  }
	
  //TODO: (possibly update to check form of data (i.e. 'Jan 4 2022' should not be valid)
  /**Checks if all of the data is the proper length.

  * @param servDate is in format MM-DD-YYYY
  * @param provNum is in format 12345678
  * @param membNum is in format 123456789
  * @param servCode is in format 123456
  * @param comments is at most 100 characters long
  * @return errorMessage
  */
  public String validService(String servDate, String provNum, String membNum, String servCode, String comments) {
    String errorMessage = "";
    //Checking if servDate is valid
    if (servDate.length() != 10) {
      errorMessage += "Invalid service date; Service date must be in the form 'MM-DD-YYYY'. ";
    }
    //Checking if provNum is valid
    if (provNum.length() != 9) {
      errorMessage += "Invalid Provider Number; Provider Number must be in the form 123456789. ";
    }
    //Checking if membNum is valid
    if (membNum.length() != 9) {
      errorMessage += "Invalid Member Number; Member Number must be in the form 123456789. ";
    }
    //Checking if servCode is valid
    if (servCode.length() != 6) {
      errorMessage += "Invalid Service Code; Service Code must be in the form 123456. ";
    }
    if (comments.length() > 100) {
      errorMessage += "Invalid comments; comments must be at most 100 characters long. ";
    }
    return errorMessage; // At end of validating the potential service
  }	

  /**
   * Prints the Service's information, on different lines.
   */
  public void print() {
    System.out.println("Date entered: " + currTime);
    System.out.println("Date of service: " + servDate);
    System.out.println("Provider number: " + provNum);
    System.out.println("Member number: " + membNum);
    System.out.println("Service code: " + servCode);
    System.out.println("Comments (optional): " + comments);
  }

  public boolean equals(Service s) {
    return s.currTime == this.currTime && s.servDate == this.servDate && s.provNum == this.provNum && s.membNum == this.membNum
      && s.servCode == this.servCode && s.comments == this.comments;
  }
}
