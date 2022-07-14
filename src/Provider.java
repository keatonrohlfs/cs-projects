/**
 * Class to construct provider's and their data
 * Main functionality of the Provider's ability to bill for their services, call Provider Directory, and validate a member
 * 
 * 
 * @author Quillen Flanigan
 * @version 1.0
 * 
 * 
 */


import java.io.File;
import java.io.IOException;
import java.io.Serializable;



//all private data for each provider

public class Provider implements Serializable{
	 /**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	 private String providerEmail;
	 private String providerPassword;
	 private String providerID;
	 private String providerAddress;
	 private String providerCity;
	 private String providerState;
	 private String providerZip;
	 private String providerName;
//	 private String filename;
	
	 //empty constructor for provider
	 
	 /**
	  * Empty Constructor. Initializes private data to " " just to be able to access provider methods.
	  */
	 public Provider() {
		 providerEmail=" ";
		 providerPassword=" ";
		 providerID=" ";
		 providerAddress=" ";
		 providerCity=" ";
		 providerState=" ";
		 providerZip=" ";
		 providerName=" ";
		 //filename=" ";
		 
	 }
	 
	 
	 //constructor for provider class that sets each private data member equal to the true Provider data
	 
	 /**
	  * Constructor for provider that sets private data to true provider data
	  * @param email
	  * @param pass
	  * @param Number
	  * @param address
	  * @param city
	  * @param state
	  * @param zip
	  * @param name
	  */
	 public Provider(String email, String pass, String Number, String address, String city, String state, String zip, String name) {
		  this.providerEmail=email;
		  this.providerPassword=pass;
		  this.providerID=Number;
		  this.providerAddress=address;
		  this.providerCity=city;
		  this.providerState=state;
		  this.providerZip=zip;
		  this.providerName=name;
	 }
	
	 //get/set functions for each private provider variable to be accessed in other methods.
	 public String GetProviderEmail() {
		  return this.providerEmail;
	  }
	 public void SetProviderEmail(String email) {
		  this.providerEmail=email;
	 }
	
	 public String GetProviderPassword() {
		  return this.providerPassword;
	 }
	
	 public void SetProviderPassword(String password) {
		  this.providerPassword=password;
	 }
	
	 public String GetProviderID() {
		  return this.providerID;
	 }
	
	 public void SetProviderID(String number) {
		  this.providerID=number;
	 }
	
	 public String GetProviderAddress() {
		  return this.providerAddress;
	 }
	
	 public void SetProviderAddress(String address) {
		  this.providerAddress=address;
	 }
	
	 public String GetProviderCity() {
		  return this.providerCity;
	 }
	
	 public void SetProviderCity(String city) {
		  this.providerCity=city;
	 }
	
	 public String GetProviderState() {
		  return this.providerState;
	 }
	
	 public void SetProviderState(String state) {
		  this.providerState=state;
	 }
	
	 public String GetProviderZip() {
		  return this.providerZip;
	 }
	
	 public void SetProviderZip(String zip) {
		  this.providerZip=zip;
	 }
	
	 public String GetProviderName() {
		  return this.providerName;
	 }
	
	 public void SetProviderName(String name) {
		  this.providerName=name;
	 }
	 
	 /**
	  * Method for a provider to validate the member at hand. 
	  * Gets passed a member, creates an empty member list to access memberList methods, checks member status
	  * @param member
	  * @return
	  */
	 
	
	 public boolean ValidateMember(Member member) {
		MemberList m;
    try {
      m = MemberList.instance();
    } catch (ClassNotFoundException e) {
      System.out.println("Could not create Member List.");
      return false;
    } catch (IOException e) {
      System.out.println("Could not create Member List.");
      return false;
    }
		if (m.isMemberNumber(member.getNum())==true) {
			if(member.statusToString(member.getStatus())=="Active") {
				return true;
			}
			
		}
		return false;
	 }
	 
	/**
	 * Creates service that was provided by Provider, adds it to service list.
	 * Service used to bill ChocAn for their provided services.
	 * @param serviceDate
	 * @param memberNumber
	 * @param serviceCode
	 * @param comments
	 * @return
	 */
	 public boolean BillChocAn(String serviceDate, String memberNumber, String serviceCode, String comments) {
	   Service thisService = new Service(serviceDate, this.providerID, memberNumber, serviceCode, comments);
	   ServiceList servList = new ServiceList();
	   boolean worked = servList.addService(thisService);
	   return worked;
		
	 }
	
	 public void ProviderDirectory() {
		  this.getProviderDirectory();
	 }
	
//	 public void GenerateReport() {
//		  this.filename = filename.trim();
//	   if (! filename.endsWith(".txt")) {
//	     filename += ".txt";
//	     System.out.println("Adding a '.txt' to the end of filename");
//	   }
//
//	   //TODO: add check if filename is some other file type
//
//	   //Creating empty file
//	   try {
//	     File myObj = new File(filename);
//	     if (myObj.createNewFile()) {
//	       System.out.println("File created: " + myObj.getName());
//	     } else {
//	       System.out.println("File already exists in program. File was not added successfully");
//	     }
//	   } catch (IOException e) { //TODO: update catch statement to account for expected errors
//	     System.out.println("An error occurred.");
//	     e.printStackTrace();
//	   }
//	   //Updating this ProviderList's file name
//       //this.filename = filename;
//	 }
	 
	 /**
	  * Method to print out all the private data of a given Provider.
	  * @param provider
	  */
	 
	 public void PrintProviderData(Provider provider) {
		  System.out.println("Provider Name: " + provider.GetProviderName());
		  System.out.println("Provider Email: " + provider.GetProviderEmail());
		  System.out.println("Provider Password: " + provider.GetProviderPassword());
		  System.out.println("Provider ID: " + provider.GetProviderID());
		  System.out.println("Provider Address: " + provider.GetProviderAddress());
		  System.out.println("Provider City: " + provider.GetProviderCity());
		  System.out.println("Provider State: " + provider.GetProviderState());
		  System.out.println("Provider Zip code: " + provider.GetProviderZip() + "\n");
	 }
	 
	 /**
	  * Provider Directory is list of provider's and their services/service codes.
	  * Creates a new ServiceNameDatabase and uses it's method to create a new directory through the service classes.
	  */
	 
	 public void getProviderDirectory() {
		 ServiceNameDatabase s= new ServiceNameDatabase();
		 s.createProviderDirectory();
	 }
	 
	 /**
	  * Method to find a provider in a provider List given a Provider id.
	  * If nothing is found in the provider list, a new empty provider is created and returned.
	  * @param id
	  * @return
	  */
	 @Override
	 // the toString method for Serializable save file and the formatting
	 public String toString() { 
		 return "Provider [Provider Email=" + providerEmail + ", Provider Password=" + providerPassword + ", Provider ID=" + providerID + ", Provider Address=" + providerAddress + ", Provider City=" + providerCity + ", Provider State=" + providerState + ", Provider Zip=" + providerZip + ", Provider Name=" + providerName + "]";
	 }
	 
	 public Provider GetProvider(String id) {
	   
	   Provider empty=new Provider();
		 
		 ProviderList pList;
		    try {
		      pList = ProviderList.instance();
		    } catch (ClassNotFoundException e) {
		      System.out.println("Could not create Provider List.");
		      return empty;
		    } catch (IOException e) {
		      System.out.println("Could not create Provider List.");
		      return empty;
		    }
		    
		 for (int i=0; i<pList.providerList.size(); i++) {
			 if (pList.providerList.get(i).GetProviderID().equals(id)) {
				 return pList.providerList.get(i);
			 }
	  }
   return empty;
		 
  }
	 
}