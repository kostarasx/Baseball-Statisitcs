package menu;

import java.io.IOException;

import org.json.JSONException;

import mlb.GeneralInfo;
import mlb.api.teams.TeamsRequests;

public class selectTeamMenu extends Menu {

	public static void show() throws IOException, JSONException {
		System.out.println("\t\t\t\t" + "Select A Team");
		System.out.println();
		System.out.println("\t\t" + "American League" + "\t\t" + "National League");
		TeamsRequests team = new TeamsRequests();
		team.getTeamsFromAPI("N", "name_asc", "2019");
		team.seperateTeams();
		String[] teamsAl = team.getAlTeams();
		String[] teamgNl = team.getNlTeams();
		for (int i = 0; i < GeneralInfo.numberOfALTeams; i++) {
			System.out.print("\t\t" + (i + 1) + "." + teamsAl[i]);
			System.out.println("\t" + (i + 1 + GeneralInfo.numberOfALTeams) + "." + teamgNl[i]);
		}
	}

}
