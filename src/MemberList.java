/**
 * Stores Members.
 * Internally, the member data is stored in a text file
 *
 * @author Keaton Rohlfs
 * @version 1.0
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.NullPointerException;

/**
 * Stores Members in a .txt file

 * @author Keaton Rohlfs
 * @version = 1.2
 *
 */

public class MemberList implements Serializable{
	//
	private static final long serialVersionUID = 1L;
	private static MemberList instance = null;
	 // Dictionary of all member e-mails and passwords
	public List<Member> memberList;
	private List<String> memberIDList;
 // Default Constructor which creates a new ArrayList of Members
 	private MemberList() throws ClassNotFoundException, IOException {
 		memberList = new ArrayList<Member>();
 		memberIDList = new ArrayList<String>();
 	}
 // Constructor that adds a new member to a list after the creation of an ArrayList
 	private MemberList(Member myMem) throws ClassNotFoundException, IOException {
 		MemberList m = this.load();
 		memberList = new ArrayList<Member>();
 		m.addMember(myMem);
 		memberList = m.memberList;
 	}
 // saves the information in the memberName ArrayList to the save file 
 // Not used outside of the class
 	public void save() throws IOException {
 		FileOutputStream fileOutputStream = new FileOutputStream("MemberList.bin");
 		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
 		objectOutputStream.writeObject(this);
 		objectOutputStream.flush();
 		objectOutputStream.close();
 	}
 // Loads the information in the save file for the Member list into a MemberList object
 // Not to be used outside of the class
 	private MemberList load() throws IOException, ClassNotFoundException, NullPointerException{
 		FileInputStream fileInputStream = new FileInputStream("MemberList.bin");
 		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
 		MemberList m2 = (MemberList) objectInputStream.readObject();
 		objectInputStream.close(); 
 		return m2;
 	}
 // creates a new MemberList instance and if there is no save file it creates one
 	public static MemberList instance() throws ClassNotFoundException, IOException, NullPointerException { 
 		if (instance == null) {
 			try {
 				MemberList list = new MemberList();
 				MemberList temp = list.load();
 				if (temp != null) {
 					instance = temp;
 					return instance;
 				}
 			}
 			catch(Throwable t) {
 				instance = new MemberList();
 			}
 		}
 		return instance;
 }
 // creates a new MemberList instance and if there is no save file it creates one and loads it in
 	public static MemberList instance(Member myMem) throws ClassNotFoundException, IOException {
 		boolean isFound = false;
 		instance = MemberList.instance();
 		for (int i = 0; i < instance.memberList.size(); i++) {
 			Member member = instance.memberList.get(i);
 			if (member.getEmail().equals(myMem.getEmail())) {
 				isFound = true;
 				break;
 			}
 		}
 		if (!isFound) {
 			instance.addMember(myMem);
 		}
 		return instance;
	}

 	public List<Member> getMemberList() {
 		return this.memberList;
	}
 //Adds a Member to the memberList
 	public void addMember(Member newMem) throws IOException {
 		long count = getMemberList().stream().filter(l-> l.getNum().equals(newMem.getNum())).count();
		if (count == 0) {
			memberList.add(newMem);
			memberIDList.add(newMem.getNum());
			this.save();
		}
		else {
			System.out.println(String.format("Member with the account number: \"%s\" already exist", newMem.getNum()));
		}  
 	}
 // Deletes a Member with the email and password passed in and calls the deleteMember function with the Member parameter
 	public void deleteMember(String name, String email, String num, String address, String city, String state, String zip, boolean status) throws IOException {
 		Member rmMem = new Member(name, email, num, address, city, state, zip, status); //Creates a new Member object with the values
 		this.deleteMember(rmMem);
 	}
 // deletes a member with the values in Member object passed in
 	public void deleteMember(Member removedMember) throws IOException {
 		int isFound = -1;
 		for (int i = 0; i < memberList.size(); i++) {
 			Member member = memberList.get(i);
 			if (member.getEmail().equals(removedMember.getEmail())) {
 				isFound = i;
 				break;
 			}
 		}
 		if (isFound != -1) {
 			memberList.remove(isFound);
 			this.save();
 		}
 		else {
 			System.out.println(String.format("No member was found with the email \"%s\"", removedMember.getEmail()));
 		}
	}
 // Checks to see if the Member exist in the list by seeing if the email passed in is present
 	public boolean isMemberEmail(String em) {
 		boolean isFound = false;
 		for (int i = 0; i < memberList.size(); i++) {
 			Member member = memberList.get(i);
 			if (member.getEmail().equals(em)) {
 				isFound = true;
 				break;
 			}
 			if(i == (memberList.size() - 1)) {
 				System.out.println(String.format("No Member with the email address \"%s\" exist", em));
 			}
 		}
 		return isFound;
 	}
 // Checks to see if the Member password passed in is correct
 	public boolean isMemberNumber(String num) {
 		boolean isFound = false;
 		for (int i = 0; i < memberList.size(); i++) {
 			Member member = memberList.get(i);
 			if (member.getNum().equals(num)) {
 				isFound = true;
 				break;
 			}
 			if(i == (memberList.size() - 1)) {
 				System.out.println("Password was incorrect");
 			}
 		}
 		return isFound;
 	}
 	
 // prints the list of Members and their logins
 	public void print() {
 		System.out.println(memberList);
 	}
 	
 	public List<String> getMemberIDList() {
 		return memberIDList;
 	}
 	
 	public void setMemberIDList(List<String> memberIDList) {
 		this.memberIDList = memberIDList;
 	}
 	
 	public Member getMember(String ID) {
   
 		for (int i=0; i<memberList.size(); i++) {
 			Member m = memberList.get(i);
 			if (m.getNum().compareTo(ID)==0) {
 				return m;
 			}
 		}
 		System.out.println("There was no Member found with that ID");
 		Member m = new Member();
 		return m;
 	}
 
 public static void main(String[] args) throws Throwable { // small testing main
  Member brandon = new Member("name", "email", "num", "address", "city", "state", "zip", true);
  //Member jordan = new Member("name", "email", "num", "address", "city", "state", "zip", true);
  MemberList m = MemberList.instance();
  m.addMember(brandon);
  m.print();
 }
}