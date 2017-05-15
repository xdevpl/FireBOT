package pl.luxdev.mbot.listeners;


import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;

import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.events.EventHandler;
import pl.luxdev.mbot.events.EventType;
import pl.luxdev.mbot.instances.FirstInstance;

public class ClientLeaveList implements EventHandler<ClientLeaveEvent>{
	
	@Override
	public void handle(ClientLeaveEvent event, FirstInstance bot) {
		try {
			int cDatabaseId = DatabaseClientUtil.getDBCIDByCID(event.getClientId());
			if(cDatabaseId == 0)return;
			if (DatabaseClientUtil.isInDatabase(cDatabaseId)){
				DatabasedClient databasedClient = DatabaseClientUtil.getDBClient(cDatabaseId);
				databasedClient.setDisconnected(true);
				databasedClient.setLastDisconnect(System.currentTimeMillis());
				databasedClient.setTimeSpent(System.currentTimeMillis() - databasedClient.getLastConnect());
			}
			DatabaseClientUtil.removeFromCBBID(event.getClientId(), cDatabaseId);
		}catch(Exception e) {
			e.printStackTrace(System.out);
		}
	}
	public EventType getEventType(){
		return EventType.CLIENT_LEAVE_EVENT;
	}
}
