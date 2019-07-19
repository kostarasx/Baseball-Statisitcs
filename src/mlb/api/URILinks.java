package mlb.api;

public class URILinks {
	protected String host = "http://lookup-service-prod.mlb.com";
	protected String inParam = "col_in=";
	protected String delim = "&";

	protected String addDel(String text) {
		text = "'" + text + "'";
		return text;
	}

	protected String in_exParam(String path) {
		int first = path.indexOf('.');
		first++;
		int second = path.indexOf('.', first);
		return path.substring(first, second) + ".";
	}
}
