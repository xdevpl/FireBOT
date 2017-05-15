package pl.luxdev.mbot.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.VirtualServerProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;

import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.instances.SecondInstance;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.HashmapValues;

public class BotUtils {

	public static SecondInstance bot;
	
	public BotUtils(SecondInstance secondInstance){
		this.bot = secondInstance;
	}
	
	public static Client getClient(int clientId){
		try {
			Client c = bot.getAsyncApi().getClientInfo(clientId).get();
			return c;
		} catch (Exception e) {
			return null;
		}
	}
	public static Client getClient(String uid){
		try {
			Client c = bot.getAsyncApi().getClientByUId(uid).get();
			return c;
		} catch (Exception e) {
            return null;
		}
	}
	public static void sendChannelMessage(String s){
		bot.getApi().sendChannelMessage(s);
	}
	public static void editChannelName(String s, int channelId){
		bot.getApi().editChannel(channelId, Collections.singletonMap(ChannelProperty.CHANNEL_NAME, s));
	}
	public static void editVirtualServerName(String s){
		bot.getApi().editServer(Collections.singletonMap(VirtualServerProperty.VIRTUALSERVER_NAME, s));
	}
	public static void createChannel(int where, String name, int subchannels){
		//TODO Create whole method.
	}
	public static void pokeClient(int clientId, String s){
		bot.getApi().pokeClient(clientId, s);
	}
	public static void editVirtualServerDescription(List<String> desc){
		bot.getApi().editServer(Collections.singletonMap(VirtualServerProperty.VIRTUALSERVER_HOSTMESSAGE, StringUtils.parseMessage(desc).toString()));
	}
	public static void editChannelDescription(List<String> desc, int channelId){
		bot.getApi().editChannel(channelId, Collections.singletonMap(ChannelProperty.CHANNEL_DESCRIPTION, StringUtils.parseMessage(desc).toString()));
	}
	public static String getVersion(){
		return "-0.3.0_SNAPSHOT developed by xdev";
	}
	public static String calculateTime(long seconds) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		String ss = day + " dni " + hours + " godz " + minute + " min ";
		return ss;
	}
	public static SecondInstance getApi(){
		return bot;
	}
}
