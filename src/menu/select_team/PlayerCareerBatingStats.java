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
			int a;
			stats.getInfoFromApi(player_id, 1);
			String[] info = stats.getPlayerInfo();
			a = 15;
			printPlayerInfo("Name:", name, a);
			int i = 0;
			printPlayerInfo("Country:", info[i], a);
			i++;
			printPlayerInfo("Birth Date:", info[i], a);
			i++;
			printPlayerInfo("Age:", info[i], a);
			i++;
			printPlayerInfo("Height:", info[i], a);
			i++;
			printPlayerInfo("Weight:", info[i], a);
			i++;
			printPlayerInfo("Position:", info[i], a);
			i++;
			printPlayerInfo("Throws:", info[i], a);
			i++;
			printPlayerInfo("Bats:", info[i], a);
			i++;
			printPlayerInfo("Pro Debute:", info[i], a);
			i++;
			printPlayerInfo("Retire:", info[i], a);
			System.out.println("\n");
			System.out.println("\t\t\t\t Career Bating Stats\n");
			a = 5;
			printInfo("Year", a);
			printInfo("Team", a);
			a = 3;
			printInfo("G", a);
			printInfo("AB", a);
			printInfo("R", a);
			printInfo("H", a);
			printInfo("2B", a);
			printInfo("3B", a);
			printInfo("HR", a);
			printInfo("RBI", a);
			printInfo("BB", a);
			printInfo("IBB", a);
			printInfo("SO", a);
			printInfo("SB", a);
			printInfo("CS", a);
			printInfo("SF", a);
			printInfo("HBP", a);
			a = 5;
			printInfo("AVG", a);
			printInfo("OBP", a);
			printInfo("SLG", a);
			printInfo("OPS", a);
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

	private static void printPlayerInfo(String info, String playerInfo, int a) {
		System.out.print(info);
		gap(info, a);
		System.out.println(playerInfo);
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
		int a;
		int counter = -1;
		boolean flag = false;
		for (int i = debut; i <= season; i++) {
			String year = Integer.toString(i);
			stats.getStatsromAPI("R", year, player_id, 2);
			stats.storeStats(flag, counter);
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
			a = 5;
			printInfo(year, a);
			printInfo(stats.getTeam(), a);
			a = 3;
			String[] hittingStats = stats.getStats();
			for (int j = 0; j < GeneralInfo.battingStats; j++) {
				printInfo(hittingStats[j], a);
				if (j == 14) {
					a = 5;
				}
			}
			System.out.println(textGap);
		}
		stats.getCarrertHittingStatsFromAPI("R", player_id, 1);
		stats.storeCareerStats();
		String[] careerStats = stats.getStats();
		a = 12;
		printInfo("Career Total", a);
		a = 3;
		for (int j = 0; j < GeneralInfo.battingStats; j++) {
			printInfo(careerStats[j], a);
			if (j == 14) {
				a = 5;
			}
		}
		System.out.println(textGap);
	}

}
