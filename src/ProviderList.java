
//class that generates list of providers, stores list in a file
/*
 * Stores Providers. Can be accessed by anyone accessing any data about a Provider.
 * Internally, the provider data is stored in a text file
 *
 * @author Quillen Flanigan
 */
 

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Stores Providers in an instance of a list using serializable

 *@author Quillen Flanigan
 *
 */
public class ProviderList implements Serializable {
  private static final long serialVersionUID = 1L;
  private static ProviderList instance = null;
  public int providerCount = 0;
  public List<Provider> providerList;
  public List<String> provIDList;
  
  private ProviderList() throws ClassNotFoundException, IOException {
		  this.providerList = new ArrayList<Provider>();
		  this.provIDList = new ArrayList<String>();
	}
  
  private ProviderList(Provider p) throws ClassNotFoundException, IOException {
		ProviderList provList = this.load();
		providerList = new ArrayList<Provider>();
		provIDList = new ArrayList<String>();
		provList.addProvider(p);
		providerList=provList.providerList;
		
	}
  
  public static ProviderList instance() throws ClassNotFoundException, IOException {
		
		if (instance == null) {
			try {
				ProviderList provList = new ProviderList();
				ProviderList temp = provList.load();
				if (temp != null) {
					instance = temp;
					return instance;
				}
				
			}
			catch(Throwable t) {
				instance = new ProviderList();
			}
		}
		return instance;
	}
  
  
  public static ProviderList instance(Provider p) throws ClassNotFoundException, IOException {
		boolean isFound = false;
		instance = ProviderList.instance();
		for (int i = 0; i < instance.providerList.size(); i++) {
			Provider provider = instance.providerList.get(i);
			if (provider.GetProviderEmail().equals(p.GetProviderEmail())) {
				isFound=true;
				break;
			}
		}
		if (!isFound) {
			instance.addProvider(p);
		}
		return instance;
	}
  
  
  
  
  public List<Provider> getProviderList() {
		return this.providerList;
	}
  
//Adds a Provider to the ProviderList
  
  /**
   * adds Provider to ProviderList instance
   * @param p
   * @throws IOException
   */
  
	public void addProvider(Provider p) throws IOException {
		long count = getProviderList().stream().
				filter(l-> l.GetProviderEmail().equals(p.GetProviderEmail())).count();
		if (count == 0) {
			providerList.add(p);
			providerCount++;
			this.save();
		}
		else {
			System.out.println(String.format("Provider with the ID: \"%s\" already exist", p.GetProviderID()));
		}
		
	}
  
	// deletes a Provider with the values in Provider object passed in
	public void deleteProvider(String string) throws IOException {
		int isFound = -1;
		for (int i = 0; i < providerList.size(); i++) {
			Provider prov = providerList.get(i);
			if (prov.GetProviderID().equals(string)) {
				isFound = i;
				break;
			}
		}
		if (isFound != -1) {
			providerList.remove(isFound);
			providerCount--;
			this.save();
		}
		else {
			System.out.println(String.format("No provider was found with the ID \"%s\"", string));
		}
	}
	public void deleteProvider(Provider provider) throws IOException {
		int isFound = -1;
		for (int i = 0; i < providerList.size(); i++) {
			Provider prov = providerList.get(i);
			if (prov.GetProviderID().equals(provider.GetProviderID())) {
				isFound = i;
				break;
			}
		}
		if (isFound != -1) {
			providerList.remove(isFound);
			providerCount--;
			this.save();
		}
		else {
			System.out.println(String.format("No provider was found with the ID \"%s\"", provider.GetProviderID()));
		}
	}

  public boolean isProvPassword(String pass) {
		boolean isFound = false;
		for (int i = 0; i < providerList.size(); i++) {
			Provider provider = providerList.get(i);
			if (provider.GetProviderPassword().equals(pass)) {
				isFound = true;
				break;
			}
			if(i == (providerList.size() - 1)) {
				System.out.println("Password was incorrect");
			}
		}
		return isFound;
	}
  
