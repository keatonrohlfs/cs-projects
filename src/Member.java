import java.io.Serializable;

/**
 * Contains the data for any given member. Not too involved, because members aren't an actor

 * @author Liam Tucker
 * @version 1.0
 *
 */
public class Member implements Serializable{
  private static final long serialVersionUID = 1L;
  //
  private String name;
  private String email;
  private String num;
  private String address;
  private String city;
  private String state;
  private String zip;
  private boolean status; // True = active, false = suspended
  
  public Member() {
    this.name = "";
    this.email = "";
    this.num = "";
    this.address = "";
    this.city = "";
    this.state = "";
    this.zip = "";
    this.status = false;
  }
  
  public Member(String name, String email, String num, String address, String city, String state, String zip, boolean status) {
    this.name = name;
    this.email = email;
    this.num = num;
    this.address = address;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.status = status;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getNum() {
    return this.num;
  }
  
  public void setNum(String num) {
    this.num = num;
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getCity() {
    return this.city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  public String getZip() {
    return this.zip;
  }
  
  public void setZip(String zip) {
    this.zip = zip;
  }
  
  public boolean getStatus() {
    return this.status;
  }
  
  public void setStatus(boolean status) {
    this.status = status;
  }
  
  public String statusToString(boolean status) {
    if (status) return "Active";
    return "Suspended";
  }
  
  public String statusToString() {
    if (this.status) {
    	return "Active";
    }
    return "Suspended";
  }
  @Override
  // the toString method for Serializable save file and the formatting
  public String toString() { 
	return "Member [name=" + name + ", email=" + email + ", num=" + num + ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + ", status=" + status + "]";
  }
//  private String name;
//  private String email;
//  private String num;
//  private String address;
//  private String city;
//  private String state;
//  private String zip;
//  private boolean status; // True = active, false = suspended
  public static void main() {
	  
  }
}
