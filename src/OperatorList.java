import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class OperatorList implements Serializable{
	/**
	 * 
	 */
	//
	private static final long serialVersionUID = 1L;
	private static OperatorList instance = null;
	// Dictionary of all operator e-mails and passwords
	public List<Operator> operatorLogins;
	@Override
	public String toString() {
		return "OperatorList [operatorLogins=" + operatorLogins + "]";
	}
	// Default Constructor which creates a new ArrayList of Operators
	private OperatorList() throws ClassNotFoundException, IOException {
		operatorLogins = new ArrayList<Operator>();
	}
	// Constructor that adds a new operator to a list after the creation of an ArrayList
	private OperatorList(Operator myOp) throws ClassNotFoundException, IOException {
		OperatorList m = this.load();
		operatorLogins = new ArrayList<Operator>();
		m.addOperator(myOp);
		operatorLogins = m.operatorLogins;
	}
	// creates a new OperatorList instance and if there is no save file it creates one
	public static OperatorList instance() throws ClassNotFoundException, IOException {
		
		if (instance == null) {
			try {
				OperatorList list = new OperatorList();
				OperatorList temp = list.load();
				if (temp != null) {
					instance = temp;
					return instance;
				}
				
			}
			catch(Throwable t) {
				instance = new OperatorList();
			}
		}
		return instance;
	}
	// creates a new OperatorList instance and if there is no save file it creates one and loads it in
	public static OperatorList instance(Operator myOp) throws ClassNotFoundException, IOException {
		boolean isFound = false;
		instance = OperatorList.instance();
		for (int i = 0; i < instance.operatorLogins.size(); i++) {
			Operator operator = instance.operatorLogins.get(i);
			if (operator.getEmail().equals(myOp.getEmail())) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			instance.addOperator(myOp);
		}
		return instance;
	}

	public List<Operator> getOperatorList() {
		return this.operatorLogins;
	}
	//Adds a Operator to the operatorList
	public void addOperator(Operator newOp) throws IOException {
		long count = getOperatorList().stream().filter(l-> l.getEmail().equals(newOp.getEmail())).count();
		if (count == 0) {
			operatorLogins.add(newOp);
			this.save();
		}
		else {
			System.out.println(String.format("Operator with the email: \"%s\" already exist", newOp.getEmail()));
		}
		
	}
	// Deletes a Operator with the email and password passed in and calls the deleteOperator function with the Operator parameter
	public void deleteOperator(String em, String pass) throws IOException {
		Operator rmOp = new Operator(em, pass); // creates a new Operator object with the values
		this.deleteOperator(rmOp);
	}
	// deletes a operator with the values in Operator object passed in
	public void deleteOperator(Operator removedOperator) throws IOException {
		int isFound = -1;
		for (int i = 0; i < operatorLogins.size(); i++) {
			Operator operator = operatorLogins.get(i);
			if (operator.getEmail().equals(removedOperator.getEmail())) {
				isFound = i;
				break;
			}
		}
		if (isFound != -1) {
			operatorLogins.remove(isFound);
			this.save();
		}
		else {
			System.out.println(String.format("No operator was found with the email \"%s\"", removedOperator.getEmail()));
		}
	}
	// Checks to see if the Operator exist in the list by seeing if the email passed in is present
	public boolean isOperatorEmail(String em) {
		boolean isFound = false;
		for (int i = 0; i < operatorLogins.size(); i++) {
			Operator operator = operatorLogins.get(i);
			if (operator.getEmail().equals(em)) {
				isFound = true;
				break;
			}
			if(i == (operatorLogins.size() - 1)) {
				System.out.println(String.format("No Operator with the email address \"%s\" exist", em));
			}
		}
		return isFound;
	}
	// Checks to see if the Operator password passed in is correct
	public boolean isOperatorPassword(String pass) {
		boolean isFound = false;
		for (int i = 0; i < operatorLogins.size(); i++) {
			Operator operator = operatorLogins.get(i);
			if (operator.getPassword().equals(pass)) {
				isFound = true;
				break;
			}
			if(i == (operatorLogins.size() - 1)) {
				System.out.println("Password was incorrect");
			}
		}
		return isFound;
	}
	// prints the list of Operators and their logins
	public void print() {
		System.out.println(operatorLogins);
	}
	// saves the information in the operatorLogins ArrayList to the save file 
	public void save() throws IOException {
		FileOutputStream fileOutputStream
	    = new FileOutputStream("OperatorList.bin");
		ObjectOutputStream objectOutputStream 
	    = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.flush();
		objectOutputStream.close();
	}
	// Loads the information in the save file for the Operator list into a OperatorList object
	public OperatorList load() throws IOException, ClassNotFoundException{
		FileInputStream fileInputStream
	    = new FileInputStream("OperatorList.bin");
		ObjectInputStream objectInputStream
		= new ObjectInputStream(fileInputStream);
		OperatorList m2 = (OperatorList) objectInputStream.readObject();
		objectInputStream.close(); 
		return m2;
	}
	public void clear() {
		this.operatorLogins.clear();
	}
	
//	public static void main(String[] args) throws Throwable { // small testing main
//		//  Operator kim = new Operator("kadventures@gmail.com", "SOLVAY");
//		Operator brandon = new Operator("brandon.afterdark@comcast.net", "CAPS");
//        //  Operator jordan = new Operator("leboski102@gmail.com", "KROGER");
//		OperatorList m = OperatorList.instance(brandon);
//		m.addOperator(brandon);
//		m.deleteOperator("brandon.afterdark@comcast.net", "CAPS");
//		m.print();
//		m.addOperator(brandon);
//		//m.save();
//		OperatorList n = m.load();
//		//System.out.println("\nAfter the load\n");
//		n.print();
//		//System.out.println(n.operatorLogins.get(0).getEmail());
//		//n.addOperator(new Operator());
//	}
	public static void main(String[] args) throws Throwable {
		Operator newOp_1 = new Operator("1@crimson.ua.edu", "Ion");
		Operator newOp_2 = new Operator("2@crimson.ua.edu", "Reaver");
		OperatorMenu myOpMenu = new OperatorMenu(newOp_1);
		OperatorList myOpList = OperatorList.instance();
		myOpList.addOperator(newOp_2);
		MemberList membList = MemberList.instance();
		ProviderList provList = ProviderList.instance();
	    Member memb0 = new Member("Liam Tucker",               "letucker2003@gmail.com",     "000000001", "14355 Spring Meadow Court", "Vil Green Oaks", "IL", "60048", true);
	    Member memb1 = new Member("Desigamoorthy Shan Nainar", "d.n@lhswildcats.org",        "111111111", "123 Main St.",              "Libertyville",   "IL", "60045", true);
	    Member memb2 = new Member("Katherine Cannon",          "katherine.cannon@gmail.com", "222222221", "535 Second Ave.",           "Lincoln",        "CA", "15000", true);
	    Member memb3 = new Member("Ed Bread",                  "ed.bread@yahoo.com",         "333333331", "1000 Grand Ave.",           "Everytown",      "AK", "12345", false);
	    Member memb4 = new Member("Marie Curie",               "mcurie@hotmail.com",         "444444441", "15 S Main Blvd.",           "Tuscaloosa",     "SC", "34543", true);
	    Member memb5 = new Member("Jen Bacon",                 "jb@gmail.com",               "555555551", "3600 Stonebrook Road",      "Chicago",        "OK", "82524", true);
	    Member memb6 = new Member("Nick Saban",                "sabanemail@ua.edu",          "666666661", "1 UA Way",                  "Northport",      "AL", "34567", true);
	    Member memb7 = new Member("Mrs. Claus",                "claus@google.org",           "777777771", "1500 Lincoln Av",           "Tuscaloosa",     "NV", "86468", true);
	    Member memb8 = new Member("Jodie Zhang",               "jodie.zhang@bcu.org",        "888888881", "42100 Alphabet Drive",      "Chicago",        "OH", "74001", false);
	    Member memb9 = new Member("Haig Ingino",               "hingino@stanford.edu",       "999999991", "8 Cali Road",               "Northport",      "GA", "19001", true);    
	    
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

	    membList.addMember(memb0);
	    membList.addMember(memb1);
	    membList.addMember(memb2);
	    membList.addMember(memb3);
	    membList.addMember(memb4);
	    membList.addMember(memb5);
	    membList.addMember(memb6);
	    membList.addMember(memb7);
	    membList.addMember(memb8);
	    membList.addMember(memb9);
	    
	    provList.addProvider(prov0);
	    provList.addProvider(prov1);
	    provList.addProvider(prov2);
	    provList.addProvider(prov3);
	    provList.addProvider(prov4);
	    provList.addProvider(prov5);
	    provList.addProvider(prov6);
	    provList.addProvider(prov7);
	    provList.addProvider(prov8);
	    provList.addProvider(prov9);
		myOpMenu.OpMenu();
	    //membList.print();
		provList.PrintProviderList();
	}
}