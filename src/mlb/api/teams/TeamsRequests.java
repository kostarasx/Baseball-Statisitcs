package mlb.api.teams;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;

public class TeamsRequests extends Request {

	private String[] alTeams;
	private String[] nlTeams;
	private String[] alTeamsId;
	private String[] nlTeamsId;
	private int teamSize;
	private String[][] firstBasePlayers;
	private String[][] secondBasePlayers;
	private String[][] thirdBasePlayers;
	private String[][] shortStopPlayers;
	private String[][] leftFieldPlayers;
	private String[][] centerFieldPlayers;
	private String[][] rightFieldPlayers;
	private String[][] catcherPlayers;
	private String[][] pitcherPlayers;
	private String[][] designHitterPlayers;
	private String[][] ofPlayers;
	private int[] numberInPotition;
	private TeamsURILinks teamURI;

	public TeamsRequests() {
		teamURI = new TeamsURILinks();
		alTeams = new String[GeneralInfo.numberOfALTeams];
		nlTeams = new String[GeneralInfo.numberOfNLTeams];
		alTeamsId = new String[GeneralInfo.numberOfALTeams];
		nlTeamsId = new String[GeneralInfo.numberOfNLTeams];
		numberInPotition = new int[GeneralInfo.totalPotition];
		for (int i = 0; i < GeneralInfo.numberOfALTeams; i++) {
			alTeams[i] = "";
			alTeamsId[i] = "";
		}
		for (int i = 0; i < GeneralInfo.numberOfNLTeams; i++) {
			nlTeams[i] = "";
			nlTeamsId[i] = "";
		}
		for (int i = 0; i < GeneralInfo.totalPotition; i++) {
			numberInPotition[i] = 0;
		}
		firstBasePlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		secondBasePlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		thirdBasePlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		shortStopPlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		leftFieldPlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		centerFieldPlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		rightFieldPlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		catcherPlayers = new String[GeneralInfo.maxPlayers / 5][GeneralInfo.maxPlayers / 5];
		pitcherPlayers = new String[GeneralInfo.maxPlayers][GeneralInfo.maxPlayers];
		designHitterPlayers = new String[GeneralInfo.maxPlayers / 10][GeneralInfo.maxPlayers / 10];
		ofPlayers = new String[GeneralInfo.maxPlayers / 10][GeneralInfo.maxPlayers / 10];
	}

	public String[] getAlTeams() {
		return alTeams;
	}

	public String[] getNlTeams() {
		return nlTeams;
	}

	public String getAlTeamId(int index) {
		return alTeamsId[index];
	}

	public String getNlTeamId(int index) {
		return nlTeamsId[index];
	}

	public int getTeamSize() {
		return teamSize;
	}

	public String[][] getFirstBasePlayers() {
		return firstBasePlayers;
	}

	public String[][] getSecondBasePlayers() {
		return secondBasePlayers;
	}

	public String[][] getThirdBasePlayers() {
		return thirdBasePlayers;
	}

	public String[][] getShortStopPlayers() {
		return shortStopPlayers;
	}

	public String[][] getLeftFieldPlayers() {
		return leftFieldPlayers;
	}

	public String[][] getCenterFieldPlayers() {
		return centerFieldPlayers;
	}

	public String[][] getRightFieldPlayers() {
		return rightFieldPlayers;
	}

	public String[][] getCatcherPlayers() {
		return catcherPlayers;
	}

	public String[][] getPitcherPlayers() {
		return pitcherPlayers;
	}

	public String[][] getDesignHitterPlayers() {
		return designHitterPlayers;
	}

	public String[][] getOfPlayers() {
		return ofPlayers;
	}

	public int getNumberInPotition(int index) {
		return numberInPotition[index];
	}

	public void getTeamsFromAPI(String all_star_sw, String sort_order, String season, int flag) throws IOException {
		String url = teamURI.requesTeamURL(all_star_sw, sort_order, season, flag);
		requestDataFromMLB(url);
	}

	// Seperate Teams base of Division AL or NL
	public void seperateTeams() throws JSONException {
		int alIndex = 0;
		int nlIndex = 0;
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res;
		for (int i = 0; i < GeneralInfo.number0fTeams; i++) {
			res = myResponse.getJSONObject("team_all_season").getJSONObject("queryResults").getJSONArray("row")
					.getJSONObject(i);
			String league = res.getString("league");
			String name = res.getString("name_display_full");
			String teamId = res.getString("team_id");
			if (league.equals("AL")) {
				alTeams[alIndex] = name;
				alTeamsId[alIndex] = teamId;
				alIndex++;
			} else if (league.equals("NL")) {
				nlTeams[nlIndex] = name;
				nlTeamsId[nlIndex] = teamId;
				nlIndex++;
			}
		}
	}

	public void getRosterFromAPI(String team_id) throws IOException {
		String url = teamURI.requestRosterURI(team_id, 1);
		requestDataFromMLB(url);
	}

	public void seperatePlayers() throws JSONException {
		int temp;
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res;
		res = myResponse.getJSONObject("roster_40").getJSONObject("queryResults");
		String size = res.getString("totalSize");
		teamSize = Integer.parseInt(size);
		for (int i = 0; i < teamSize; i++) {
			res = myResponse.getJSONObject("roster_40").getJSONObject("queryResults").getJSONArray("row")
					.getJSONObject(i);
			String potition = res.getString("position_txt");
			String name = res.getString("name_display_first_last");
			String playerId = res.getString("player_id");
			switch (potition) {
			case "1B":
				temp = numberInPotition[0];
				firstBasePlayers[temp][0] = name;
				firstBasePlayers[temp][1] = playerId;
				temp++;
				numberInPotition[0] = temp;
				break;
			case "2B":
				temp = numberInPotition[1];
				secondBasePlayers[temp][0] = name;
				secondBasePlayers[temp][1] = playerId;
				temp++;
				numberInPotition[1] = temp;
				break;
			case "3B":
				temp = numberInPotition[2];
				thirdBasePlayers[temp][0] = name;
				thirdBasePlayers[temp][1] = playerId;
				temp++;
				numberInPotition[2] = temp;
				break;
			case "C":
				temp = numberInPotition[3];
				catcherPlayers[temp][0] = name;
				catcherPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[3] = temp;
				break;
			case "SS":
				temp = numberInPotition[4];
				shortStopPlayers[temp][0] = name;
				shortStopPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[4] = temp;
				break;
			case "LF":
				temp = numberInPotition[5];
				leftFieldPlayers[temp][0] = name;
				leftFieldPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[5] = temp;
				break;
			case "CF":
				temp = numberInPotition[6];
				centerFieldPlayers[temp][0] = name;
				centerFieldPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[6] = temp;
				break;
			case "RF":
				temp = numberInPotition[7];
				rightFieldPlayers[temp][0] = name;
				rightFieldPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[7] = temp;
				break;
			case "DH":
				temp = numberInPotition[8];
				designHitterPlayers[temp][0] = name;
				designHitterPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[8] = temp;
				break;
			case "OF":
				temp = numberInPotition[9];
				ofPlayers[temp][0] = name;
				ofPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[9] = temp;
				break;
			case "P":
				temp = numberInPotition[10];
				pitcherPlayers[temp][0] = name;
				pitcherPlayers[temp][1] = playerId;
				temp++;
				numberInPotition[10] = temp;
				break;
			default:
				System.out.println("no match");
			}
		}
	}
}
