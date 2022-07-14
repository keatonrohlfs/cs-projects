import java.io.IOException; 
public class ManagerMenu{
	private Manager manager;
	//
	
	public ManagerMenu(Manager inMan) {
		manager = inMan;
	}
	
	public void ProviderReport() throws ClassNotFoundException, IOException {
		manager.ProviderReport();
	}
	
	public void SummaryReport() throws ClassNotFoundException, IOException {
		manager.SummaryReport();
	}
	
	public void EFTReport() throws ClassNotFoundException, IOException {
		manager.EFTReport();
	}
	
	public void MemberReport() throws ClassNotFoundException, IOException {
		manager.MemberReport();
	}
	
	public void AllReports() throws ClassNotFoundException, IOException {
		manager.AllReports();
	}
	
	// This display is for the visual of the Manager Menu
	public void Display() throws ClassNotFoundException, IOException {
		//Scanner myScanner = new Scanner(System.in);
		SingletonScanner scan = SingletonScanner.getInstance();
		System.out.println(manager.getEmail()); // prints out the Manager's ID at the top of the Screen
		System.out.println();
		int option = 0; // takes the user's input for the action they would like
		//TODO: May need to handle error if user types something that isn't an integer
		/* 
		 * Menu loop
		 * 
		 */
		//TODO: move the prompts inside of of the while loop
		while(option != 6) {
			System.out.println("Select what you would like to do:");
			System.out.println("[1] Print Provider Report"); 
			System.out.println("[2] Print Summary Report"); 
			System.out.println("[3] Print EFT Report");
			System.out.println("[4] Print Member Report");
			System.out.println("[5] Print All Reports");
			System.out.println("[6] Logout");
			option = scan.nextInt(); // takes the user's input for the action they would like
			if (option == 1) {
				ProviderReport();
			}
			else if (option == 2) {
				SummaryReport();
			}
			else if (option == 3) {
			  EFTReport();
			}
			else if (option == 4) {
				MemberReport();
			}
			else if (option == 5) {
				AllReports();
			}
			else if (option == 6) {
				break;
			}
			else {
				 System.out.println("Invalid input: " + option);
			  System.out.println("Type a number between 1 and 5");
			}
		}
		scan.close();
	}
//	public static void main(String[] args) throws Throwable {
//		ManagerList m = ManagerList.instance();
//		Manager newMan = new Manager("coalmander.afterdark@gmail.com", "CS200");
//		m.addManager(newMan);
////		ManagerList n = m.load();
////		//m.save();
////		System.out.println("\nAfter the load\n");
////		n.print();
////		n.addManager(new Manager());
//		ManagerMenu myMenu = new ManagerMenu(newMan);
//		myMenu.Display();
//	}
}