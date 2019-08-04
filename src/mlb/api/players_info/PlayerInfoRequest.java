package mlb.api.players_info;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import mlb.GeneralInfo;
import mlb.api.Request;

public class PlayerInfoRequest extends Request {

	private PlayerInfoURI infoURI;
	private int debutYear;
	private int endYear;

	public int getDebutYear() {
		return debutYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public PlayerInfoRequest() {
		infoURI = new PlayerInfoURI();
		debutYear = 0;
		endYear = 0;
	}

	public void getInfoFromApi(String player_id, int flag) throws IOException {
		String url = infoURI.requestInfoURL(player_id, 1);
		requestDataFromMLB(url);
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

	// Data Handling

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
