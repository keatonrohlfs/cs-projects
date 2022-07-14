import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.lang.String;
public class Manager implements Serializable{
	/**
	 * 
	 */
	//
	private static final long serialVersionUID = 1L;
	private String email; // Manager's email
	private String password; // Manager's password
	// Default constructor
	// Does not add the Manager to a list immediately
	public Manager() {
		this.email = "no-email-set"; // sets email to a non-set value 
		this.password = "no-password-set"; // sets password to a non-set password
	}
	// Constructor that creates a Manager with a set email and password
	public Manager(String em, String pass) {
		this.email = em; // sets email to the value of me
		this.password = pass; // sets email to the value of pass
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	// Compares the password with the passwords in the ArrayList as well as the emails in the list of managers
	public boolean CompPass(String em, String pass) throws ClassNotFoundException, IOException { 
		Manager compManager = new Manager(em, pass); // creates a Manager with the passed in email and password
		return this.CompPass(compManager); // calls the Manager version of CompPass
	}

	public boolean CompPass(Manager manager) throws ClassNotFoundException, IOException {
		ManagerList list = ManagerList.instance(); // uses an instance of the ManagerList class
		return list.isManagerEmail(manager.getEmail()) && list.isManagerPassword(manager.getPassword()); // calls the isManagerEmail and isManagerPass methods in the ManagerList class
	}
	
	// Creates a Provider Report
	public boolean ProviderReport() throws ClassNotFoundException, IOException {
		ServiceList s = new ServiceList();
		ProviderList p = ProviderList.instance();
		List<String> providers = p.getProviderIDs();
		//System.out.println(providers);
		boolean worked = s.createProviderReports(providers);
		if (!worked) {
			System.out.println("Could not create provider reports.");
		}
		return worked;
	}
	// Creates a Summary Report
	public boolean SummaryReport() throws ClassNotFoundException, IOException {
	    ServiceList s = new ServiceList();
	    ProviderList p = ProviderList.instance();
	    List<String> providers = p.getProviderIDs();
	    boolean worked = s.createSummaryReport(providers);
	    if (!worked) {
		    System.out.println("Could not create summary reports.");
	    }
	    return worked;
	}
	// Creates a EFT Report
	public boolean EFTReport() throws ClassNotFoundException, IOException {
		ServiceList s = new ServiceList();
		ProviderList p = ProviderList.instance();
		List<String> providers = p.getProviderIDs();
		boolean worked = s.createEftReport(providers);
		if (!worked) {
			System.out.println("Could not create EFT reports.");
		}
		return worked;
	}
	// Creates a Member Report
	public boolean MemberReport() throws ClassNotFoundException, IOException {
	    ServiceList s = new ServiceList();
	    MemberList m = MemberList.instance();
	    List<String> members = m.getMemberIDList();
	    boolean worked = s.createMemberReports(members);
	    if (!worked) {
		    System.out.println("Could not create member reports.");
	    }
	    return worked;
	}
	
	// Generates all the report types
	public boolean AllReports() throws ClassNotFoundException, IOException {
		boolean provRep = ProviderReport();
		boolean sumRep = SummaryReport();
		boolean eftRep =  EFTReport();
		boolean memRep = MemberReport();
		return provRep && sumRep && eftRep && memRep;
	}
	@Override
	// the toString method for Serializable save file and the formatting
	public String toString() { 
		return "Manager [email=" + email + ", password=" + password + "]";
	}
	public static void main(String[] args) throws Throwable { // small testing main
//		Manager kim = new Manager("kadventures@gmail.com", "SOLVAY");
	    Manager brandon = new Manager("brandon.afterdark@comcast.net", "CAPS");
//		Manager jordan = new Manager("leboski102@gmail.com", "KROGER");
//		Manager jordan1 = new Manager("leboski@gmail.com", "123");
		ManagerList mL = ManagerList.instance();
//		mL.addManager(kim);
//		mL.addManager(jordan);
		mL.addManager(brandon);
//		jordan1.CompPass(jordan1.getEmail(), jordan1.getPassword());
		brandon.ProviderReport();
		brandon.MemberReport();
	}
}