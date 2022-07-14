import java.io.IOException;

public class OperatorMenu {
	//
	SingletonScanner scan = SingletonScanner.getInstance();
	private Operator my_op;
	public OperatorMenu(Operator input_operator) {
		this.my_op = new Operator(input_operator.getEmail(), input_operator.getPassword());
	}
	public void  OpMenu() throws IOException, ClassNotFoundException {
		System.out.println(my_op.getEmail());
		String input;
			MemberList memList = MemberList.instance();
			ProviderList provList = ProviderList.instance();
			while(true) {
				System.out.println("Would you like to manage a 'Provider' or a 'Member'? Or Enter 'Exit' to close the terminal.");
				input = scan.next().toUpperCase().trim(); // Sanitizing input
				if(input.equals("EXIT")) {
							break;
				}
			//Do this stuff if the operator wants to manage a member
			//TODO: Create helper methods like OpMenuAddMember(), and move the logic of the prompts there, to increase readability
				else if(input.equals("MEMBER")) {
					String memEmail, memName, memID, memAddress, memCity, memState,  memZC, memStatus;
					System.out.println("Do you want to 'Add', 'Delete', or 'Update' a Member? Or Enter 'Exit' to close the terminal.");
					String memOption;
					memOption = scan.next().toUpperCase().trim();
					if(memOption.equals("EXIT")) {
						break;
					}
					//adding a member to MemberList
					else if(memOption.equals("ADD")) {
					    System.out.println("Please enter the Member Name:");
					    memName = scan.next().trim();
					   
					    System.out.println("Please enter the Member Email:");
					    memEmail = scan.next().trim();
					    
					    System.out.println("Please enter the Member ID:");
					    memID = scan.next().trim();
		            	while (memID.length() != 9) {
		            		System.out.println("Make sure the length of the ID is 9");
		            		memID = scan.next().trim();
		            	}
					    
					    System.out.println("Please enter the Member's Street Address:");
					    memAddress = scan.next().trim();
					    
					    System.out.println("Please enter the Member's City:");
					    memCity = scan.next().trim();
					    
					    System.out.println("Please enter the Member's State:");
					    memState = scan.next().trim();
			            while (memState.length() != 2) {
		            		System.out.println("Make sure the length of the State is 2. The State should be input like this.\nEx. 'GA' for Georgia");
		            		memState = scan.next().toUpperCase().trim();
		            	}
					    
					    System.out.println("Please enter the Member's Zip Code:");
					    memZC = scan.next().trim();
					    
					    // call MemberList.addMember function of however keaton implements it
					    
					  memList.addMember(my_op.addMember(memName, memEmail, memID, memAddress, memCity, memState, memZC, true));
					  System.out.println("New Member has been added to the Directory.");
					}
					// Removing member from MemberList
					else if(memOption.equals("DELETE")) {
						System.out.println("Enter the ID of the Member you would like to Delete");
						String memRemoval;
						memRemoval = scan.next();
						memList.deleteMember(memList.getMember(memRemoval));
						System.out.println("Member Removed.");
						
					}
					//Editing member in MemberList
					else if(memOption.equals("UPDATE")) {
				    	System.out.println("Enter the name of the ID you would like to Update");
				    	String updateMem;
				    	
				    	updateMem = scan.next();
				    	Member selectedMember = memList.getMember(updateMem);
				    	while (selectedMember.getNum() == "") {
				    		System.out.println("Input a valid ID");
				    		updateMem = scan.next();
				    		selectedMember = memList.getMember(updateMem);
				    	}
				    	System.out.println("Please update the desired field. Any field desired to be unchanged should be left blank.");
					    System.out.println("Please enter the Member Name:");
					    memName = scan.next().trim();
					    
					    System.out.println("Please enter the Member Email:");
					    memEmail = scan.next().trim();
					    
					    System.out.println("Please enter the Member ID:");
					    memID = scan.next().trim();
		            	while (memID.length() != 9) {
		            		System.out.println("Make sure the length of the ID is 9\n");
		            		memID = scan.next().trim();
		            	}
					    System.out.println("Please enter the Member's Street Address:");
					    memAddress = scan.next().trim();
					    
					    System.out.println("Please enter the Member's City:");
					    memCity = scan.next().trim();
					    
					    System.out.println("Please enter the Member's State:");
					    memState = scan.next().trim();
			            while (memState.length() != 2) {
		            		System.out.println("Make sure the length of the State is 2. The State should be input like this.\nEx. 'GA' for Georgia\n");
		            		memState = scan.next().toUpperCase().trim();
		            	}
					    
					    System.out.println("Please enter the Member's Zip Code:");
					    memZC = scan.next().trim();
					    
					    System.out.println("Did the Member's Status change?\n Answer with a 'y' or 'n':");
					    memStatus = scan.next().trim();
					    // String email, password, memName, memID, memAddress, memCity, memState,  memZC;
					    if (memStatus == "n") {
					    	my_op.UpdateMember(selectedMember, memName, memEmail, memID, memAddress, memCity, memState, memZC, memList.getMember(memID).getStatus());
					    }
					    else {
					    	my_op.UpdateMember(selectedMember, memName, memEmail, memID, memAddress, memCity, memState, memZC, !memList.getMember(memID).getStatus());
					    }
					}  
				}
				else if(input.equals("PROVIDER")) {
					String P_Name, P_ID, P_Address, P_City, P_State, P_ZC, P_Email, P_Password;
		        	System.out.println("Do you want to 'Add', 'Delete', or 'Update' a Provider? Or Enter 'Exit' to close the terminal.");
		        	String selectionP;
		        	selectionP = scan.next().toUpperCase().trim();
		        	if(selectionP.equals("EXIT")) {
		        		break;
		        	}
		        	// adding a provider to providerList
		        	else if(selectionP.equals("ADD")) {
			            System.out.println("Please enter the Provider Name:");
			            P_Name = scan.next().trim();
			            
			            System.out.println("Please enter the Provider Email:");
			            P_Email = scan.next().trim();
			            
			            System.out.println("Please enter the Provider Password:");
			            P_Password = scan.next().trim();
			            
			            System.out.println("Please enter the Provider ID:");
			            P_ID = scan.next().trim();
		            	while (P_ID.length() != 9) {
		            		System.out.println("Make sure the length of the ID is 9\n");
		            		P_ID = scan.next().trim();
		            	}
			         
			            System.out.println("Please enter the Provider's Street Address:");
			            P_Address = scan.next().trim();
			            
			            System.out.println("Please enter the Provider's City:");
			            P_City = scan.next().trim();
			            
			            System.out.println("Please enter the Provider's State:");
			            P_State = scan.next().trim();
			            while (P_State.length() != 2) {
		            		System.out.println("Make sure the length of the State is 2. The State should be input like this.\nEx. 'GA' for Georgia\n");
		            		P_State = scan.next().toUpperCase().trim();
		            	}
			            
			            System.out.println("Please enter the Provider's Zip Code:");
			            P_ZC = scan.next().trim();
			            provList.addProvider(my_op.addProvider(P_Email, P_Password, P_Name, P_ID, P_Address, P_City, P_State, P_ZC));
			            System.out.println("New Provider Added to Directory.");
		    		}
		        	//Deletes Provider
		    		else if(selectionP.equals("DELETE")) {
			    		System.out.println("Enter the name of the Provider you would like to Delete");
			    		String nameRemove;
			    		nameRemove = scan.next();
			    		provList.deleteProvider((provList.getProvider(nameRemove)));
			    		System.out.println("Provider Removed.");
		    		}
		        	//Edits Provider
		    		else if(selectionP.equals("UPDATE")) {
		    			System.out.println("Enter the ID of the Provider you would like to Update");
			        	String editProv;
			        	editProv = scan.next().toUpperCase().trim();
			        	Provider selectedProvider = provList.getProvider(editProv);
				    	while (selectedProvider.GetProviderID() == " ") {
				    		System.out.println("Input a valid ID");
				    		editProv = scan.next();
				    		selectedProvider = provList.getProvider(editProv);
				    	}
			        	System.out.println("Please edit the desired field when prompted, leave sections blank that should be unchanged and hit enter");
			        	
				        System.out.println("Please enter the Provider Name:");
				        P_Name = scan.next().trim();
				        
				        System.out.println("Please enter the Provider Email:");
				        P_Email = scan.next().trim();
				        
				        System.out.println("Please enter the Provider Password:");
				        P_Password = scan.next().trim();
				        
				        System.out.println("Please enter the Provider ID:");
				        P_ID = scan.next().trim();
		            	while (P_ID.length() != 9) {
		            		System.out.println("Make sure the length of the ID is 9\n");
		            		P_ID = scan.next().trim();
		            	}
				        
				        System.out.println("Please enter the Provider's Street Address:");
				        P_Address = scan.next().trim();
				        
				        System.out.println("Please enter the Provider's City:");
				        P_City = scan.next().trim();
				        
				        System.out.println("Please enter the Provider's State:");
				        P_State = scan.next().trim();
			            while (P_State.length() != 2) {
		            		System.out.println("Make sure the length of the State is 2. The State should be input like this.\nEx. 'GA' for Georgia\n");
		            		P_State = scan.next().toUpperCase().trim();
		            	}
				        
				        System.out.println("Please enter the Provider's Zip Code:");
				        P_ZC = scan.next().trim();
				        my_op.UpdateProvider(selectedProvider, P_Email, P_Password, P_Name, P_ID, P_Address, P_City, P_State, P_ZC);
				    }  
				}
				else {
					System.out.println("Type a valid input\n'Provider', 'Member', or 'Exit'");	
				}
			}
		}
    }
