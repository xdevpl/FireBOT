package pl.luxdev.mbot.basic;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import pl.luxdev.mbot.utils.BotUtils;

public class TeamSpeakUser{
	
	private boolean isAfk;
	
	private int oldChannel;
	private int clientId;
	
	private String nickname;
	private String uniqueIdentifier;
	
	public TeamSpeakUser(Client c){
		this.uniqueIdentifier = c.getUniqueIdentifier();
		this.clientId = c.getId();
		this.nickname = c.getNickname();
	}
	public String getUniqueIdentifier(){
		return this.uniqueIdentifier;
	}
	public int getId(){
		return this.clientId;
	}
	public Client getAsClient(){
		Client c = BotUtils.getClient(this.uniqueIdentifier);
		if(c != null)return c;
		return null;
	}
	public String getNick(){
		return this.nickname;
	}
	public void setOldChannel(int channelId){
		this.oldChannel = channelId;
	}
	public void setAfk(boolean isAfk){
		this.isAfk = isAfk;
	}
	public boolean isAfk(){
		return this.isAfk;
	}
	public boolean isGoneAfk(){
		Client c = BotUtils.getClient(this.uniqueIdentifier);
		if(c == null){
			return false;
		}
		if(c.isAway() || c.isOutputMuted()){
			return true;
		}
		return false;
	}
	public int getOldChannel(){
		return this.oldChannel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.uniqueIdentifier == null) ? 0 : this.uniqueIdentifier.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		TeamSpeakUser u = (TeamSpeakUser) o;
		if(!u.getUniqueIdentifier().equals(this.uniqueIdentifier)) return false;
		return true;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	@Override
	public String toString(){
		return this.uniqueIdentifier.toString();
	}
}
