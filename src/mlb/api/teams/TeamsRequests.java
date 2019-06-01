package mlb.api.teams;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;

public class TeamsRequests {
	
	private String[] alTeams;
	private String[] nlTeams;
	private String[] alTeamsId;
	private String[] nlTeamsId;
	private StringBuffer response;
	
	public  TeamsRequests() {
		alTeams = new String[GeneralInfo.numberOfALTeams];
		nlTeams = new String[GeneralInfo.numberOfNLTeams];
		alTeamsId = new String[GeneralInfo.numberOfALTeams];
		nlTeamsId = new String[GeneralInfo.numberOfNLTeams];
		for (int i = 0; i < GeneralInfo.numberOfALTeams; i++) {
			alTeams[i] = "";
			alTeamsId[i] = "";
		}
		for (int i = 0; i < GeneralInfo.numberOfNLTeams; i++) {
			nlTeams[i] = "";
			nlTeamsId[i] = "";
		}
	}
	
	public String[] getAlTeams() {
		return alTeams;
	}
	
	
	public void setAlTeams(String[] alTeams) {
		this.alTeams = alTeams.clone();
	}
	
	
	public String[] getNlTeams() {
		return nlTeams;
	}
	
	
	public void setNlTeams(String[] nlTeams) {
		this.nlTeams = nlTeams.clone();
	}
	
	public void getTeamsFromAPI(String all_star_sw, String sort_order, String season) throws IOException {
		TeamsURILinks teamURI = new TeamsURILinks();
		String url = teamURI.requestURL(all_star_sw, sort_order, season, 1);
		response = Request.requestDataFromMLB(url);
	}
	
	// Seperate Teams base of Division AL or NL
	public void seperateTeams() throws JSONException {
		int alIndex = 0;
		int nlIndex = 0;
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res;
		for (int i = 0; i < GeneralInfo.number0fTeams; i++) {
			res = myResponse.getJSONObject("team_all_season").getJSONObject("queryResults").getJSONArray("row").getJSONObject(i);
			String league = res.getString("league");
			String name = res.getString("name_display_full");
			String teamId = res.getString("team_id");
			if (league.equals("AL")) {
				alTeams[alIndex] = name;
				alTeamsId[alIndex] = teamId;
				alIndex++;
			}
			else if (league.equals("NL")) {
				nlTeams[nlIndex] = name;
				nlTeamsId[nlIndex] = teamId;
				nlIndex++;
			}
			
	}
		
		
	}
	
	
}
