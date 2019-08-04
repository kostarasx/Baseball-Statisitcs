package mlb.api.players_info;

import mlb.api.URILinks;

public class PlayerInfoURI extends URILinks {
	private String playerInfoPath = "/json/named.player_info.bam?sport_code='mlb'";
	private String playerIdParam = "player_id=";
	private String player_id;

	public PlayerInfoURI() {
		player_id = "";
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
