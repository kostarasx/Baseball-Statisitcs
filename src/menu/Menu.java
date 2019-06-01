package menu;

import java.util.Scanner;

public class Menu {

	private static Scanner scanner;
	
	

	public static int checkIfInt() {
		boolean check;
		int choice = -9;
		scanner = new Scanner(System.in);
		do {
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				check = true;
			} else {
				System.out.println("Error wrong input. Please try again and enter a number!");
				check = false;
				scanner.next();
			}
		} while (!(check));
		return choice;
	}

	public static boolean checIfRightValue(int choice, int low, int high) {
		boolean check = false;
		if (choice < low || choice > high) {
			System.out.println("Error wrong choice. Pls try again!");
			check = true;
		}
		return check;
	}

	public static int getUserInput(int minChoice, int maxChoice) {
		int choice;
		boolean check;
		do {
			choice = checkIfInt();// check if innpu has int
			check = checIfRightValue(choice, minChoice, maxChoice);// check input right values
		} while (check);
		return choice;
	}

	public static  void clearConcolse() {
		try
		{
			final String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
				Runtime.getRuntime().exec("cls");
			}
			else
			{
				System.out.print("\033[H\033[2J");  
				System.out.flush();  
			}
		}
		catch (final Exception e)
		{
			//  Handle any exceptions.
		}

	}

}
