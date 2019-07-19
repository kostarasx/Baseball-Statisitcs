package menu;

import java.io.IOException;

import org.json.JSONException;

import menu.select_team.SelectTeamMenu;

public class MainMenu extends Menu {

	public static void show() throws IOException, JSONException {
		clearConcolse();
		do {
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
			System.out.println(0 + "." + "Exit");
			int choice = getUserInput(0, j);
			switch (choice) {
			case 0:
				exitProgramm();
				break;
			case 1:
				check = SelectTeamMenu.show();
				break;
			case 2:
				System.out.println("TODO");
			}
		} while (check);
	}

}
