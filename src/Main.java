import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    
    ServiceList s = new ServiceList();
    System.out.println(s.findWeekNum("2022-03-27"));
    System.out.println(s.findWeekNum("2022-03-11"));
    System.out.println(s.findWeekNum("2022-04-28"));
    //System.out.println(s.findWeekNum("03-27-2022"));
    Provider p = new Provider();
    Provider prov0 = new Provider("liamt2003@yahoo.com", "password", "123454321", "14355 Main St.", "Vernon Hills", "NY", "83001", "Liam T");
    System.out.println(p.toString());
    System.out.println(prov0.toString());
    
    // TODO Auto-generated method stub
    //System.out.println("Hello World!");
    
    //ServiceTestMain s = new ServiceTestMain();
    //ServiceList servList = s.loadServiceList();
    //ServiceNameDatabase servNameDB = s.loadServiceNameDB();
    
//    ProviderList testProvList;
//    try {
//     testProvList = ProviderList.instance();
//    } catch (ClassNotFoundException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      return;
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      return;
//    }
//    
//    Provider prov0 = new Provider("merill@merill.com",     "Merill2022"      , "000000000", "2048 Dentist Drive",       "Vernon Hills",     "NY", "83001", "Merill & Merill");
//    Provider prov1 = new Provider("mto@gmail.com",         "Pass1w0rd",        "111111110", "15 S Circle Boulevard",    "Redwoods",         "CA", "13579", "Massage Therapy Org.");
//    Provider prov2 = new Provider("hypnosis@google.org",   "zzz123zzz123",     "222222220", "19741 S. Lincoln Avenue",  "Everytown",        "NM", "26535", "Google Hypnosis Department");
//    Provider prov3 = new Provider("jefferson@bakers.edu",  "cupcakeCOOKIE123", "333333330", "1 Ice Cream Cone Way",     "Dessert Land",     "LA", "66743", "Jefferson's Cupcakeria");
//    Provider prov4 = new Provider("abe@loseweight.org",    "password1953",     "444444440", "123 Henderson Road",       "Orlando",          "FL", "78398", "Abe Weight Loss");
//    Provider prov5 = new Provider("pediatrics@gmail.com",  "LFPediatrics",     "555555550", "6 N Eastward Grove",       "Toledo",           "OH", "59740", "LF Pediatrics");
//    Provider prov6 = new Provider("meg.paul@yahoo.com",    "meaghanpaulson1",  "666666660", "7 Deer Creak Avenue",      "Boise",            "ID", "26356", "Physical Training and Therapy");
//    Provider prov7 = new Provider("email@provider.com",    "myPassWord",       "777777770", "12345 Address Road",       "Chicago",          "IL", "26357", "Dentists & Allergists");
//    Provider prov8 = new Provider("net@family.net",        "familybusiness",   "888888880", "13 Lake Street Dr.",       "Orland Park",      "IL", "14578", "Therapists R US");
//    Provider prov9 = new Provider("allergies@hospital.org","nomoreallergies",  "999999990", "929 Alphabet Soup Avenue", "Trenton",          "NJ", "15003", "Allergy Testing Dpmt");
//    
//    testProvList.addProvider(prov0);
//    testProvList.addProvider(prov1);
//    testProvList.addProvider(prov2);
//    testProvList.addProvider(prov3);
//    testProvList.addProvider(prov4);
//    testProvList.addProvider(prov5);
//    testProvList.addProvider(prov6);
//    testProvList.addProvider(prov7);
//    testProvList.addProvider(prov8);
//    testProvList.addProvider(prov9);
//    System.out.println("Added all");
    
    
//    MainMenu mainMenu = new MainMenu();
//    LoadData s = new LoadData();
//    s.CreateStoredDataFolder();
//    ServiceList servList = s.loadServiceList();
//    ServiceNameDatabase servDB = s.loadServiceNameDB();
//    ProviderList provList;
//    try {
//      provList = s.loadProviderList();
//    } catch (ClassNotFoundException e1) {
//      System.out.println("Could not access provider list.");
//      return;
//    } catch (IOException e1) {
//      System.out.println("Could not access provider list.");
//      return;
//    }
//    System.out.println("Made it here.");
//    //return;
//    //ServiceNameDatabase servNameDB = s.loadServiceNameDB();
//    MemberList membList;
//    try {
//      membList = s.loadMemberList();
//    } catch (ClassNotFoundException e) {
//      System.out.println("Could not create member list");
//      return;
//    } catch (IOException e) {
//      System.out.println("Could not create member list");
//      return;
//    }
    
    
//    List<String> membIDs = membList.getMemberIDList();
//    for (int k = 0; k < membIDs.size(); k++) {
//      System.out.println(membIDs.get(k) + "\n");
//    }
//    
//    boolean mWorked = servList.createMemberReports(membIDs);
//    if (! mWorked) System.out.println("Failed to create member reports");
//    if (mWorked) System.out.println("Created member reports");
    
//    System.out.println("Made it here");
//   
//    List<String> provIDs = provList.getProviderIDs();
//    boolean worked = servList.createProviderReports(provIDs);
//    if (! worked) System.out.println("Failed to create provider reports");
//    if (worked) System.out.println("Created provider reports");
//    
//    List<String> membIDs = membList.getMemberIDList();
//    boolean workedm = servList.createMemberReports(membIDs);
//    if (! workedm) System.out.println("Failed to create member reports");
//    if (workedm) System.out.println("Created member reports");
//    
//    boolean workede = servList.createEftReport(provIDs);
//    if (! workede) System.out.println("Failed to create EFT reports");
//    if (workede) System.out.println("Created eft reports");
//    
//    boolean workeds = servList.createSummaryReport(provIDs);
//    if (! workeds) System.out.println("Failed to create summary reports");
//    if (workeds) System.out.println("Created summary reports");
    
//    System.out.println("before comparison");
//    Member blank = new Member();
//    Member memb1 = membList.getMember("111111111");
//    Member memb2 = membList.getMember("123456789");
//    if (memb1 != blank) System.out.println("Member 1 name: |" + memb1.getName());
//    if (memb2 != blank) System.out.println("Member 2 name: |" + memb2.getName());
    
//    List<String> stringList = Arrays.asList("000000001", "111111111", "222222221", "012345678");
//    System.out.println("Made it to creating member reports");
//    servList.createMemberReports(stringList);
    
//    try {
//      mainMenu.Display();
//    } catch (ClassNotFoundException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    
//    Provider liam = new Provider("letucker@crimson.ua.edu", "password", "123456789", "14355 Spring Meadow Ct", "Green Oaks", "IL", "60048", "Liam Tucker");
//    ProviderMenu t = new ProviderMenu(liam);
//    t.mainMenu();

  }

}
