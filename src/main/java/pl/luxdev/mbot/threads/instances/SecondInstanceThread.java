package pl.luxdev.mbot.threads.instances;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import pl.luxdev.mbot.basic.util.TeamSpeakUserUtil;
import pl.luxdev.mbot.instances.SecondInstance;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.ModuleSettings;
import pl.luxdev.mbot.modules.AdminUpdaterModule;
import pl.luxdev.mbot.modules.Module;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

public class SecondInstanceThread extends Thread {

	private final Module modules = new Module();

	public SecondInstanceThread() {
		super("SecondInstanceThread");
	}

	@Override
	public void run() {
		try {
			while (true) {
				modules.getChannelUpdaterModule().updateVirutalServer();
				modules.getChannelUpdaterModule().updateEveryInfoChannel();
				modules.getChannelUpdaterModule().updatePremiumChannels();
				modules.getAdminUpdaterModule().updateAdminStatus();
				modules.getAdminUpdaterModule().updateAdminList();
				modules.getTopUpdaterModule().updateTopConnections();
				modules.getTopUpdaterModule().updateTopSpentTime();
				modules.getTeamspeakUserUpdaterModule().checkForServerGroups();//This method may be laggy :C
				Thread.sleep(120000);
				//60k * 2 = 2m
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
