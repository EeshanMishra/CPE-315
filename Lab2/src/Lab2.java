import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lab2 {
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println(args[0]);
		File inputFile = new File(args[0]);
		Scanner scanner = new Scanner(inputFile);
		System.out.println(scanner.nextLine());
	}
}
