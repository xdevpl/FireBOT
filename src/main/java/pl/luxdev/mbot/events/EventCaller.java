package pl.luxdev.mbot.events;

import java.util.HashSet;
import java.util.Set;

import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.listeners.ClientJoinChannelList;
import pl.luxdev.mbot.listeners.ClientJoinList;
import pl.luxdev.mbot.listeners.ClientLeaveList;
import pl.luxdev.mbot.listeners.TextMessageList;

public class EventCaller implements TS3Listener {
	
	private final FirstInstance bot;
	private final Set<EventHandler> eventHandlers;

	public EventCaller(FirstInstance bot) {
		this.bot = bot;
		this.eventHandlers = new HashSet<>();
		this.initModules();
	}

	private void initModules() {
		this.eventHandlers.add(new ClientJoinList());
		this.eventHandlers.add(new ClientJoinChannelList());
		this.eventHandlers.add(new ClientLeaveList());
		this.eventHandlers.add(new TextMessageList());

	}

	private void handle(Object event, EventType type) {
		this.eventHandlers.stream().filter(e -> e.getEventType() == type).forEach(e -> e.handle(event, this.bot));
	}

	@Override
	public void onTextMessage(TextMessageEvent e) {
		this.handle(e, EventType.TEXT_MESSAGE_EVENT);
	}

	@Override
	public void onClientJoin(ClientJoinEvent e) {
		this.handle(e, EventType.CLIENT_JOIN_EVENT);
	}

	@Override
	public void onClientLeave(ClientLeaveEvent e) {
		this.handle(e, EventType.CLIENT_LEAVE_EVENT);
	}

	@Override
	public void onServerEdit(ServerEditedEvent e) {
		this.handle(e, EventType.SERVER_EDITED_EVENT);
	}

	@Override
	public void onChannelEdit(ChannelEditedEvent e) {
		this.handle(e, EventType.CHANNEL_EDITED_EVENT);
	}

	@Override
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
		this.handle(e, EventType.CHANNEL_DESCRIPTION_EDITED_EVENT);
	}

	@Override
	public void onClientMoved(ClientMovedEvent e) {
		this.handle(e, EventType.CLIENT_MOVED_EVENT);
	}

	@Override
	public void onChannelCreate(ChannelCreateEvent e) {
		this.handle(e, EventType.CHANNEL_CREATE_EVENT);
	}

	@Override
	public void onChannelDeleted(ChannelDeletedEvent e) {
		this.handle(e, EventType.CHANNEL_DELETED_EVENT);
	}

	@Override
	public void onChannelMoved(ChannelMovedEvent e) {
		this.handle(e, EventType.CHANNEL_MOVED_EVENT);
	}

	@Override
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
		this.handle(e, EventType.CHANNEL_PASSWORD_CHANGED_EVENT);
	}

	@Override
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
		this.handle(e, EventType.PRIVILEGE_KEY_USED_EVENT);
	}

}
