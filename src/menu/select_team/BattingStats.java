package menu.select_team;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONException;

import menu.Menu;
import mlb.GeneralInfo;
import mlb.api.players_stats.StatsRequest;
import mlb.api.teams.TeamsRequests;

public class BattingStats extends Menu {

	private static int playerNumber;

	public BattingStats() {
	}

	public static boolean show(TeamsRequests team, String name) throws IOException, JSONException {
		do {
			textGap = "========================================================================================================================================";
			playerNumber = 1;
			clearConcolse();
			System.out.println("\t\t\t\t Batting Stats\n");
			System.out.println(name);
			int gap = 25;
			printInfo("Name", gap);
			gap = 3;
			printInfo("Pos", gap);
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
				stats.createTeamRoster(team);
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
			stats.getBattingStatsromAPI("R", season, player_id, 1);
			stats.storeBattingStats(false, 0);
			if (pos == 12) {
				System.out.println(textGap);
				pos = 11;
			}
			int gap = 25;
			printInfo(Integer.toString(playerNumber) + "." + name, gap);
			gap = 3;
			printInfo(position, gap);
			String[] hittingStats = stats.getStats();
			for (int j = 0; j < GeneralInfo.battingStats; j++) {
				printInfo(hittingStats[j], gap);
				if (j == 14) {
					gap = 5;
				}
			}
			playerNumber++;
			System.out.println();
		}
		pos++;
		return pos;
	}
}
