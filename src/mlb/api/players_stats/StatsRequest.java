package mlb.api.players_stats;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;
import mlb.api.teams.TeamsRequests;

public class StatsRequest extends Request {

	private String[] stats;
	private StringBuffer response;
	private PlayerStatsURI playerStats;
	private String[][] roster;
	private int debutYear;
	private int endYear;
	private int size;
	private String team;
	private int teamsPlayed;
	private int flag;

	public StatsRequest() {
		playerStats = new PlayerStatsURI();
		stats = new String[GeneralInfo.battingStats];
		response = null;
		roster = null;
		size = 0;
		debutYear = 0;
		endYear = 0;
		flag = 0;
		team = "";
	}

	public String[] getStats() {
		return stats;
	}

	public String[][] getRoster() {
		return roster;
	}

	public int getDebutYear() {
		return debutYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public String getTeam() {
		return team;
	}

	public int getTeamsPlayed() {
		return teamsPlayed;
	}

	public void getStatsromAPI(String game_type, String season, String player_id, int flag) throws IOException {
		String url = playerStats.requestBattingStatsURL(game_type, season, player_id, flag);
		response = requestDataFromMLB(url);
		if (flag == 2) {
			this.flag = flag;
		}
	}

	public void getCarrertHittingStatsFromAPI(String game_type, String player_id, int flag) throws IOException {
		String url = playerStats.requestCareerStatsURL(game_type, player_id, flag);
		response = requestDataFromMLB(url);
	}

	public void storeStats(boolean flag2, int index) throws JSONException {
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults");
			teamsPlayed = Integer.parseInt(res.getString("totalSize"));
			if (teamsPlayed <= 1 && flag2 == false) {
				res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONObject("row");
				createStatsArray(res);
			} else if (flag2 == false) {
				int temp[] = new int[GeneralInfo.battingStats];
				for (int j = 0; j < teamsPlayed; j++) {
					res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONArray("row")
							.getJSONObject(j);
					createStatsArray(res);
					for (int i = 0; i < GeneralInfo.battingStats - 4; i++) {
						temp[i] = temp[i] + Integer.parseInt(stats[i]);
					}
				}
				float avg = (float) temp[3] / temp[1];// avg = H/AB
				float obp = (float) (temp[3] + temp[8] + temp[14]) / (temp[1] + temp[8] + temp[13]);// obp=(H+BB+HBP)/(AB+BB+SF)
				float slg = (float) ((temp[3] - temp[4] - temp[5] - temp[6]) + 2 * temp[4] + 3 * temp[5] + 4 * temp[6])
						/ temp[1];// slg=(1B+2*D+3*T+4*HR)/AB
				float ops = obp + slg;
				for (int i = 0; i < GeneralInfo.battingStats; i++) {
					stats[i] = Integer.toString(temp[i]);
				}
				stats[GeneralInfo.battingStats - 4] = findPercentage(avg);
				stats[GeneralInfo.battingStats - 3] = findPercentage(obp);
				stats[GeneralInfo.battingStats - 2] = findPercentage(slg);
				stats[GeneralInfo.battingStats - 1] = findPercentage(ops);
			} else if (flag2) {
				res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONArray("row")
						.getJSONObject(index);
				createStatsArray(res);
			}
			if (flag == 2) {
				team = res.getString("team_abbrev");
				flag = 0;
			}
		} catch (JSONException e) {
			for (int i = 0; i < GeneralInfo.battingStats; i++) {
				stats[i] = "-";
			}

		}
	}

