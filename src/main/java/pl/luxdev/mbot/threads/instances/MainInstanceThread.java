package pl.luxdev.mbot.threads.instances;

import java.util.Iterator;
import java.util.TimerTask;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;

import pl.luxdev.mbot.basic.TeamSpeakUser;
import pl.luxdev.mbot.basic.util.TeamSpeakUserUtil;
import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.ModuleSettings;
import pl.luxdev.mbot.utils.BotUtils;

public class MainInstanceThread extends Thread {
	private final FirstInstance bot;
	private final ModuleSettings settings;

	public MainInstanceThread(FirstInstance bot) {
		super("MainInstanceThread");
		this.bot = bot;
		this.settings = ConfigManager.getConfig().getSettings();
	}

	@Override
	public void run() {
		try {
			while (true) {
				for (TeamSpeakUser itr : TeamSpeakUserUtil.getTeamSpeakUsers()) {
					Client user = itr.getAsClient();
					if (itr.isAfk() && !itr.isGoneAfk()) {
						bot.getApi().moveClient(itr.getId(), itr.getOldChannel());
						itr.setAfk(false);
						bot.getApi().sendPrivateMessage(itr.getId(), "Hej, wyglada na to ze wrociles, przenieslismy cie spowrotem :)");
					}
					if (itr.isGoneAfk() && user.getChannelId() != settings.getAwayChannelId() && !user.isInServerGroup(settings.getAwayIgnoreGroupId()) && !user.isServerQueryClient()) {
						itr.setOldChannel(user.getChannelId());
						itr.setAfk(true);
						bot.getApi().moveClient(itr.getAsClient().getId(), settings.getAwayChannelId());
						bot.getApi().sendPrivateMessage(itr.getAsClient().getId(), settings.getAwayMovedMessage());
					}
					if (user.getChannelId() == settings.getAutoGroupBoyAssignChannelId()) {
						if (user.isInServerGroup(settings.getAutoGroupGirlAssignGroupId()))
							bot.getApi().kickClientFromChannel("Rejestracja dokonana wczesniej", user);
						if (user.isInServerGroup(settings.getAutoGroupBoyAssignGroupId()))
							bot.getApi().kickClientFromChannel("Rejestracja dokonana wczesniej", user);
						bot.getApi().addClientToServerGroup(settings.getAutoGroupBoyAssignGroupId(), user.getDatabaseId());
						bot.getApi().kickClientFromChannel("", user);
					}
					if (user.getChannelId() == settings.getAutoGroupGirlAssignChannelId()) {
						if (user.isInServerGroup(settings.getAutoGroupGirlAssignGroupId()))
							bot.getApi().kickClientFromChannel("Rejestracja dokonana wczesniej", user);
						if (user.isInServerGroup(settings.getAutoGroupBoyAssignGroupId()))
							bot.getApi().kickClientFromChannel("Rejestracja dokonana wczesniej", user);
						bot.getApi().addClientToServerGroup(settings.getAutoGroupGirlAssignGroupId(), user.getDatabaseId());
						bot.getApi().kickClientFromChannel("", user);
					}
					if (user.getChannelId() == settings.getPrivateChannelId()) {
						bot.getApi().sendPrivateMessage(user.getId(), "Operation not supported yet, current version: " + BotUtils.getVersion());
						BotUtils.createChannel(0, "test", 3);//TODO CREATE THE WHOLE METHOD!!!!!!!!!
					}
					if (user.getChannelId() == settings.getAutoPokeChannelId()) {
						if (itr.isGoneAfk()) {
							bot.getApi().kickClientFromChannel(settings.getCantBeMuted(), user.getId());
						} else {
							for (ServerGroupClient sgc : bot.getApi().getServerGroupClients(settings.getAdministratorGroupId())) {
								bot.getApi().pokeClient(bot.getApi().getClientByUId(sgc.getUniqueIdentifier()).getId(), settings.getSomeoneNeedHelp());
							}
						}
					}
				}
				Thread.sleep(80000);
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
}