//	public static void main() throws ClassNotFoundException, IOException {
//		Operator newOp_1 = new Operator("1@crimson.ua.edu", "Ion");
//		//Operator newOp_2 = new Operator("2@crimson.ua.edu", "Reaver");
//		OperatorMenu myOpMenu = new OperatorMenu(newOp_1);
//		//OperatorList myOpList = OperatorList.instance();
//		//myOpList.addOperator(newOp_2);
//		MemberList membList = MemberList.instance();
//	    Member memb0 = new Member("Liam Tucker              ", "letucker2003@gmail.com    ", "000000001", "14355 Spring Meadow Court", "Vil Green Oaks", "IL", "60048", true);
//	    Member memb1 = new Member("Desigamoorthy Shan Nainar", "d.n@lhswildcats.org",        "111111111", "123 Main St.",              "Libertyville",   "IL", "60045", true);
//	    Member memb2 = new Member("Katherine Cannon",          "katherine.cannon@gmail.com", "222222221", "535 Second Ave.",           "Lincoln",        "CA", "15000", true);
//	    Member memb3 = new Member("Ed Bread",                  "ed.bread@yahoo.com",         "333333331", "1000 Grand Ave.",           "Everytown",      "AK", "12345", false);
//	    Member memb4 = new Member("Marie Curie",               "mcurie@hotmail.com",         "444444441", "15 S Main Blvd.",           "Tuscaloosa",     "SC", "34543", true);
//	    Member memb5 = new Member("Jen Bacon",                 "jb@gmail.com",               "555555551", "3600 Stonebrook Road",      "Chicago",        "OK", "82524", true);
//	    Member memb6 = new Member("Nick Saban",                "sabanemail@ua.edu",          "666666661", "1 UA Way",                  "Northport",      "AL", "34567", true);
//	    Member memb7 = new Member("Mrs. Claus",                "claus@google.org",           "777777771", "1500 Lincoln Av",           "Tuscaloosa",     "NV", "86468", true);
//	    Member memb8 = new Member("Jodie Zhang",               "jodie.zhang@bcu.org",        "888888881", "42100 Alphabet Drive",      "Chicago",        "OH", "74001", false);
//	    Member memb9 = new Member("Haig Ingino",               "hingino@stanford.edu",       "999999991", "8 Cali Road",               "Northport",      "GA", "19001", true);    
//	    membList.addMember(memb0);
//	    membList.addMember(memb1);
//	    membList.addMember(memb2);
//	    membList.addMember(memb3);
//	    membList.addMember(memb4);
//	    membList.addMember(memb5);
//	    membList.addMember(memb6);
//	    membList.addMember(memb7);
//	    membList.addMember(memb8);
//	    membList.addMember(memb9);
//		myOpMenu.OpMenu();
//	}