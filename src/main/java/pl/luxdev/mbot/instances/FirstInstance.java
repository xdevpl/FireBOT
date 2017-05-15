package pl.luxdev.mbot.instances;

import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

import pl.luxdev.mbot.events.EventCaller;
import pl.luxdev.mbot.managers.Config;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.mysql.MysqlManager;
import pl.luxdev.mbot.threads.IDTDThread;
import pl.luxdev.mbot.threads.instances.MainInstanceThread;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

public class FirstInstance {
	
	private TS3Query query;
	private TS3Api api;
	private TS3ApiAsync asyncApi;
	private final TS3Config config;
	private static FirstInstance inst;
	
	public FirstInstance(){
		this.config = new TS3Config();
	}
	public void start(){
		Config botConfig = ConfigManager.getConfig();
		config.setHost(botConfig.getFirstInstanceInfo().getQueryIp());
		config.setDebugLevel(Level.OFF);
		config.setQueryPort(botConfig.getFirstInstanceInfo().getPort());
		config.setReconnectStrategy(ReconnectStrategy.constantBackoff());
		this.query = new TS3Query(this.config);
		this.api = this.query.getApi();
		this.asyncApi = this.query.getAsyncApi();
		query.connect();
		api.selectVirtualServerById(botConfig.getFirstInstanceInfo().getVirtualServerId());
		api.login(botConfig.getFirstInstanceInfo().getQueryLogin(), botConfig.getFirstInstanceInfo().getPassword());
		api.setNickname(botConfig.getFirstInstanceInfo().getBotName());
		this.api.registerAllEvents();
		this.api.addTS3Listeners(new EventCaller(this));
		System.out.println("--- Registred events ---");
		new StringUtils(this);
		new MainInstanceThread(this).start();
		System.out.println("bot #1 (QUERY) should join on the server, everything started, version: " + BotUtils.getVersion());
	}
	public TS3Api getApi(){
        return api;
    }
    public TS3ApiAsync getAsyncApi(){
		return this.asyncApi;
	}
	public static FirstInstance getInst(){
		if(inst == null) inst = new FirstInstance();
		return inst;
	}
}
