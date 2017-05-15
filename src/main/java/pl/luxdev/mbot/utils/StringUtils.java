package pl.luxdev.mbot.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.theholywaffle.teamspeak3.api.CommandFuture.SuccessListener;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;

import pl.luxdev.mbot.instances.FirstInstance;

public class StringUtils {
	
	private static FirstInstance bot;
	private static StringBuilder sb = new StringBuilder();
	private static String TIMEPARSE_DAY = " dzien ";
	private static String TIMEPARSE_DAYS = " dni ";
	private static String TIMEPARSE_HOUR = " godzina ";
	private static String TIMEPARSE_HOURS = " godzin ";
	private static String TIMEPARSE_HOURS_2 = " godzin ";
	private static String TIMEPARSE_MINUTE = " minuta ";
	private static String TIMEPARSE_MINUTES = " minut ";
	private static String TIMEPARSE_MINUTES_2 = " minut ";
	private static String TIMEPARSE_SECOND = " sekunda ";
	private static String TIMEPARSE_SECONDS = " sekund ";
	private static String TIMEPARSE_SECONDS_2 = " sekund ";
	
	public StringUtils(FirstInstance inst){
		StringUtils.bot = inst;
	}

	public static StringBuilder parseMessage(List<String> strings) {
		StringBuilder builder = new StringBuilder();
		for (String string : strings) {
			builder.append("\n");
			builder.append(string);
		}
		return builder;
	}

	public static String basicVariables(String content) {
		try {
			content = content.replaceAll("%online", String.valueOf(bot.getApi().getClients().size()));
			content = content.replaceAll("%bans", String.valueOf(bot.getApi().getBans().size()));
			content = content.replaceAll("%channels", String.valueOf(bot.getApi().getChannels().size()));
			content = content.replaceAll("%uptime", StringUtils.parseTime(bot.getApi().getHostInfo().getUptime()));
            content = content.replaceAll("%version", BotUtils.getVersion());
			return content;
		} catch (Exception ex) {
			return content;
		}
	}

	public static String userVariables(ClientInfo client, String content) {
		try {
			content = content.replaceAll("%totalConnections", String.valueOf(client.getTotalConnections()));
			content = content.replaceAll("%firstConnection", client.getCreatedDate().toString());
			content = content.replaceAll("%lastConnection", client.getLastConnectedDate().toString());
			content = content.replaceAll("%clientIp", String.valueOf(client.getIp()));
			content = content.replaceAll("%name", client.getNickname());
			content = content.replaceAll("%version", BotUtils.getVersion());
			content = content.replaceAll("%userid", client.getUniqueIdentifier());
			content = content.replaceAll("%clientCountry", client.getCountry());
			return content;
		} catch (Exception ex) {
			return content;
		}
	}
	public static String parseTime(long millis) {
		if (millis == 0)
			return "0";
		sb.setLength(0);

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		if (days > 0)
			millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		if (hours > 0)
			millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		if (minutes > 0)
			millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		if (seconds > 0)
			millis -= TimeUnit.SECONDS.toMillis(seconds);

		if (days > 0) {
			sb.append(days);
			long i = days % 10;
			if (i == 1)
				sb.append(TIMEPARSE_DAY);
			else
				sb.append(TIMEPARSE_DAYS);
		}
		if (hours > 0) {
			sb.append(hours);
			long i = hours % 10;
			if (i == 1)
				sb.append(TIMEPARSE_HOUR);
			else if (i < 5)
				sb.append(TIMEPARSE_HOURS);
			else
				sb.append(TIMEPARSE_HOURS_2);
		}
		if (minutes > 0) {
			sb.append(minutes);
			long i = minutes % 10;
			if (i == 1)
				sb.append(TIMEPARSE_MINUTE);
			else if (i < 5)
				sb.append(TIMEPARSE_MINUTES);
			else
				sb.append(TIMEPARSE_MINUTES_2);
		}
		if (seconds > 0) {
			sb.append(seconds);
			long i = seconds % 10;
			if (i == 1)
				sb.append(TIMEPARSE_SECOND);
			else if (i < 5)
				sb.append(TIMEPARSE_SECONDS);
			else
				sb.append(TIMEPARSE_SECONDS_2);
		}
		return (sb.toString());
	}
}
