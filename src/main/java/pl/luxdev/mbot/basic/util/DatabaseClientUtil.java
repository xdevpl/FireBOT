package pl.luxdev.mbot.basic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.utils.BotUtils;

public class DatabaseClientUtil {


	private static ConcurrentHashMap<Integer, DatabasedClient> dbClients = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<Integer, Integer> databaseCidclipboard = new ConcurrentHashMap<>();

	public static void addToDatabase(DatabasedClient client, int databaseClientId) {
		dbClients.put(databaseClientId, client);
	}
	public static DatabasedClient getDBClient(int databaseClientId) {
		return dbClients.get(databaseClientId);
	}

	public static boolean isInDatabase(int databaseClientId) {
		if (dbClients.containsKey(databaseClientId))
			return true;
		return false;
	}
	public static DatabasedClient getDBClient(String uid){
		for(DatabasedClient c : getDatabasedClientAsList()){
			if(c.getUid().equals(uid)){
				return c;
			}
		}
		return null;
	}
	public static Map<Integer, DatabasedClient> getDatabaseClients() {
		return dbClients;
	}
	public static List<DatabasedClient> getDatabasedClientAsList(){
		List<DatabasedClient> list = new ArrayList<>();
		for(Map.Entry<Integer, DatabasedClient> entry : dbClients.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}
	public static void addToCDBID(int clientId, int clientDbID){
		databaseCidclipboard.put(clientId, clientDbID);
	}
	public static void removeFromCBBID(int clientId, int clientDbID){
		databaseCidclipboard.remove(clientId, clientDbID);
	}
	public static int getDBCIDByCID(int clientId){
		if (databaseCidclipboard.get(clientId) == null){
			return 0;
		}
		return databaseCidclipboard.get(clientId);
	}
}
