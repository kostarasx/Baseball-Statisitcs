package mlb.api.players_stats;

import mlb.api.URILinks;

public class PlayerStatsURI extends URILinks {

	private String playerInfoPath = "/json/named.player_info.bam?sport_code='mlb'";
	private String hittingPath = "/json/named.sport_hitting_tm.bam?league_list_id='mlb'";
	private String carrerHittingPath = "/json/named.sport_career_hitting.bam?league_list_id='mlb'";
	private String gameParam = "game_type=";
	private String game_type;
	private String seasonParam = "season=";
	private String season;
	private String playerIdParam = "player_id=";
	private String player_id;

	public PlayerStatsURI() {
		game_type = "";
		season = "";
		player_id = "";
	}

	public String requestBattingStatsURL(String game_type, String season, String player_id, int flag) {
		String param1;
		String param2;
		String param3;
		String param4 = "";
		this.game_type = addDel(game_type);
		param1 = delim + gameParam + this.game_type;
		this.season = addDel(season);
		param2 = delim + seasonParam + this.season;
		this.player_id = addDel(player_id);
		param3 = delim + playerIdParam + this.player_id;
		if (flag > 0) {
			param4 = in_exParam(hittingPath);
			String temp = delim + param4 + inParam;
			param4 = temp + "g" + temp + "ab" + temp + "r" + temp + "h" + temp + "d" + temp + "t";
			param4 = param4 + temp + "hr" + temp + "rbi" + temp + "bb" + temp + "ibb" + temp + "so";
			param4 = param4 + temp + "sb" + temp + "cs" + temp + "avg" + temp + "obp" + temp + "slg";
			param4 = param4 + temp + "ops" + temp + "sf" + temp + "hbp";
			if (flag == 2) {
				param4 = param4 + temp + "team_abbrev";
			}
		}
		String link = host + hittingPath + param1 + param2 + param3 + param4;
		return link;
	}

	public String requestCareerStatsURL(String game_type, String player_id, int flag) {
		String param1;
		String param3;
		String param4 = "";
		this.game_type = addDel(game_type);
		param1 = delim + gameParam + this.game_type;
		this.player_id = addDel(player_id);
		param3 = delim + playerIdParam + this.player_id;
		if (flag == 1) {
			param4 = in_exParam(carrerHittingPath);
			String temp = delim + param4 + inParam;
			param4 = temp + "g" + temp + "ab" + temp + "r" + temp + "h" + temp + "d" + temp + "t";
			param4 = param4 + temp + "hr" + temp + "rbi" + temp + "bb" + temp + "ibb" + temp + "so";
			param4 = param4 + temp + "sb" + temp + "cs" + temp + "avg" + temp + "obp" + temp + "slg";
			param4 = param4 + temp + "ops" + temp + "sf" + temp + "hbp";

		}
		String link = host + carrerHittingPath + param1 + param3 + param4;
		return link;
	}

	public String requestInfoURL(String player_id, int flag) {
		String param1;
		String param2 = "";
		this.player_id = addDel(player_id);
		param1 = delim + playerIdParam + this.player_id;
		if (flag == 1) {
			param2 = in_exParam(playerInfoPath);
			String temp = delim + param2 + inParam;
			param2 = temp + "birth_country" + temp + "height_inches" + temp + "height_feet" + temp + "pro_debut_date"
					+ temp + "birth_date" + temp + "primary_position_txt" + temp + "weight" + temp + "end_date" + temp
					+ "throws" + temp + "bats" + temp + "age";

		}
		String link = host + playerInfoPath + param1 + param2;
		return link;
	}
}
