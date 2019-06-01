package mlb.api.teams;

import java.util.Calendar;

import mlb.api.Request;

/*
 * A class for Mlb Teams URI LINKS
 */

/*
 * URI Parameters:
 * all_star_sw String (optional) Set to ‘Y’ for all star data, and ‘N’ for regular season.
 * sort_order String (optional) Field to sort results by.
 * season String (required)
 */

public class TeamsURILinks {


	private String host = "http://lookup-service-prod.mlb.com";
	private String path = "/json/named.team_all_season.bam?sport_code='mlb'";
	private String allStarParam = "all_star_sw=";
	private String all_star_sw;
	private String sortParam = "sort_order=";
	private String sort_order;
	private String seasonParam = "season=";
	private String season;
	private String inParam = "col_in=";
	private String delim ="&";
	private String del ="'";
	
	public TeamsURILinks() {
		all_star_sw = "";
		sort_order = "";
		int year = Calendar.getInstance().get(Calendar.YEAR); // Get Current Year
		season = Integer.toString(year); // Default icurrent year
		
	}
	
	
	/*
	 * Flag = 0 None
	 * Flag = 1 Include  Team Name Team League Team id
	 * Flag = 2 exclude
	 */
	public String requestURL(String all_star_sw, String sort_order, String season, int flag) {
		String param1 = "";
		String param2 = "";
		String param3;
		String param4 = "";
		if (all_star_sw != "") {
			this.all_star_sw = Request.addDel(all_star_sw);
			param1 = delim + allStarParam + this.all_star_sw;
		}
		if (sort_order != "") {
			this.sort_order = Request.addDel(sort_order);
			param2 = delim + sortParam + this.sort_order;
		}
		if (flag == 1) {
			param4 = Request.in_exParam(path);
			String temp = delim + param4 + inParam;
			param4 = temp + "name_display_full" + temp + "league" + temp + "team_id";
		}
		this.season = Request.addDel(season);
		param3 = delim + seasonParam + this.season;
		String Link = host + path + param1 + param2 + param3 + param4;
		return Link;
	}
	

	
}
