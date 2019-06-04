package menu.select_team;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONException;

import menu.Menu;
import mlb.GeneralInfo;
import mlb.api.players_stats.StatsRequest;
import mlb.api.teams.TeamsRequests;

public class BattingStats extends Menu {

	private static int k = 1;
	
	public static void show(TeamsRequests team) throws IOException, JSONException {
		clearConcolse();
		System.out.println("\t\t\t Batting Stats\n");
		int a = 25;
		String text = "Name";
		System.out.print(text + " ");
		gap(text, a);
		a = 3;
		text = "Pos";
		System.out.print(text + " ");
		gap(text, a);
		a = 3;
		text = "G";
		System.out.print(text + " ");
		gap(text, a);
		text = "AB";
		System.out.print(text + " ");
		gap(text, a);
		text = "R";
		System.out.print(text + " ");
		gap(text, a);
		text = "H";
		System.out.print(text + " ");
		gap(text, a);
		text = "2B";
		System.out.print(text + " ");
		gap(text, a);
		text = "3B";
		System.out.print(text + " ");
		gap(text, a);
		text = "HR";
		System.out.print(text + " ");
		gap(text, a);
		text = "RBI";
		System.out.print(text + " ");
		gap(text, a);
		text = "BB";
		System.out.print(text + " ");
		gap(text, a);
		text = "IBB";
		System.out.print(text + " ");
		gap(text, a);
		text = "SO";
		System.out.print(text + " ");
		gap(text, a);
		text = "SB";
		System.out.print(text + " ");
		gap(text, a);
		text = "CS";
		System.out.print(text + " ");
		gap(text, a);
		a = 5;
		text = " AVG";
		System.out.print(text + " ");
		gap(text, a);
		text = " OBP";
		System.out.print(text + " ");
		gap(text, a);
		text = " SLG";
		System.out.print(text + " ");
		gap(text, a);
		text = " OPS";
		System.out.print(text + " ");
		gap(text, a);
		System.out.println("\n==================================================================================================================================");
		int pos = 1;
		int j = 0;
		pos = pritnPlayersStats(team.getFirstBasePlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getSecondBasePlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getThirdBasePlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getCatcherPlayers(),team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getShortStopPlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getLeftFieldPlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getCenterFieldPlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getRightFieldPlayers(), team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getDesignHitterPlayers(),team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getOfPlayers(),team.getNumberInPotition(j), pos);
		j++;
		pos = pritnPlayersStats(team.getPitcherPlayers(),team.getNumberInPotition(j), pos);
		
	}
	
	private static int pritnPlayersStats(String[][] array, int playersInPosition, int pos) throws IOException, JSONException {
		StatsRequest stats = null;
		stats = new StatsRequest();
		int year = Calendar.getInstance().get(Calendar.YEAR); // Get Current Year
		String season = Integer.toString(year); // Default icurrent year
		String position = "" ;
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
		for(int i = 0; i < playersInPosition; i++) {
			String name = array [i][0];
			String player_id = null;
			player_id = array [i][1];
			stats.getStatsromAPI("R", season, player_id, 0);
			stats.storeStats();
			if ( pos == 12) {
				System.out.println("==================================================================================================================================");
				pos = 11;
			}
			int a = 25;
			String text = Integer.toString(k) + "." + name;
			System.out.print(text + " ");
			gap(text, a);
			text = position;
			a = 3;
			System.out.print(text + " ");
			gap(text, a);
			String[] hittingStats = stats.getStats();
			a = 3;
			for (int j = 0; j < GeneralInfo.battingStats; j++) {
				text = hittingStats[j];
				System.out.print(text + " ");
				gap(text, a);
				if ( j == 12) {
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
