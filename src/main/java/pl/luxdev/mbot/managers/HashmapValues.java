package pl.luxdev.mbot.managers;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class HashmapValues {

	@SerializedName("premium-Channels")
	private HashMap<Integer, Integer> premiumChannels = new HashMap<>();
	@SerializedName("admins")
	private HashMap<String, Integer> admins = new HashMap<>();

	public HashmapValues(){
		this.premiumChannels.put(-1, -1);
		this.premiumChannels.put(-2, -2);
		this.admins.put("admin_uid", -1);
		this.admins.put("admin_uid_2", -1);
	}
	public HashMap<Integer, Integer> getPremium(){
		return this.premiumChannels;
	}
	public HashMap<String, Integer> getAdmins(){
		return this.admins;
	}
}
