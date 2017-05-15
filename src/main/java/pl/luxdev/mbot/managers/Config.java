package pl.luxdev.mbot.managers;

import com.google.gson.annotations.SerializedName;

public class Config {

	@SerializedName("first-instance-info")
	private FirstInstanceSettings firstInstance = new FirstInstanceSettings();
	@SerializedName("second-instance-info")
	private SecondInstanceSettings secondInstance = new SecondInstanceSettings();
	@SerializedName("module-settings")
	private ModuleSettings settings = new ModuleSettings();
	@SerializedName("hashMap-data")
	private HashmapValues hashmapValues = new HashmapValues();
	
	public Config(){
		
	}
	public FirstInstanceSettings getFirstInstanceInfo(){
		return this.firstInstance;
	}
	public SecondInstanceSettings getSecondInstanceInfo(){
		return this.secondInstance;
	}
	public ModuleSettings getSettings(){
		return this.settings;
	}
	public HashmapValues getHashMapData(){
		return this.hashmapValues;
	}

}
