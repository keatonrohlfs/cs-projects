import java.util.Scanner;
public class SingletonScanner {
	private Scanner scanner = new Scanner(System.in);
	static SingletonScanner instance = null;
	private SingletonScanner() {

	}
	public static SingletonScanner getInstance() {
		if (instance == null) {
			instance = new SingletonScanner();
		}
		return instance;
	}
	public String next() {
		return scanner.next();
	}
	public void close() {
		//scanner.close();
	}
	public int nextInt() {
		return scanner.nextInt();
	}
}
