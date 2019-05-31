package MLB.API;

import java.util.Calendar;

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
	private String delim = "&";
	private String del ="'";
	
	public TeamsURILinks() {
		all_star_sw = "";
		sort_order = "";
		int year = Calendar.getInstance().get(Calendar.YEAR); // Get Current Year
		season = Integer.toString(year); // Default icurrent year
	}
	
	
	public String requestLink(String all_star_sw, String sort_order, String season) {
		String param1 = "";
		String param2 = "";
		String param3;
		if (all_star_sw != "") {
			this.all_star_sw = del + all_star_sw + del;
			param1 = delim + this.all_star_sw;
		}
		if (sort_order != "") {
			this.sort_order = del + this.sort_order + del;
			param2 = delim + this.sort_order;
		}
		this.season = del + season + del;
		param3 = delim + this.season;
		String Link = host + path + param1 + param2 + param3;
		return Link;
	}
	
}
