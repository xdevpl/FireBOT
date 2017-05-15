package pl.luxdev.mbot.managers;

import com.google.gson.annotations.SerializedName;

public class FirstInstanceSettings {
	
	@SerializedName("virutal-server-id")
	private int virtualServerId;
	@SerializedName("query-password")
	private String password;
	@SerializedName("query-address")
	private String ip;
	@SerializedName("query-port")
	private int port;
	@SerializedName("query-login")
	private String queryLogin;
	@SerializedName("instance-name")
	private String botName;
	
	public FirstInstanceSettings(){
		this.virtualServerId = 1;
		this.queryLogin = "serveradmin";
		this.password = "passwd";
		this.port = 10011;
		this.botName = "FireBOT (Query)";
	}
	public int getVirtualServerId() {
		return virtualServerId;
	}
	public String getPassword() {
		return password;
	}
	public int getPort() {
		return port;
	}
	public String getBotName() {
		return botName;
	}
	public String getQueryLogin() {
		return queryLogin;
	}
	public String getQueryIp(){
		return this.ip;
	}
}
