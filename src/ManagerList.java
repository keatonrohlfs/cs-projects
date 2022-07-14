import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class ManagerList implements Serializable{
	/**
	 * 
	 */
	//
	private static final long serialVersionUID = 1L;
	private static ManagerList instance = null;
	// Dictionary of all manager e-mails and passwords
	public List<Manager> managerLogins;
	// Default Constructor which creates a new ArrayList of Managers
	private ManagerList() throws ClassNotFoundException, IOException {
		managerLogins = new ArrayList<Manager>();
	}
	// Constructor that adds a new manager to a list after the creation of an ArrayList
	private ManagerList(Manager myMan) throws ClassNotFoundException, IOException {
		ManagerList m = this.load();
		managerLogins = new ArrayList<Manager>();
		m.addManager(myMan);
		managerLogins = m.managerLogins;
	}
	// creates a new ManagerList instance and if there is no save file it creates one
	public static ManagerList instance() throws ClassNotFoundException, IOException {
		
		if (instance == null) {
			try {
				ManagerList list = new ManagerList();
				ManagerList temp = list.load();
				if (temp != null) {
					instance = temp;
					return instance;
				}
				
			}
			catch(Throwable t) {
				instance = new ManagerList();
			}
		}
		return instance;
	}
	// creates a new ManagerList instance and if there is no save file it creates one and loads it in
	public static ManagerList instance(Manager myMan) throws ClassNotFoundException, IOException {
		boolean isFound = false;
		instance = ManagerList.instance();
		for (int i = 0; i < instance.managerLogins.size(); i++) {
			Manager manager = instance.managerLogins.get(i);
			if (manager.getEmail().equals(myMan.getEmail())) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			instance.addManager(myMan);
		}
		return instance;
	}

	public List<Manager> getManagerList() {
		return this.managerLogins;
	}
	//Adds a Manager to the managerList
	public void addManager(Manager newMan) throws IOException {
		long count = getManagerList().stream().
				filter(l-> l.getEmail().equals(newMan.getEmail())).count();
		if (count == 0) {
			managerLogins.add(newMan);
			this.save();
		}
		else {
			System.out.println(String.format("Manager with the email: \"%s\" already exist", newMan.getEmail()));
		}
		
	}
	// Deletes a Manager with the email and password passed in and calls the deleteManager function with the Manager parameter
	public void deleteManager(String em, String pass) throws IOException {
		Manager rmMan = new Manager(em, pass); // creates a new Manager object with the values
		this.deleteManager(rmMan);
	}
	// deletes a manager with the values in Manager object passed in
	public void deleteManager(Manager removedManager) throws IOException {
		int isFound = -1;
		for (int i = 0; i < managerLogins.size(); i++) {
			Manager manager = managerLogins.get(i);
			if (manager.getEmail().equals(removedManager.getEmail())) {
				isFound = i;
				break;
			}
		}
		if (isFound != -1) {
			managerLogins.remove(isFound);
			this.save();
		}
		else {
			System.out.println(String.format("No manager was found with the email \"%s\"", removedManager.getEmail()));
		}
	}
	// Checks to see if the Manager exist in the list by seeing if the email passed in is present
	public boolean isManagerEmail(String em) {
		boolean isFound = false;
		for (int i = 0; i < managerLogins.size(); i++) {
			Manager manager = managerLogins.get(i);
			if (manager.getEmail().equals(em)) {
				isFound = true;
				break;
			}
			if(i == (managerLogins.size() - 1)) {
				System.out.println(String.format("No Manager with the email address \"%s\" exist", em));
			}
		}
		return isFound;
	}
	// Checks to see if the Manager password passed in is correct
	public boolean isManagerPassword(String pass) {
		boolean isFound = false;
		for (int i = 0; i < managerLogins.size(); i++) {
			Manager manager = managerLogins.get(i);
			if (manager.getPassword().equals(pass)) {
				isFound = true;
				break;
			}
			if(i == (managerLogins.size() - 1)) {
				System.out.println("Password was incorrect");
			}
		}
		return isFound;
	}
	// prints the list of Managers and their logins
	public void print() {
		System.out.println(managerLogins);
	}
	// saves the information in the managerLogins ArrayList to the save file 
	public void save() throws IOException {
		FileOutputStream fileOutputStream
	    = new FileOutputStream("ManagerList.bin");
		ObjectOutputStream objectOutputStream 
	    = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.flush();
		objectOutputStream.close();
	}
	// Loads the information in the save file for the Manager list into a ManagerList object
	public ManagerList load() throws IOException, ClassNotFoundException{
		FileInputStream fileInputStream
	    = new FileInputStream("ManagerList.bin");
		ObjectInputStream objectInputStream
		= new ObjectInputStream(fileInputStream);
		ManagerList m2 = (ManagerList) objectInputStream.readObject();
		objectInputStream.close(); 
		return m2;
	}
	
	public void clear() throws IOException {
		this.managerLogins.clear();
		this.save();
	}
	
	public static void main(String[] args) throws Throwable { // small testing main
		Manager kim = new Manager("kadventures@gmail.com", "SOLVAY");
		//Manager brandon = new Manager("brandon.afterdark@comcast.net", "CAPS");
        Manager jordan = new Manager("leboski102@gmail.com", "KROGER");
		ManagerList m = ManagerList.instance();
		m.addManager(jordan);
		m.deleteManager("brandon.afterdark@comcast.net", "CAPS");
		m.print();
		m.addManager(kim);
		//m.save();
		ManagerList n = m.load();
		n.print();
		n.clear();
		//System.out.println("\nAfter the load\n");
		n.print();
		//System.out.println(n.managerLogins.get(0).getEmail());
		//n.addManager(new Manager());
	}
}