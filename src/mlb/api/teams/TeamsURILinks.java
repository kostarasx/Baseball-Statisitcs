package mlb.api.teams;

import java.util.Calendar;

import mlb.api.URILinks;

/*
 * A class for Mlb Teams URI LINKS
 */

/*
 * URI Parameters:
 * all_star_sw String (optional) Set to ‘Y’ for all star data, and ‘N’ for regular season.
 * sort_order String (optional) Field to sort results by.
 * season String (required)
 */

public class TeamsURILinks extends URILinks {

	private String teamPath = "/json/named.team_all_season.bam?sport_code='mlb'";
	private String allStarParam = "all_star_sw=";
	private String all_star_sw;
	private String sortParam = "sort_order=";
	private String sort_order;
	private String seasonParam = "season=";
	private String season;

	private String rosterPath = "/json/named.roster_40.bam?";
	private String teamIdParam = "team_id=";
	private String team_Id;

	public TeamsURILinks() {
		all_star_sw = "";
		sort_order = "";
		int year = Calendar.getInstance().get(Calendar.YEAR); // Get Current Year
		season = Integer.toString(year); // Default icurrent year
		team_Id = "";
	}

	/*
	 * Flag = 0 None Flag = 1 Include Team Name Team League Team id Flag = 2 exclude
	 */
	public String requesTeamURL(String all_star_sw, String sort_order, String season, int flag) {
		String param1 = "";
		String param2 = "";
		String param3;
		String param4 = "";
		if (all_star_sw != "") {
			this.all_star_sw = addDel(all_star_sw);
			param1 = delim + allStarParam + this.all_star_sw;
		}
		if (sort_order != "") {
			this.sort_order = addDel(sort_order);
			param2 = delim + sortParam + this.sort_order;
		}
		if (flag == 1) {
			param4 = in_exParam(teamPath);
			String temp = delim + param4 + inParam;
			param4 = temp + "name_display_full" + temp + "league" + temp + "team_id";
		}
		this.season = addDel(season);
		param3 = delim + seasonParam + this.season;
		String link = host + teamPath + param1 + param2 + param3 + param4;
		return link;
	}

	public String requestRosterURI(String team_id, int flag) {
		String param1;
		String param2 = "";
		team_Id = addDel(team_id);
		param1 = teamIdParam + team_Id;
		if (flag == 1) {
			param2 = in_exParam(rosterPath);
			String temp = delim + param2 + inParam;
			param2 = temp + "name_display_first_last" + temp + "position_txt" + temp + "player_id";
		}
		String link = host + rosterPath + param1 + param2;
		return link;
	}

}
