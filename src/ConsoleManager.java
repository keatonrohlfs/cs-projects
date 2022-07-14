import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class ConsoleManager {

	public ConsoleManager(){}

	private static int menu(){
	
		int selection = 4;
		Scanner input = new Scanner(System.in);
		/***************************************************/
		System.out.println("Choose from these Choices");
		System.out.println("-------------------------\n");
		System.out.println("1 - Generate Yellow Report");
		System.out.println("2 - Generate Blue Report");
		System.out.println("3 - Generate Red Report");
		System.out.println("4 - Quit");
		while(input.hasNext()){ 
			if( input.hasNextInt() ){
			selection = input.nextInt();
			break;
			}
			else{
			System.err.println(String.format("Invalid Selection: %s", input.next())); 
			}
		}
		return selection;
	} 
	public static void main(String[] args) {

		int choice = 0;
		do {
			choice = ConsoleManager.menu();
			System.err.println(String.format("Choice: %d ", choice));
			switch(choice){
				case 1:
				System.err.println(String.format("Executing: %d ", choice));
				break;
				case 2:
				System.err.println(String.format("Executing: %d ", choice));
				break;
				case 3:
				System.err.println(String.format("Executing: %d ", choice));
				break;
			}
		}
		while (choice != 4);
	}
}