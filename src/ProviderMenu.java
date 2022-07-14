import java.io.IOException;

/**
 * The menu prompting providers to choose a use case

 * @author letucker
 * @version 1.0
 *
 */
public class ProviderMenu {
  
  private Provider provider;
  SingletonScanner scan = SingletonScanner.getInstance();
  
  public ProviderMenu(Provider p) {
    this.provider = p;
//    this.scan = new Scanner(System.in);
  }
  
  public Provider getProvider() {
    return this.provider;
  }
  
  public void mainMenu() {
    
    while (true) { // will return when we're done, to return to main menu
      //Printing options
      System.out.println("Welcome, " + provider.GetProviderName().trim() + "!");
      System.out.println("");
      System.out.println("1: Validate Member");
      System.out.println("2: Bill ChocAn:");
      System.out.println("3: Request Provider Directory");
      System.out.println("4: Logout");
      System.out.println("");
      System.out.println("Which action would you like to perform?");
      
      //reading in response
      String response = scan.next().trim();
      if (response.length() == 0) continue;
      response = response.substring(0, 1);
      
      switch (response) {
        
        case "1":
          ValidateMemberMenu();
          continue;
        case "2":
          BillChocAnMenu();
          continue;
        case "3":
          RequestProviderDirectoryMenu();
          continue;
        case "4":
          System.out.println("Logged out successfully.");
          return;
        default:
          System.out.println("Invalid entry: " + response + ". Must enter either '1', '2', '3', or '4'.");
          System.out.println("Press ENTER to continue.");
          String temp = scan.next();
          
      } 
    } 
  }
  
  public void ValidateMemberMenu() {
    while (true) {
      System.out.println("Please enter the member number: ");
      String response = scan.next().trim();
      if (response.length() != 9) {
        System.out.println("Invalid member number. Must be 9 characters long.");
        System.out.println("Press ENTER to continue.");
        String temp = scan.next();
        return;
      }
      MemberList m;
      try {
        m = MemberList.instance();
      } catch (ClassNotFoundException e) {
        System.out.println("Could not create Member List.");
        continue;
      } catch (IOException e) {
        System.out.println("Could not create Member List.");
        continue;
      }
      Member thisMemb = m.getMember(response);
      if (thisMemb.getName() == "") {
        System.out.println("Member " + response + " not found.");
        System.out.println("Press ENTER to continue.");
        String temp = scan.next();
        return;
      }
      
      //TODO: refactor because there are really three possibilities: member is active, suspended, or does not exist 
      if (thisMemb.getStatus()) {
      //if (1 == 1) {
        System.out.println("Member " + response + " is active.");
      }
      else {
        System.out.println("Member " + response + " is suspended.");
      }  
      System.out.println("Press ENTER to continue.");
      String temp = scan.next();
      return;
    }
    
    //TODO: implement this;
  }
  
  public void BillChocAnMenu() {
    
    //Initializing variables
    String date;
    String membNum;
    String serviceCode;
    String comments;
    double fee = 0;
    while (true) {
      System.out.println("Please enter the member number from the service.");
      membNum = scan.next().trim();
      
      ProviderList provList;
      try {
        provList = ProviderList.instance();
      } catch (ClassNotFoundException e1) {
        System.out.println("ERROR: Could not access provider report.");
        System.out.println("Press ENTER to continue.");
        String temp = scan.next();
        return;
      } catch (IOException e1) {
        System.out.println("ERROR: Could not access provider report.");
        System.out.println("Press ENTER to continue.");
        String temp = scan.next();
        return;
      }
      MemberList membList;
      try {
        membList = MemberList.instance();
      } catch (ClassNotFoundException e) {
        System.out.println("Could not access member list.");
        return;
      } catch (IOException e) {
        System.out.println("Could not access member list.");
        return;
      }
      Member memb = membList.getMember(membNum);
      //if (provList.IsProvider(membNum) {
      if (this.provider.ValidateMember(memb)) {
        System.out.println("Member " + membNum + " is valid.");
        break;
      }
      System.out.println("Invalid member number: " + membNum + ". Please try again.");
    }
    while (true) {
      System.out.println("Please enter the date of service in the form MM-DD-YYYY.");
      date = scan.next().trim();
      if (date.length() == 10) break; // Not checking if format is right
      System.out.println("Invalid date of service: should be in the form MM-DD-YYYY.");
      //System.out.println("Len: " + date.length() + " Date: " + date);
    }
    while (true) {
      System.out.println("Please enter the service code.");
      serviceCode = scan.next().trim();
      if (serviceCode.length() == 6) {
        ServiceNameDatabase servDB = new ServiceNameDatabase();
        if (! servDB.isValidService(serviceCode)) {
          System.out.println("Invalid service code: " + serviceCode + ". Please try again.");
          continue;
        }
        String serviceName = servDB.getServiceName(serviceCode);
        System.out.println("Is the following service name correct? (Y/N)");
        System.out.println(serviceName);
        String isCorrect = scan.next().trim();//.substring(0, 1).toUpperCase();
        if (isCorrect.compareTo("Y") == 0) {
          //scan.close();
          fee = servDB.getServiceFee(serviceCode);
          break;
        }
        continue;
      }
      System.out.println("Invalid service code. Must be six digits long.");
    }
    while (true) {
      System.out.println("(Optional) Enter comments from the service (up to 100 characters).");
      comments = scan.next();
      if (comments.length() > 100) comments = comments.substring(0, 100);
      break;
    }
    
    boolean worked = provider.BillChocAn(date, membNum, serviceCode, comments);
    if (worked) {
      System.out.println("Successfully billed ChocAn for the following service: \n");
      ServiceList servList = new ServiceList();
      servList.getService(membNum, date).print();
      System.out.println("Service fee: " + fee);
      System.out.println("");
      
      System.out.println("Press ENTER to continue.");
      String temp = scan.next();
      return;
    }
    System.out.println("Could not bill ChocAn. Press ENTER to continue...");
    String temp = scan.next();
    return;
    
  }
  
  public void RequestProviderDirectoryMenu() {
    Provider p = new Provider();
    p.getProviderDirectory();
    System.out.println("Provider Directory has been created.");
    System.out.println("Press ENTER to continue.");
    String temp = scan.next();
    return;
  }  
  
  /*
  public static void main(String[] args) throws Throwable {
		ProviderList prov = ProviderList.instance();
		Provider p = new Provider("kim@example", "kimPass", "123456789", "1 kim st", "Tuscsa", "AL", "32748", "Kim");
		prov.addProvider(p);
		ProviderList prov2 = prov.load();
		prov.save();
		System.out.println("\nAfter the load\n");
		prov2.PrintProviderList();
		prov2.addProvider(new Provider());
		ProviderMenu myMenu = new ProviderMenu(p);
		myMenu.mainMenu();
	}
*/
}
