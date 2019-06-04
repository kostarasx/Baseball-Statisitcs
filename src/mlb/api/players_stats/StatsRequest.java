package mlb.api.players_stats;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;

public class StatsRequest {
	
	
	private String[] stats;
	private StringBuffer response;
	private PlayerStatsURI hiitingStats;
	
	public StatsRequest() {
		hiitingStats = new PlayerStatsURI();
		stats = new String [GeneralInfo.battingStats];
		response = null;
	}
	
	public String[] getStats() {
		return stats;
	}
	
	public void getStatsromAPI(String game_type, String season, String player_id, int flag) throws IOException {
		String url = hiitingStats.requestStatsURL(game_type, season, player_id, flag);
		response = Request.requestDataFromMLB(url);
	}
	
	public void storeStats() throws JSONException   {
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject res = null;
		try {
			res = myResponse.getJSONObject("sport_hitting_tm").getJSONObject("queryResults").getJSONObject("row");
			int i = 0;
			stats [i] = res.getString("g");
			i++;
			stats [i] = res.getString("ab");
			i++;
			stats [i] = res.getString("r");
			i++;
			stats [i] = res.getString("h");
			i++;
			stats [i] = res.getString("d");
			i++;
			stats [i] = res.getString("t");
			i++;
			stats [i] = res.getString("hr");
			i++;
			stats [i] = res.getString("rbi");
			i++;
			stats [i] = res.getString("bb");
			i++;
			stats [i] = res.getString("ibb");
			i++;
			stats [i] = res.getString("so");
			i++;
			stats [i] = res.getString("sb");
			i++;
			stats [i] = res.getString("cs");
			i++;
			stats [i] = res.getString("avg");
			i++;
			stats [i] = res.getString("obp");
			i++;
			stats [i] = res.getString("slg");
			i++;
			stats [i] = res.getString("ops");
		} catch (JSONException e) {
			for(int i = 0; i < GeneralInfo.battingStats; i++) {
				stats[i] = "-";
			}
			
		}
		
	}
}
