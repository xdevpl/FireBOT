package pl.luxdev.mbot.instances;

import java.io.File;
import java.util.Timer;
import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

import pl.luxdev.mbot.managers.Config;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.mysql.MysqlManager;
import pl.luxdev.mbot.threads.instances.SecondInstanceThread;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.GsonUtil;
import pl.luxdev.mbot.utils.StringUtils;

public class SecondInstance {
	
	private TS3Query query;
	private TS3Api api;
	private TS3ApiAsync asyncApi;
	private final TS3Config config;
	private static SecondInstance inst;
	
	public SecondInstance(){
		this.config = new TS3Config();
	}
	public void start(){
		Config botConfig = ConfigManager.getConfig();
		config.setHost(botConfig.getSecondInstanceInfo().getQueryIp());
		config.setDebugLevel(Level.OFF);
		config.setQueryPort(botConfig.getSecondInstanceInfo().getPort());
		config.setReconnectStrategy(ReconnectStrategy.constantBackoff());
		this.query = new TS3Query(this.config);
		query.connect();
		this.api = this.query.getApi();
		this.asyncApi = this.query.getAsyncApi();
		api.selectVirtualServerById(botConfig.getSecondInstanceInfo().getVirtualServerId());
		api.login(botConfig.getSecondInstanceInfo().getQueryLogin(), botConfig.getSecondInstanceInfo().getPassword());
		api.setNickname(botConfig.getSecondInstanceInfo().getBotName());
		new BotUtils(this);
		new SecondInstanceThread().start();
		System.out.println("bot #2 (PP) should join on the server, everything started, version: " + BotUtils.getVersion());
	}
	public TS3Api getApi(){
        return api;
    }
	public TS3ApiAsync getAsyncApi(){
		return this.asyncApi;
	}
	public static SecondInstance getInst(){
		if(inst == null) inst = new SecondInstance();
		return inst;
	}

}
