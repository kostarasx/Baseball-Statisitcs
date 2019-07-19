package menu.select_team;

import java.io.IOException;

import org.json.JSONException;

import menu.Menu;
import mlb.GeneralInfo;
import mlb.api.teams.TeamsRequests;

public class SelectTeamMenu extends Menu {

	public static boolean show() throws IOException, JSONException {
		do {
			clearConcolse();
			System.out.println("\t\t\t\t" + "Select A Team");
			System.out.println();
			System.out.println("\t\t" + "American League" + "\t\t" + "National League");
			TeamsRequests team = new TeamsRequests();
			team.getTeamsFromAPI("N", "name_asc", "2019", 1);
			team.seperateTeams();
			String[] teamsAl = team.getAlTeams();
			String[] teamsNl = team.getNlTeams();
			for (int i = 0; i < GeneralInfo.numberOfALTeams; i++) {
				System.out.print("\t\t" + (i + 1) + "." + teamsAl[i]);
				System.out.println("\t" + (i + 1 + GeneralInfo.numberOfALTeams) + "." + teamsNl[i]);
			}
			System.out.println();
			System.out.println(GeneralInfo.number0fTeams + 1 + "." + "Back");
			System.out.println(0 + "." + "Exit");
			int choice = getUserInput(0, GeneralInfo.number0fTeams + 1);
			if (choice == GeneralInfo.number0fTeams + 1) {
				return true;
			} else if (choice == 0) {
				exitProgramm();
			} else {
				String team_id;
				String name;
				if (choice <= 15) {
					choice--;
					team_id = team.getAlTeamId(choice);
					name = teamsAl[choice];
				} else {
					choice = choice - 16;
					team_id = team.getNlTeamId(choice);
					name = teamsNl[choice];
				}
				team.getRosterFromAPI(team_id);
				team.seperatePlayers();
				check = RosterMenu.show(team, name);
			}
		} while (check);
		return true;
	}
}
