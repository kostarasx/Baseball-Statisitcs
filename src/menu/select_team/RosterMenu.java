package menu.select_team;

import java.io.IOException;

import org.json.JSONException;

import menu.Menu;
import mlb.api.teams.TeamsRequests;

public class RosterMenu extends Menu {

	public static boolean show(TeamsRequests team, String name) throws IOException, JSONException {
		do {
			clearConcolse();
			System.out.println(name + "\n");
			System.out.println("Total Players: " + team.getTeamSize() + "\n");
			int j = 1;
			System.out.println(j + "." + "Bating Stats");
			j++;
			System.out.println(j + "." + "Pitching Stats");
			j++;
			System.out.println(j + "." + "Fielding Stats");
			j++;
			System.out.println(j + "." + "Back");
			System.out.println(0 + "." + "Exit");
			j++;
			int choice = getUserInput(0, j);
			if (choice == j) {
				return true;
			} else if (choice == 0) {
				exitProgramm();
			}
			switch (choice) {
			case 1:
				check = BattingStats.show(team, name);
				break;
			}
		} while (check);
		return true;
	}

}
