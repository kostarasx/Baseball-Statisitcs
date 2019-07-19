package menu.select_team;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONException;

import menu.Menu;
import mlb.GeneralInfo;
import mlb.api.players_stats.StatsRequest;
import mlb.api.teams.TeamsRequests;

public class BattingStats extends Menu {

	private static int k;

	public BattingStats() {
	}

	public static boolean show(TeamsRequests team, String name) throws IOException, JSONException {
		do {
			textGap = "========================================================================================================================================";
			k = 1;
			clearConcolse();
			System.out.println("\t\t\t\t Batting Stats\n");
			System.out.println(name);
			int a = 25;
			printInfo("Name", a);
			a = 3;
			printInfo("Pos", a);
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
			System.out.println("\n" + textGap);
			int pos = 1;
			int j = 0;
			pos = pritnPlayersStats(team.getFirstBasePlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getSecondBasePlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getThirdBasePlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getCatcherPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getShortStopPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getLeftFieldPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getCenterFieldPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getRightFieldPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getDesignHitterPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getOfPlayers(), team.getNumberInPotition(j), pos);
			j++;
			pos = pritnPlayersStats(team.getPitcherPlayers(), team.getNumberInPotition(j), pos);
			System.out.println(textGap);
			System.out.println((team.getTeamSize() + 1) + "." + "Back");
			System.out.println(0 + "." + "Exit");
			int choice = getUserInput(0, team.getTeamSize() + 1);
			if (choice == 0) {
				exitProgramm();
			} else if (choice == team.getTeamSize() + 1) {
				return true;
			} else {
				StatsRequest stats = null;
				stats = new StatsRequest();
				stats.playerCareerStats(team);
				String[][] roster = stats.getRoster();
				check = PlayerCareerBatingStats.show(roster[choice - 1][0], roster[choice - 1][1], stats);
			}
		} while (check);
		return true;

	}

	private static int pritnPlayersStats(String[][] array, int playersInPosition, int pos)
			throws IOException, JSONException {
		StatsRequest stats = null;
		stats = new StatsRequest();
		int year = Calendar.getInstance().get(Calendar.YEAR); // Get Current Year
		String season = Integer.toString(year); // Default icurrent year
		String position = "";
		switch (pos) {
		case 1:
			position = "1Β";
			break;
		case 2:
			position = "2Β";
			break;
		case 3:
			position = "3Β";
			break;
		case 4:
			position = "C";
			break;
		case 5:
			position = "SS";
			break;
		case 6:
			position = "LF";
			break;
		case 7:
			position = "CF";
			break;
		case 8:
			position = "RF";
			break;
		case 9:
			position = "DH";
			break;
		case 10:
			position = "OF";
			break;
		case 11:
			position = "P";
			pos = 12;
			break;
		}
		for (int i = 0; i < playersInPosition; i++) {
			String name = array[i][0];
			String player_id = array[i][1];
			stats.getStatsromAPI("R", season, player_id, 1);
			stats.storeStats(false, 0);
			if (pos == 12) {
				System.out.println(textGap);
				pos = 11;
			}
			int a = 25;
			printInfo(Integer.toString(k) + "." + name, a);
			a = 3;
			printInfo(position, a);
			String[] hittingStats = stats.getStats();
			for (int j = 0; j < GeneralInfo.battingStats; j++) {
				printInfo(hittingStats[j], a);
				if (j == 14) {
					a = 5;
				}
			}
			k++;
			System.out.println();
		}
		pos++;
		return pos;
	}
}
