import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  
	  //new MainFrame().setVisible(true);
	  
	  //ServiceList servList = loadServiceList();
	  ServiceNameDatabase servNameDB = loadServiceNameDB();
	  MemberList membList;
	  try {
      membList = loadMemberList();
    } catch (ClassNotFoundException e) {
      System.out.println("Could not create member list");
      return;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("Could not create member list");
      return;
    }
	  
	  boolean works1 = servNameDB.isValidService("111111");
	  if (works1) System.out.println("Found service code 1");
   boolean works2 = servNameDB.isValidService("123455");
   if (works2) System.out.println("Found service code 2");
   boolean works3 = servNameDB.isValidService("000000");
   if (works3) System.out.println("Found service code 3");
   boolean works4 = servNameDB.isValidService("999999");
   if (works4) System.out.println("Found service code 4");
   
   // Prints the contents of the MemberList 
   membList.print();
	  return;
	  
		  
	  /*
		Service template1 = new Service("03-27-2022", "123456789", "012345678", "123456", "This is a test comment");
		Service template2 = new Service("04-06-2022 01:21:00", "03-29-2023", "111111111", "123456789", "123456", "This is a test comment");
		Service template3 = new Service("04-11-2022", "987654321", "098765432", "123789");
		Service template4 = new Service("04-06-2022", "123454321", "987656789", "000001", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()");
		
		ServiceList testList = new ServiceList("testfile.txt");
		testList.clearFile("letucker");
		boolean createdFile = testList.createFile();
		testList.addService(template1);
		testList.addService(template3);
		testList.addService(template4);
		testList.clearFile("password");
		
		System.out.println("Searching for items");
		Service search1 = testList.getService("012345678", "03-27-2022");
		System.out.println("Searched for item 1");
		Service search2 = testList.getService("123456789", "MM-DD-YYYY");
		System.out.println("Searched for item 2");
  Service search4 = testList.getService("987656789", "04-06-2022");
  System.out.println("Searched for item 4");
		Service empty = new Service();
		if (empty.equals(search1)) System.out.println("Search 1 not found.");
		if (empty.equals(search2)) System.out.println("Search 2 not found.");
  if (empty.equals(search4)) System.out.println("Search 4 not found.");		
		

		ServiceList testList2 = new ServiceList("testfile2");
		testList2.clearFile("letucker");
		testList2.addService(template2);
		testList2.addService(template3);
		testList2.clearFile("letucker");
		testList2.addService(template2);
		
		ServiceList testList3 = new ServiceList("testfile3.pdf");
		testList3.clearFile("letucker");
		testList3.addService(template4);
		testList3.addService(template4);
		testList3.addService(template4);
		testList3.addService(template4);
		testList3.addService(template4);
		testList3.deleteFile("letucker@crimson.ua.edu");
		
		ServiceList testList3a = new ServiceList("testfile3a");
		testList3a.deleteFile("letucker");
		testList3a.addService(template4);
		testList3a.addService(template4);
		testList3a.addService(template4);
		testList3a.addService(template4);
		testList3a.addService(template4);
		testList3a.deleteFile("letucker@crimson.ua.edu");
		
		ServiceList testList4 = new ServiceList("testfile4.txt");
		//testList4.clearFile("letucker");
		testList4.addService(template2);
		testList4.deleteFile("letucker");
		*/

	}
	
	public static void CreateStoredDataFolder() {
	  File testFile = new File("");
   String currentPath = testFile.getAbsolutePath();
   File file = new File(".\\Stored Data");
   boolean bool = file.mkdir();
   if (! bool) {
     System.out.println("Could not make directory");
     return;
   }
   try {
    Files.createDirectories(Paths.get(System.getProperty("user.dir")));
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
	}
	
	public static ServiceNameDatabase loadServiceNameDB() {
  	  ServiceNameDatabase servdb = new ServiceNameDatabase();
  	  servdb.clearFile("letucker");
  	  
     servdb.addService("000002", "Physical Therapy", 25.00);
     servdb.addService("111112", "Massage Therapy", 30.00);
     servdb.addService("222222", "Dietician Session", 35.00);
     servdb.addService("333332", "Therapy Session", 55.00);
     servdb.addService("444442", "Allergy Testing", 20.00);
     servdb.addService("555552", "Cooking Lessons", 8.50); // 20 char name
     servdb.addService("666662", "Hypnotic Treatment", 5.99);
     servdb.addService("777772", "Cast Removal", 24.00);
     servdb.addService("888882", "Root Canal", 263.00);
     servdb.addService("999992", "Wisdom tooth removal", 200.00); // 20 char name
     
     return servdb;
  	}

	
	public static ServiceList loadServiceList() {
	  ServiceList servList = new ServiceList();
	  servList.clearFile("letucker");
	  
	  //                           MM-DD-YYYY    ProvNum      MembNum     ServCode  Comments
	  Service serv1 =  new Service("04-21-2022", "111111110", "000000001", "000002", "Patient should book follow-up appointments every day the next week.");
	  Service serv2 =  new Service("04-21-2022", "111111110", "111111111", "000002", "Patient's treatment is now complete.");
	  Service serv3 =  new Service("04-21-2022", "222222220", "222222221", "111112");
	  Service serv4 =  new Service("04-22-2022", "111111110", "000000001", "000002", "Session 2 of 7");
   Service serv5 =  new Service("04-23-2022", "111111110", "000000001", "000002", "Session 3 of 7");
   Service serv6 =  new Service("04-24-2022", "111111110", "000000001", "000002", "Session 4 of 7");
   Service serv7 =  new Service("04-25-2022", "111111110", "000000001", "000002", "Session 5 of 7");
   Service serv8 =  new Service("04-26-2022", "111111110", "000000001", "000002", "Session 6 of 7");
   Service serv9 =  new Service("04-27-2022", "111111110", "000000001", "000002", "Session 7 of 7");
   Service serv10 = new Service("04-27-2022", "333333330", "999999991", "333332", "Patient worried about surgery later today. Call tomorrow.");
   Service serv11 = new Service("04-27-2022", "444444440", "888888881", "444442", "No allergies.");
   Service serv12 = new Service("04-27-2022", "555555550", "777777771", "666662", "N/A");
   Service serv13 = new Service("04-27-2022", "666666660", "666666661", "888882");
   Service serv14 = new Service("04-27-2022", "333333330", "777777771", "999992", "Requesting follow-up appointment in 3 months.");
   
   servList.addService(serv1);
   servList.addService(serv2);
   servList.addService(serv3);
   servList.addService(serv4);
   servList.addService(serv5);
   servList.addService(serv6);
   servList.addService(serv7);
   servList.addService(serv8);
   servList.addService(serv9);
   servList.addService(serv10);
   servList.addService(serv11);
   servList.addService(serv12);
   servList.addService(serv13);
   
	  //Pause for 1.5 seconds
	  try {
      Thread.sleep(1500); 
    } catch (InterruptedException e) {
      System.out.println("Could not pause for 1.5 seconds.");
    }
	  
   servList.addService(serv14);
	  
	  return servList;
	}
	
	 public static MemberList loadMemberList() throws ClassNotFoundException, IOException {
	   MemberList membList = MemberList.instance();
	   System.out.println("Created instance");
	   
	   //                         Name                         Email Address                 MemberNum     Address                           City       State   ZIP    status
	   Member memb0 = new Member("Liam Tucker              ", "letucker2003@gmail.com    ", "000000001", "14355 Spring Meadow Court", "Vil Green Oaks", "IL", "60048", true);
	   Member memb1 = new Member("Desigamoorthy Shan Nainar", "d.n@lhswildcats.org",        "111111111", "123 Main St.",              "Libertyville",   "IL", "60045", true);
	   Member memb2 = new Member("Katherine Cannon",          "katherine.cannon@gmail.com", "222222221", "535 Second Ave.",           "Lincoln",        "CA", "15000", true);
	   Member memb3 = new Member("Ed Bread",                  "ed.bread@yahoo.com",         "333333331", "1000 Grand Ave.",           "Everytown",      "AK", "12345", false);
	   Member memb4 = new Member("Marie Curie",               "mcurie@hotmail.com",         "444444441", "15 S Main Blvd.",           "Tuscaloosa",     "SC", "34543", true);
	   Member memb5 = new Member("Jen Bacon",                 "jb@gmail.com",               "555555551", "3600 Stonebrook Road",      "Chicago",        "OK", "82524", true);
	   Member memb6 = new Member("Nick Saban",                "sabanemail@ua.edu",          "666666661", "1 UA Way",                  "Northport",      "AL", "34567", true);
	   Member memb7 = new Member("Mrs. Claus",                "claus@google.org",           "777777771", "1500 Lincoln Av",           "Tuscaloosa",     "NV", "86468", true);
	   Member memb8 = new Member("Jodie Zhang",               "jodie.zhang@bcu.org",        "888888881", "42100 Alphabet Drive",      "Chicago",        "OH", "74001", false);
	   Member memb9 = new Member("Haig Ingino",               "hingino@stanford.edu",       "999999991", "8 Cali Road",               "Northport",      "GA", "19001", true);    
	   System.out.println("Created members");
	   membList.addMember(memb0);
	   System.out.println("Added first member");
	   membList.addMember(memb1);
	   membList.addMember(memb2);
	   membList.addMember(memb3);
	   membList.addMember(memb4);
	   membList.addMember(memb5);
	   membList.addMember(memb6);
	   membList.addMember(memb7);
	   membList.addMember(memb8);
	   membList.addMember(memb9);
	   return membList;
	 }

	 
	 public static ProviderList loadProviderList() throws ClassNotFoundException, IOException {
	   
	   //System.out.println("Before creating provider list");
	   ProviderList provList = ProviderList.instance();
	   //System.out.println("Accessed provider list");
	   //provList.clearFile("qcflanigan");
	   
	   //                               Email                    pass                  id            address                 city              state   zip         name
	   Provider prov0 = new Provider("merill@merill.com",     "Merill2022"      , "000000000", "2048 Dentist Drive",       "Vernon Hills",     "NY", "83001", "Merill & Merill");
	   Provider prov1 = new Provider("mto@gmail.com",         "Pass1w0rd",        "111111110", "15 S Circle Boulevard",    "Redwoods",         "CA", "13579", "Massage Therapy Org.");
	   Provider prov2 = new Provider("hypnosis@google.org",   "zzz123zzz123",     "222222220", "19741 S. Lincoln Avenue",  "Everytown",        "NM", "26535", "Google Hypnosis Department");
	   Provider prov3 = new Provider("jefferson@bakers.edu",  "cupcakeCOOKIE123", "333333330", "1 Ice Cream Cone Way",     "Dessert Land",     "LA", "66743", "Jefferson's Cupcakeria");
	   Provider prov4 = new Provider("abe@loseweight.org",    "password1953",     "444444440", "123 Henderson Road",       "Orlando",          "FL", "78398", "Abe Weight Loss");
	   Provider prov5 = new Provider("pediatrics@gmail.com",  "LFPediatrics",     "555555550", "6 N Eastward Grove",       "Toledo",           "OH", "59740", "LF Pediatrics");
	   Provider prov6 = new Provider("meg.paul@yahoo.com",    "meaghanpaulson1",  "666666660", "7 Deer Creak Avenue",      "Boise",            "ID", "26356", "Physical Training and Therapy");
	   Provider prov7 = new Provider("email@provider.com",    "myPassWord",       "777777770", "12345 Address Road",       "Chicago",          "IL", "26357", "Dentists & Allergists");
	   Provider prov8 = new Provider("net@family.net",        "familybusiness",   "888888880", "13 Lake Street Dr.",       "Orland Park",      "IL", "14578", "Therapists R US");
	   Provider prov9 = new Provider("allergies@hospital.org","nomoreallergies",  "999999990", "929 Alphabet Soup Avenue", "Trenton",          "NJ", "15003", "Allergy Testing Dpmt");

	   System.out.println("Created providers.");
	   provList.addProvider(prov0);
	   System.out.println("Added first provider");
	   provList.addProvider(prov1);
	   provList.addProvider(prov2);
	   provList.addProvider(prov3);
	   provList.addProvider(prov4);
	   provList.addProvider(prov5);
	   provList.addProvider(prov6);
	   provList.addProvider(prov7);
	   provList.addProvider(prov8);
	   provList.addProvider(prov9);
	   
	   System.out.println("About to return provList");
	   return provList;
	 }
	 
	 public static OperatorList loadOperatorList() throws ClassNotFoundException, IOException {
	   OperatorList opList = OperatorList.instance();
	   
	   Operator liam = new Operator("letucker@crimson.ua.edu", "password");
	   opList.addOperator(liam);
	   return opList;
	 }
	 
	 public static ManagerList loadManagerList() throws ClassNotFoundException, IOException {
	   ManagerList mgList = ManagerList.instance();
	   Manager liam = new Manager("letucker@crimson.ua.edu", "password");
	   mgList.addManager(liam);
	   
	   return mgList;
	 }
	 
	 
}