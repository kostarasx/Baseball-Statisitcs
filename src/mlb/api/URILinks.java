package mlb.api;

public class URILinks {
	protected String host = "http://lookup-service-prod.mlb.com";
	protected String inParam = "col_in=";
	protected String delim = "&";




	public  String addDel (String text) {
		text = "'" + text + "'";
		return text;
	}

	public String in_exParam(String path) {
		int first = path.indexOf('.');
		int second = path.indexOf('.', first);
		return path.substring(first, second);
	}
}
