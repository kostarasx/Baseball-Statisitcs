package menu.select_team;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONException;

import menu.Menu;
import mlb.GeneralInfo;
import mlb.api.players_stats.StatsRequest;

public class PlayerCareerBatingStats extends Menu {

	public static boolean show(String name, String player_id, StatsRequest stats) throws IOException, JSONException {
		do {
			clearConcolse();
			textGap = "\n======================================================================================================================";
			int gap;
			stats.getInfoFromApi(player_id, 1);
			String[] info = stats.getPlayerInfo();
			gap = 15;
			printPlayerInfo("Name:", name, gap);
			int i = 0;
			printPlayerInfo("Country:", info[i], gap);
			i++;
			printPlayerInfo("Birth Date:", info[i], gap);
			i++;
			printPlayerInfo("Age:", info[i], gap);
			i++;
			printPlayerInfo("Height:", info[i], gap);
			i++;
			printPlayerInfo("Weight:", info[i], gap);
			i++;
			printPlayerInfo("Position:", info[i], gap);
			i++;
			printPlayerInfo("Throws:", info[i], gap);
			i++;
			printPlayerInfo("Bats:", info[i], gap);
			i++;
			printPlayerInfo("Pro Debute:", info[i], gap);
			i++;
			printPlayerInfo("Retire:", info[i], gap);
			System.out.println("\n");
			System.out.println("\t\t\t\t Career Bating Stats\n");
			gap = 5;
			printInfo("Year", gap);
			printInfo("Team", gap);
			gap = 3;
			printInfo("G", gap);
			printInfo("AB", gap);
			printInfo("R", gap);
			printInfo("H", gap);
			printInfo("2B", gap);
			printInfo("3B", gap);
			printInfo("HR", gap);
			printInfo("RBI", gap);
			printInfo("BB", gap);
			printInfo("IBB", gap);
			printInfo("SO", gap);
			printInfo("SB", gap);
			printInfo("CS", gap);
			printInfo("SF", gap);
			printInfo("HBP", gap);
			gap = 5;
			printInfo("AVG", gap);
			printInfo("OBP", gap);
			printInfo("SLG", gap);
			printInfo("OPS", gap);
			System.out.println(textGap);
			printPlayerCarrerHittingStats(stats, player_id);
			System.out.println(1 + "." + "Back");
			System.out.println(0 + "." + "Exit");
			int choice = getUserInput(0, 1);
			if (choice == 0) {
				exitProgramm();
			} else if (choice == 1) {
				return true;
			}
		} while (check);
		return true;
	}

	private static void printPlayerCarrerHittingStats(StatsRequest stats, String player_id)
			throws IOException, JSONException {
		int debut = stats.getDebutYear();
		int season = Calendar.getInstance().get(Calendar.YEAR);
		if (stats.getEndYear() != 0) {
			season = stats.getEndYear();
		} else if (debut == 0) {
			System.out.println("\t\t\t\tNo proffesional games");
			season = -1;
		}
		int gap;
		int counter = -1;
		boolean flag = false;
		for (int i = debut; i <= season; i++) {
			String year = Integer.toString(i);
			stats.getBattingStatsromAPI("R", year, player_id, 2);
			stats.storeBattingStats(flag, counter);
			if (stats.getTeamsPlayed() > 1) {
				counter++;
				i--;
				flag = true;
			}
			if (stats.getTeamsPlayed() == counter) {
				counter = -1;
				flag = false;
				i++;
			}
			gap = 5;
			printInfo(year, gap);
			printInfo(stats.getTeam(), gap);
			gap = 3;
			String[] hittingStats = stats.getStats();
			for (int j = 0; j < GeneralInfo.battingStats; j++) {
				printInfo(hittingStats[j], gap);
				if (j == 14) {
					gap = 5;
				}
			}
			System.out.println(textGap);
		}
		stats.getCarrertHittingStatsFromAPI("R", player_id, 1);
		stats.storeHittingCareerStats();
		String[] careerStats = stats.getStats();
		gap = 12;
		printInfo("Career Total", gap);
		gap = 3;
		for (int j = 0; j < GeneralInfo.battingStats; j++) {
			printInfo(careerStats[j], gap);
			if (j == 14) {
				gap = 5;
			}
		}
		System.out.println(textGap);
	}

}
