package menu;

import java.io.IOException;

import org.json.JSONException;

public class MainMenu extends Menu {

	public static void show() throws IOException, JSONException {
		System.out.println("Welcome to Baseball Statistics");
		System.out.println();
		int j = 1;
		System.out.println(j + "." + "Select A Team");
		j++;
		System.out.println(j + "." + "Live Socre");
		j++;
		System.out.println(j + "." + "Standings");
		j++;
		System.out.println(j + "." + "Schedule");
		j++;
		System.out.println(j + "." + "Exit");
		int choice = getUserInput(0, j);
		clearConcolse();
		switch (choice) {
		case 1:
			selectTeamMenu.show();
			break;
		case 2:
			System.out.println("TODO");
		}
	}

	
}