	public void storeCareerStats() throws JSONException {
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("sport_career_hitting").getJSONObject("queryResults").getJSONObject("row");
			createStatsArray(res);
		} catch (JSONException e) {
			for (int i = 0; i < GeneralInfo.battingStats; i++) {
				stats[i] = "-";
			}

		}
	}

	public void playerCareerStats(TeamsRequests team) {
		roster = new String[team.getTeamSize()][2];
		addPlayersToArray(roster, team.getFirstBasePlayers(), team.getNumberInPotition(0));
		addPlayersToArray(roster, team.getSecondBasePlayers(), team.getNumberInPotition(1));
		addPlayersToArray(roster, team.getThirdBasePlayers(), team.getNumberInPotition(2));
		addPlayersToArray(roster, team.getCatcherPlayers(), team.getNumberInPotition(3));
		addPlayersToArray(roster, team.getShortStopPlayers(), team.getNumberInPotition(4));
		addPlayersToArray(roster, team.getLeftFieldPlayers(), team.getNumberInPotition(5));
		addPlayersToArray(roster, team.getCenterFieldPlayers(), team.getNumberInPotition(6));
		addPlayersToArray(roster, team.getRightFieldPlayers(), team.getNumberInPotition(7));
		addPlayersToArray(roster, team.getDesignHitterPlayers(), team.getNumberInPotition(8));
		addPlayersToArray(roster, team.getOfPlayers(), team.getNumberInPotition(9));
		addPlayersToArray(roster, team.getPitcherPlayers(), team.getNumberInPotition(10));
	}

	private void addPlayersToArray(String[][] roster, String[][] array, int numberInPosition) {
		for (int i = 0; i < numberInPosition; i++) {
			for (int j = 0; j < 2; j++) {
				roster[size][j] = array[i][j];
			}
			size++;
		}
	}

	public void getInfoFromApi(String player_id, int flag) throws IOException {
		String url = playerStats.requestInfoURL(player_id, 1);
		response = requestDataFromMLB(url);
	}

	public String[] getPlayerInfo() throws JSONException {
		String[] info = new String[GeneralInfo.playerInfo];
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("player_info").getJSONObject("queryResults").getJSONObject("row");
			int i = 0;
			info[i] = res.getString("birth_country");
			i++;
			String birthDate = res.getString("birth_date");
			info[i] = convertDate(birthDate, 0);
			i++;
			info[i] = res.getString("age");
			i++;
			String inch = res.getString("height_inches");
			String feets = res.getString("height_feet");
			info[i] = feetsToMeters(feets, inch);
			i++;
			String weight = res.getString("weight");
			info[i] = poundsToKillos(weight);
			i++;
			info[i] = res.getString("primary_position_txt");
			i++;
			info[i] = res.getString("throws");
			i++;
			info[i] = res.getString("bats");
			i++;
			String debut = res.getString("pro_debut_date");
			info[i] = convertDate(debut, 1);
			i++;
			String end = res.getString("end_date");
			info[i] = convertDate(end, 2);
		} catch (JSONException e) {
			for (int i = 0; i < GeneralInfo.playerInfo; i++) {
				info[i] = "-";
			}
		}
		return info;
	}

	private void createStatsArray(JSONObject res) throws JSONException {
		int i = 0;
		stats[i] = res.getString("g");
		i++;
		stats[i] = res.getString("ab");
		i++;
		stats[i] = res.getString("r");
		i++;
		stats[i] = res.getString("h");
		i++;
		stats[i] = res.getString("d");
		i++;
		stats[i] = res.getString("t");
		i++;
		stats[i] = res.getString("hr");
		i++;
		stats[i] = res.getString("rbi");
		i++;
		stats[i] = res.getString("bb");
		i++;
		stats[i] = res.getString("ibb");
		i++;
		stats[i] = res.getString("so");
		i++;
		stats[i] = res.getString("sb");
		i++;
		stats[i] = res.getString("cs");
		i++;
		stats[i] = res.getString("sf");
		i++;
		stats[i] = res.getString("hbp");
		i++;
		stats[i] = res.getString("avg");
		i++;
		stats[i] = res.getString("obp");
		i++;
		stats[i] = res.getString("slg");
		i++;
		stats[i] = res.getString("ops");
	}

	private String findPercentage(float stat) {
		stat = (float) (Math.round(stat * 1000.0) / 1000.0);
		String stt = "";
		if (stat < 1) {
			// int div = (int) (stat / 1);
			stt = ".";
		}
		stt = stt + Integer.toString((int) (stat * 1000));
		return stt;
	}

	private String convertDate(String date, int flag) {
		if (date.equals("")) {
			return "-";

		}
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String Date = date.substring(8, 10);
		if (flag == 1) {
			debutYear = Integer.parseInt(year);
		} else if (flag == 2) {
			endYear = Integer.parseInt(year);
		}
		return Date + "/" + month + "/" + year;
	}

	private String feetsToMeters(String feets, String inch) {
		float feet = Float.parseFloat(feets);
		double meters = (float) (feet * 0.305);
		float inc = Float.parseFloat(inch);
		meters = meters + inc * 0.0254;
		meters = Math.round(meters * 100.0) / 100.0;
		return Double.toString(meters);
	}

	private String poundsToKillos(String pounds) {
		float pound = Float.parseFloat(pounds);
		float kg = (float) (pound * 0.45359237);
		int killos = Math.round(kg);
		return Integer.toString(killos) + " kg";

	}

}
