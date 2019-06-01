package mlb.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
	
	/*
	 * Request data from MLB API base of url 
	 */
	public static StringBuffer requestDataFromMLB(String url) throws IOException {
		StringBuffer response = new StringBuffer();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) !=null) {
				response.append(inputLine);
			}
		}
		else {
			System.out.println("Error getting data!");
			response = null;
		}
		return response;
	}
	
	
	public static String addDel (String text) {
		text = "'" + text + "'";
		return text;
	}
	
	public static String in_exParam(String path) {
		int first = path.indexOf('.');
		int second = path.indexOf('.', first);
		return path.substring(first, second);
	}
	
}
