package mlb.api.players_batting_stats;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;
import mlb.api.teams.TeamsRequests;

public class BattingStatsRequest extends Request {

	private String[] battingStats;
	private PlayerBattingStatsURI playerStats;
	private String[][] roster;
	private int size;
	private String team;
	private int teamsPlayed;
	private int flag;

	public BattingStatsRequest() {
		playerStats = new PlayerBattingStatsURI();
		roster = null;
		size = 0;
		flag = 0;
		teamsPlayed = 0;
		team = "";
	}

	// Getters

	public String[] getStats() {
		return battingStats;
	}

	public String[][] getRoster() {
		return roster;
	}

	public String getTeam() {
		return team;
	}

	public int getTeamsPlayed() {
		return teamsPlayed;
	}

	// Create an array with full forster

	public void createTeamRoster(TeamsRequests team) {
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

	// Batting Stats

	public void getBattingStatsromAPI(String game_type, String season, String player_id, int flag) throws IOException {
		String url = playerStats.requestBattingStatsURL(game_type, season, player_id, flag);
		requestDataFromMLB(url);
		if (flag == 2) {
			this.flag = flag;
		}
	}

	public void getCarrertHittingStatsFromAPI(String game_type, String player_id, int flag) throws IOException {
		String url = playerStats.requestCareerStatsURL(game_type, player_id, flag);
		requestDataFromMLB(url);
	}

	public void storeBattingStats(boolean flag2, int index) throws JSONException {
		battingStats = new String[GeneralInfo.battingStats];
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults");
			teamsPlayed = Integer.parseInt(res.getString("totalSize"));
			if (teamsPlayed <= 1 && !flag2) {
				res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONObject("row");
				createBattingStatsArray(res);
			} else if (!flag2) {
				int temp[] = new int[GeneralInfo.battingStats];
				for (int j = 0; j < teamsPlayed; j++) {
					res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONArray("row")
							.getJSONObject(j);
					createBattingStatsArray(res);
					for (int i = 0; i < GeneralInfo.battingStats - 4; i++) {
						temp[i] = temp[i] + Integer.parseInt(battingStats[i]);
					}
				}
				float avg = (float) temp[3] / temp[1];// avg = H/AB
				float obp = (float) (temp[3] + temp[8] + temp[14]) / (temp[1] + temp[8] + temp[13]);// obp=(H+BB+HBP)/(AB+BB+SF)
				float slg = (float) ((temp[3] - temp[4] - temp[5] - temp[6]) + 2 * temp[4] + 3 * temp[5] + 4 * temp[6])
						/ temp[1];// slg=(1B+2*D+3*T+4*HR)/AB
				float ops = obp + slg;
				for (int i = 0; i < GeneralInfo.battingStats; i++) {
					battingStats[i] = Integer.toString(temp[i]);
				}
				battingStats[GeneralInfo.battingStats - 4] = findPercentage(avg);
				battingStats[GeneralInfo.battingStats - 3] = findPercentage(obp);
				battingStats[GeneralInfo.battingStats - 2] = findPercentage(slg);
				battingStats[GeneralInfo.battingStats - 1] = findPercentage(ops);
			} else if (flag2) {
				res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONArray("row")
						.getJSONObject(index);
				createBattingStatsArray(res);
			}
			if (flag == 2) {
				team = res.getString("team_abbrev");
				flag = 0;
			}
		} catch (JSONException e) {
			for (int i = 0; i < GeneralInfo.battingStats; i++) {
				battingStats[i] = "-";
			}

		}
	}

	public void storeHittingCareerStats() throws JSONException {
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("sport_career_hitting").getJSONObject("queryResults").getJSONObject("row");
			createBattingStatsArray(res);
		} catch (JSONException e) {
			for (int i = 0; i < GeneralInfo.battingStats; i++) {
				battingStats[i] = "-";
			}

		}
	}

	private void createBattingStatsArray(JSONObject res) throws JSONException {
		int i = 0;
		battingStats[i] = res.getString("g");
		i++;
		battingStats[i] = res.getString("ab");
		i++;
		battingStats[i] = res.getString("r");
		i++;
		battingStats[i] = res.getString("h");
		i++;
		battingStats[i] = res.getString("d");
		i++;
		battingStats[i] = res.getString("t");
		i++;
		battingStats[i] = res.getString("hr");
		i++;
		battingStats[i] = res.getString("rbi");
		i++;
		battingStats[i] = res.getString("bb");
		i++;
		battingStats[i] = res.getString("ibb");
		i++;
		battingStats[i] = res.getString("so");
		i++;
		battingStats[i] = res.getString("sb");
		i++;
		battingStats[i] = res.getString("cs");
		i++;
		battingStats[i] = res.getString("sf");
		i++;
		battingStats[i] = res.getString("hbp");
		i++;
		battingStats[i] = res.getString("avg");
		i++;
		battingStats[i] = res.getString("obp");
		i++;
		battingStats[i] = res.getString("slg");
		i++;
		battingStats[i] = res.getString("ops");
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
}
