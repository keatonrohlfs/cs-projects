import java.io.Serializable;

/** Author: Ramsey Morrow*/

public class Operator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;

	public Operator() {
		this.setEmail("");
		this.setPassword("");
	}
	public Operator(String em, String pass) {
		this.setEmail(em);
		this.setPassword(pass);
	}
	
	public Member addMember(String M_name, String M_email, String M_ID, String M_streetAddress, String M_city, String M_state, String M_zipcode, boolean M_status) {
		Member member = new Member(M_name, M_email, M_ID, M_streetAddress, M_city, M_state, M_zipcode, M_status);
		return member;
	}
	
	public void deleteMember(Member member) {
		member = null;
	}
	
	public void UpdateMember(Member member, String M_name, String M_email, String M_ID, String M_streetAddress, String M_city, String M_state, String M_zipcode, boolean M_status) {
		if (M_name != "") member.setName(M_name);
		if (M_email != "") member.setEmail(M_email);
		if (M_ID != "") member.setNum(M_ID);
		if (M_streetAddress != "") member.setAddress(M_streetAddress);
		if (M_city != "") member.setCity(M_city);
		if (M_state != "") member.setState(M_state);
		if (M_zipcode != "") member.setZip(M_zipcode);
		member.setStatus(M_status);
	}
	
	public Provider addProvider(String P_email, String P_password, String P_name, String P_ID, String P_streetAddress, String P_city, String P_state, String P_zipcode) {
        Provider provider = new Provider(P_email, P_password, P_ID, P_streetAddress, P_city, P_state, P_zipcode, P_name);
        return provider;
    }
    
    public void deleteProvider(Provider provider) {
        provider = null;
    }
    
    public void UpdateProvider(Provider provider, String P_email, String P_password, String P_name, String P_ID, String P_streetAddress, String P_city, String P_state, String P_zipcode) {
    	if (P_email != " ") provider.SetProviderEmail(P_email);
    	if (P_password != " ") provider.SetProviderPassword(P_password);
    	if (P_name != " ") provider.SetProviderName(P_name);
		if (P_ID != " ") provider.SetProviderID(P_ID);
		if (P_streetAddress != " ") provider.SetProviderAddress(P_streetAddress);
		if (P_city != " ") provider.SetProviderCity(P_city);
		if (P_state != " ") provider.SetProviderState(P_state);
		if (P_zipcode != " ") provider.SetProviderZip(P_zipcode);
		
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
	
	
}