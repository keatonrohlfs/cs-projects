import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;


public class MainMenuTest { //all tests work successfully when entering any input in console, which is correct
	
	MainMenu currMain = new MainMenu();
	Manager currMan = new Manager("setemail", "setpassword");
	
	@Test
	public void TestDisplay() throws ClassNotFoundException, IOException {
		boolean success = currMain.DisplayBool();
		assertTrue(success == true);
	}
	
	
	@Test
	public void TestSelectUserType () throws ClassNotFoundException, IOException {
	char Input = '5';
	boolean workedSuccessfully = currMain.SelectUserTypeBool(Input);;
	assertFalse(workedSuccessfully == true);
	currMain.SelectUserType(Input);
	}
	
	@Test
	public void testGetemail() {
		assertEquals("setemail", currMan.getEmail());
	}
	
}

