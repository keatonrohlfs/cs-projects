import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperatorTest {

	Operator Op;
	Provider pro;
	Provider pro2;
	Provider pro3;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		Op = new Operator();
		pro = new Provider("email", "password", "name", "iD", "streetAddress", "city", "state", "zipcode");
		pro2 = new Provider("emailchange", "password", "nameChange", "iD", "streetAddress", "city", "state", "zipcode");
	}

	@Test
	/** Author: Ramsey Morrow */
	public void testAddProvider() {
		pro3 = Op.addProvider("email", "password", "name", "iD", "streetAddress", "city", "state","zipcode");
		assertEquals(pro.GetProviderEmail(), pro3.GetProviderEmail());
	}
	@Test
	/** Author: Ramsey Morrow */
	public void testUpdateProvider() throws ClassNotFoundException, IOException  {
		Op.UpdateProvider(pro, "emailchange", "", "", "", "", "", "", "");
		assertEquals(pro2.GetProviderEmail(), pro.GetProviderEmail());
	}
}
