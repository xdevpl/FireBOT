package pl.luxdev.mbot.listeners;

import java.util.HashMap;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;

import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.TeamSpeakUser;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.basic.util.TeamSpeakUserUtil;
import pl.luxdev.mbot.events.EventHandler;
import pl.luxdev.mbot.events.EventType;
import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.managers.Config;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

public class ClientJoinList implements EventHandler<ClientJoinEvent> {
	
	public void handle(ClientJoinEvent event, FirstInstance bot) {
		try{
			if(check(event, bot))return;
			int clientId = event.getClientId();
			int dbClientId = event.getClientDatabaseId();
			Config config = ConfigManager.getConfig();
			if (!config.getSettings().canJoin(event.getClientNickname())) {
				bot.getApi().kickClientFromServer(config.getSettings().getBannedNicknameMessage(), clientId);
			}
			TeamSpeakUser user = new TeamSpeakUser(BotUtils.getClient(clientId));
			TeamSpeakUserUtil.registerUser(user);
			DatabaseClientUtil.addToCDBID(event.getClientId(), event.getClientDatabaseId());
			if(!DatabaseClientUtil.isInDatabase(dbClientId)){
				DatabasedClient dbClient = new DatabasedClient(dbClientId);
                dbClient.setLastConnect(System.currentTimeMillis());
                dbClient.setClientDBId(dbClientId);
                dbClient.setDisconnected(false);
                dbClient.setNick(event.getClientNickname());
                dbClient.setUid(event.getUniqueClientIdentifier());
                dbClient.changed();
				DatabaseClientUtil.addToDatabase(dbClient, dbClientId);
			}else{
                DatabasedClient dbClient = DatabaseClientUtil.getDBClient(dbClientId);
                dbClient.setLastConnect(System.currentTimeMillis());
                dbClient.setNick(event.getClientNickname());
                dbClient.setDisconnected(false);
				dbClient.changed();
            }
			ClientInfo client = (ClientInfo) BotUtils.getClient(event.getUniqueClientIdentifier());
			String message = StringUtils.parseMessage(config.getSettings().getWelcomeMessage()).toString();
			message = StringUtils.basicVariables(message);
			message = StringUtils.userVariables(client, message);
			bot.getApi().sendPrivateMessage(event.getClientId(), message);
			if (client.getTotalConnections() == 1) {
				HashMap<ChannelProperty, String> properties = new HashMap<>();
				properties.put(ChannelProperty.CHANNEL_DESCRIPTION, StringUtils.userVariables(client,config.getSettings().getNewUserChannelMessage()));
				properties.put(ChannelProperty.CHANNEL_NAME, StringUtils.userVariables(client, "[lspacer][»] "+config.getSettings().getNewUserChannelName()));
				bot.getApi().editChannel(config.getSettings().getNewUserChannelId(), properties);
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
	public EventType getEventType() {
		return EventType.CLIENT_JOIN_EVENT;
	}
	public boolean check(ClientJoinEvent e, FirstInstance bot) {
		if (e.getUniqueClientIdentifier() == "serveradmin") {
			return true;
		}
		if (e.getUniqueClientIdentifier() == "ServerQuery") {
			return true;
		}
		if (e.getUniqueClientIdentifier() == null) {
			return true;
		}
		if (e.getClientType() == 1) {
			return true;
		}
		if(bot.getApi().getClientInfo(e.getClientId()).isServerQueryClient()){
			return true;
		}
		return false;
	}
}