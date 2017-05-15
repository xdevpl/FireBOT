package pl.luxdev.mbot.listeners;

import java.util.HashMap;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.CommandFuture;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo;

import pl.luxdev.mbot.basic.TeamSpeakUser;
import pl.luxdev.mbot.basic.util.TeamSpeakUserUtil;
import pl.luxdev.mbot.events.EventHandler;
import pl.luxdev.mbot.events.EventType;
import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.managers.Config;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.ModuleSettings;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

public class ClientJoinChannelList implements EventHandler<ClientMovedEvent>{

	@Override
	public void handle(ClientMovedEvent event, FirstInstance bot) {
		ModuleSettings settings = ConfigManager.getConfig().getSettings();
		if(event.getTargetChannelId() == settings.getPrivateChannelId()){
			int privateChannelsSpacerId = settings.getPrivateChannelsSpacerId();
			HashMap<ChannelProperty, String> properties = new HashMap<>();
			HashMap<ChannelProperty, String> subchannel = new HashMap<>();
			
			properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
			properties.put(ChannelProperty.CPID, String.valueOf(privateChannelsSpacerId));
			int mainChannel = bot.getApi().createChannel(StringUtils.userVariables(bot.getApi().getClientInfo(event.getClientId()), settings.getPrivateChannelName()), properties);
			subchannel.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
			subchannel.put(ChannelProperty.CPID, String.valueOf(mainChannel));
			bot.getApi().createChannel(settings.getChannelSubChannelName(), subchannel);
			bot.getApi().moveClient(event.getClientId(), mainChannel);
			bot.getApi().setClientChannelGroup(5, mainChannel, bot.getApi().getClientInfo(event.getClientId()).getDatabaseId());
		}
	}

	@Override
	public EventType getEventType() {
		return EventType.CLIENT_MOVED_EVENT;
	}

}
