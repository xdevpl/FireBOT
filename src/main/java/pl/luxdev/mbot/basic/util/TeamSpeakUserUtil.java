package pl.luxdev.mbot.basic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pl.luxdev.mbot.basic.TeamSpeakUser;

public class TeamSpeakUserUtil {
	
	private static volatile List<TeamSpeakUser> clients = new ArrayList<TeamSpeakUser>();
	
	public static void registerUser(TeamSpeakUser c){
		clients.add(c);
	}
	public static void unregisterUser(TeamSpeakUser c){
		clients.remove(c);
	}
	public static TeamSpeakUser getClient(String uid){
		for(TeamSpeakUser c : clients){
			if(c.getUniqueIdentifier() == uid){
				return c;
			}
		}
		return null;
	}
	public static List<TeamSpeakUser> getTeamSpeakUsers(){
		return clients;
	}
}