  public boolean isProvEmail(String em) {
	  boolean isFound=false;
	  for (int i=0; i<providerList.size(); i++) {
		  Provider provider=providerList.get(i);
		  if(provider.GetProviderEmail().equals(em)) {
			  isFound=true;
			  break;
		  }
		  if (i==(providerList.size()-1)) {
			  System.out.println("Email was incorrect");
		  }
	  }
	  return isFound;
  }
  
  public boolean IsProvider(String id) {
		 for (int i=0; i<providerList.size(); i++) {
		    	if (providerList.get(i).GetProviderID().equals(id)) {
		    		return true;
		    	}
		    }
		 return false;
	}

  
	
  
 public Provider getProvider(String id) {
    for (int i=0; i<providerList.size(); i++) {
    	if (providerList.get(i).GetProviderID().equals(id)) {
    		return providerList.get(i);
    	}
    }
    Provider empty=new Provider();
    return empty;
 }
  
  public List<String> getProviderIDs() {
	this.provIDList.clear();
    
    for (int i=0; i<providerList.size(); i++) {
		  provIDList.add(providerList.get(i).GetProviderID());
	  }
	  
	  return provIDList;
  }

	
private int getProviderCount() {
	return providerCount;
}


public void PrintProviderList() {
	Provider myProv = new Provider();
	for (int i=0; i<providerList.size(); i++) {
		myProv.PrintProviderData(providerList.get(i));
	}
}


//saves the information in the providerList ArrayList to the save file 
	public void save() throws IOException {
		FileOutputStream fileOutputStream
	    = new FileOutputStream("ProviderList.bin");
		ObjectOutputStream objectOutputStream 
	    = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.flush();
		objectOutputStream.close();
	}
	
	// Loads the information in the save file for the Provider list into a ProviderList object
		public ProviderList load() throws IOException, ClassNotFoundException{
			FileInputStream fileInputStream
		    = new FileInputStream("ProviderList.bin");
			ObjectInputStream objectInputStream
			= new ObjectInputStream(fileInputStream);
			ProviderList provList = (ProviderList) objectInputStream.readObject();
			objectInputStream.close(); 
			return provList;
		}
		
		public void clear() throws IOException {
			this.providerList.clear();
			this.save();
		}
		
	/*	
		public static void main(String[] args) throws Throwable { // small testing main
			Provider kim = new Provider("kim@example", "kimPass", "123456789", "1 kim st", "Tuscsa", "AL", "32748", "Kim");
			Provider brandon = new Provider("brandon@example", "bPass", "234156789", "2 b st", "Jeff", "KY", "40207", "Brandon");
	        Provider jordan = new Provider("j@exmaple", "jPass", "987654321", "3 j st", "Oldham", "KY", "43221", "Jordan");
			ProviderList p = ProviderList.instance();
			p.addProvider(jordan);
			p.addProvider(brandon);
			p.addProvider(kim);
			Member m=new Member();
			System.out.println(p.getProviderCount());
			if(p.isProvEmail(kim.GetProviderEmail())==true) {
				System.out.println("email is valid");
			}
			if(p.isProvPassword(brandon.GetProviderPassword())==true) {
				System.out.println("Password is valid");
			}
			Provider p2=p.getProvider(jordan.GetProviderID());
			p2.PrintProviderData(p2);
			if(p.IsProvider("234")==false) {
				System.out.println("provider with this id is not in the list");
			}
			boolean v=kim.ValidateMember(m);
			if (v==false) {
				System.out.println("m is an invalid member");
			}
			Service s=new Service();
			s.setServDate("04-28-2022");
			s.setMembNum("123456789");
			s.setServCode("123456");
			boolean worked=brandon.BillChocAn(s.getServDate(), s.getMembNum(), s.getServCode(), "no comments");
			if (worked==true) {
				System.out.println("Service was correctly billed");
			}
			p.deleteProvider(jordan);
			p.PrintProviderList();
			p.addProvider(kim); 
			p.deleteProvider(kim);
			p.save();
			
			ProviderList prov = p.load();
			prov.PrintProviderList();
			prov.clear();
			//System.out.println("\nAfter the load\n");
			prov.PrintProviderList();
			//System.out.println(n.managerLogins.get(0).getEmail());
			//n.addManager(new Manager());
		}
		
	*/	
}