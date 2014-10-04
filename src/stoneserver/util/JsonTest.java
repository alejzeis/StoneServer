package stoneserver.util;

import org.json.simple.JSONObject;

public class JsonTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject versionObj = new JSONObject();
		versionObj.put("name", "1.7.10");
		versionObj.put("protocol", 5);
		
		JSONObject players = new JSONObject();
		players.put("max", 2);
		players.put("online", 0);
		
		JSONObject serverName = new JSONObject();
		serverName.put("text", "Harro! Dis is a cool server asdfasdf");
		
		JSONObject root = new JSONObject();
		root.put("description", serverName);
		root.put("players", players);
		root.put("version", versionObj);
		System.out.println(root.toString());
	}

}
