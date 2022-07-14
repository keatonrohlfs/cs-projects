import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LiamTucker {

  @Test
  void testGetFilename() {
    ServiceList temp1 = new ServiceList();
    assert (temp1.getFilename().compareTo("./Stored Data/ServiceList.txt") == 0);
    
    ServiceList temp2 = new ServiceList("ServiceListName.txt");
    assert (temp2.getFilename().compareTo("ServiceListName.txt") == 0);
    
    ServiceList temp3 = new ServiceList("TestName");
    assert (temp3.getFilename().compareTo("TestName.txt") == 0);
    
    //fail("Not yet implemented");
  }

  @Test
  void testFindWeekNum() {
    ServiceList temp = new ServiceList();
    assertEquals(temp.findWeekNum("2022-03-27"), 1);
    assertEquals(temp.findWeekNum("2022-03-13"), -1);
    assertEquals(temp.findWeekNum("2022-04-28"), 5);
    assertEquals(temp.findWeekNum("2023-03-27"), 53);
  }

  @Test
  void testToString() {
    Provider empty = new Provider();
    String emptyPrint = "Provider [Provider Email= , Provider Password= , Provider ID= , Provider Address= , Provider City= , Provider State= , Provider Zip= , Provider Name= ]";
    assertEquals(empty.toString(), emptyPrint);
    Provider prov0 = new Provider("liamt2003@yahoo.com", "password", "123454321", "14355 Main St.", "Vernon Hills", "NY", "83001", "Liam T");
    String provPrint = "Provider [Provider Email=liamt2003@yahoo.com, Provider Password=password, Provider ID=123454321, Provider Address=14355 Main St., Provider City=Vernon Hills, Provider State=NY, Provider Zip=83001, Provider Name=Liam T]";
    assertEquals(provPrint, prov0.toString());
  }

}
