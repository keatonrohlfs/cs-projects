
import java.io.IOException;

/**
 * Menu that verifies if current user is a Provider, Manager, or ChocAn Operator
 * Main Menu can call Manager Menu, Provider Menu, and Operator Menu Main Menu
 * can access data from Manager List, Provider List, and Operator List
 * 
 * 
 * @author Deondre North
 * @version 1.0
 *
 */
//TODO:can generate report before logging in
//call generate reports as needed by button press perhaps?
// give option to exit

public class MainMenu {
	SingletonScanner scan = SingletonScanner.getInstance();
	public MainMenu() {
	    
	}

	/**
	 * Function to prompt user to enter a Key to decided use case of system
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean Display() throws ClassNotFoundException, IOException {
		System.out.println("Welcome to the ChocAn Data Center");
		System.out.println("");
		System.out.println(
				"Press [1] for Manager Login \n"
				+ "Press [2] for Operator Login \n"
				+ "Press [3] for Provider Login \n"
				+ "Press [4] to Generate All Reports \n"
				+ "Press [5] to Close the system");
		char userInput = scan.next().charAt(0);
		SelectUserType(userInput);
		scan.close();
		if (userInput == '5') {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Function that allows users to enter their ID and password to be verified as a
	 * provider
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean ProviderLogin() throws ClassNotFoundException, IOException {
		System.out.println("Please Enter Your Provider ID:");
		String ID = scan.next().trim(); 
		System.out.println("Please Enter Your Password:");
		String password = scan.next().trim();
		ProviderList currProvList = ProviderList.instance();
		if (currProvList.IsProvider(ID) == true && currProvList.isProvPassword(password) == true) {
			//myread.close();
			Provider currProv = currProvList.getProvider(ID);
			ProviderMenu currProvMenu = new ProviderMenu(currProv);
			currProvMenu.mainMenu();
			scan.close();
			return true;
		}
		else {
			System.out.println("Login Failed");
			scan.close();
			return false;
		}
	}

	/**
	 * Function that allows users to enter their email and password to be verified
	 * as a manager
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean ManagerLogin() throws ClassNotFoundException, IOException {
		System.out.println("Please Enter Your Email address:");
		String email = scan.next().trim();
		System.out.println("Please Enter Your Password:");
		String password = scan.next().trim();
		ManagerList currManList = ManagerList.instance();
		if (currManList.isManagerEmail(email) == true && currManList.isManagerPassword(password) == true) { // returnsismanager == true
			Manager currMan = new Manager(email, password);
			ManagerMenu myMenu = new ManagerMenu(currMan);
			myMenu.Display();
			scan.close();
			return true;
		} else {
			System.out.println("Login Failed");
			scan.close();
			return false;
		}

	}

	/**
	 * Function that allows users to enter their email and password to be verified
	 * as a
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean OperatorLogin() throws ClassNotFoundException, IOException {
		System.out.println("Please Enter Your Email Address:");
		//Scanner myread = new Scanner(System.in);
		String email = scan.next().trim();
		System.out.println("Please Enter Your Password:");
		String password = scan.next().trim();
		
		OperatorList currOpList = OperatorList.instance();
		if (currOpList.isOperatorEmail(email) == true && currOpList.isOperatorPassword(password) == true) { // returnsismanager == true
			Operator currOp = new Operator(email, password);
			OperatorMenu myOpMenu = new OperatorMenu(currOp);
			myOpMenu.OpMenu();
			scan.close();
			return true;
		} 
		else {
			System.out.println("Login Failed");
			scan.close();
			return false;
		}
		
	}

	/**
	 * Function that accepts either a 'M', 'O', 'P', '1', or '*'
	 * for input that decides the type of user
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void SelectUserType(char userType) throws ClassNotFoundException, IOException {
		Manager clock = new Manager();
		char userTypeUpper = Character.toUpperCase(userType);
		switch (userTypeUpper) {
		case '1':
			ManagerLogin(); // call manager login
			break;
		case '2':
			OperatorLogin(); // call operator login
			break;
		case '3':
			ProviderLogin(); // call provider login
			break;
		case '4': System.out.println("Generating All Reports \n"); 
			clock.AllReports();
			break;
		case '5':
			return;
		default:
			System.out.println("Invalid key entered: " + userType);
			System.out.println(
					"Press [1] for Manager Login \n"
					+ "Press [2] for Operator Login \n"
					+ "Press [3] for Provider Login \n"
					+ "Press [4] to Generate All Reports \n"
					+ "Press [5] to Close the system");	
			char userInput = scan.next().charAt(0);
			SelectUserType(userInput);
			break;
		}
	}
	
	public void loadData() throws ClassNotFoundException, IOException {
	  LoadData d = new LoadData();
	  d.CreateStoredDataFolder();
	  d.loadServiceList();
	  d.loadServiceNameDB();
	  d.loadManagerList();
	  d.loadOperatorList();
	  d.loadProviderList();
	  d.loadMemberList();
	}
	
	public static void main(String[] args) throws Throwable {  //testermain
		MainMenu myMenu = new MainMenu();
		myMenu.loadData();
		boolean keepGoing = true;
		while (keepGoing) {
			keepGoing = myMenu.Display();
		}
		
	}
}