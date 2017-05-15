package pl.luxdev.mbot.managers;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ModuleSettings {
	
	//------ Just strings ------
	@SerializedName("banned-Nickname-Message")
	private String bannedNicknameMessage;
	@SerializedName("clients-Online-Channel-Id")
	private String channelInfoOnline;
	@SerializedName("virtual-Server-Name")
	private String virutalservername;
	@SerializedName("new-User-Channel-Message")
	private String newUserChannelMessage;
	@SerializedName("private-Channel-Name")
	private String privateChannelName;
	@SerializedName("private-ChannelSubChannel-Name")
	private String privateSubchannelName;
	@SerializedName("away-Moved-Message")
	private String awayMovedMessage;
	@SerializedName("cant-BeMuted-Message")
	private String cantBeMuted;
	@SerializedName("someone-Need-Help")
	private String someoneNeedHelp;
	@SerializedName("new-User-Channel-Name")
	private String newUserChannelName;

	//------ Just strings ------
	
	//------ INTEGERS ------
	@SerializedName("new-User-Channel-Id")
	private int newUserChannelId;
	@SerializedName("private-Channel-Id")
	private int privateChannelId;
	@SerializedName("auto-Poke-Channel-Id")
	private int autoPokeChannelid;
	@SerializedName("auto-Group-BoyAssign-ChannelId")
	private int autoGroupBoyAssignChannelId;
	@SerializedName("auto-Group-GirlAssign-ChannelId")
	private int autoGroupGirlAssignChannelId;
	@SerializedName("auto-Group-BoyAssign-GroupId")
	private int autoGroupBoyAssignGroupId;
	@SerializedName("auto-Group-GirlAssign-GroupId")
	private int autoGroupGirlAssignGroupId;
	@SerializedName("away-Channel-Id")
	private int awayChannelId;
	@SerializedName("away-Ignore-GroupId")
	private int awayIgnoreGroupId;
	@SerializedName("administrator-GroupId")
	private int administratorGroupId;
	@SerializedName("private-Channels-SpacerId")
	private int privateChannelsSpacerId;
	//------ INTEGERS ------
	
	//------ Lists ------
	@SerializedName("blocked-Usernames-List")
	private List<String> blockedUsernames;
	@SerializedName("welcome-Message")
	private List<String> welcomeMessage;
	@SerializedName("welcome-Message-Poke")
	private List<String> welcomeMessagePoke;
	//------ Lists ------
	
	public ModuleSettings(){
		this.someoneNeedHelp = "Ktos na centrum pomocy potrzebuje administratora!";
		this.cantBeMuted = "Na tym kanale nie mozesz byc zmutowany.";
		this.bannedNicknameMessage = "Uzyty nick jest niedozwolony, zmien go !";
		this.channelInfoOnline = "Aktualnie Uzytkownikow: %online";
		this.virutalservername = "TS.EXAMPLE.PL [Online: %online]";
		this.newUserChannelMessage = "Ostatni nowy uzytkownik: %name. Polaczyl sie z kraju %clientCountry";
		this.privateChannelName = "Kanal %name";
		this.privateSubchannelName = "Subkanal";
		this.awayMovedMessage = "Wykryto ze jestes afk, zostales przeniesiony na kanal dla osob afk.";
		this.newUserChannelName = "Najnowszy uzytkownik: %name";
		//--
		this.awayChannelId = -1;
		this.awayIgnoreGroupId = -1;
		this.administratorGroupId = -1;
		this.autoGroupGirlAssignChannelId = -1;
		this.autoGroupBoyAssignChannelId = -1;
		this.newUserChannelId = -1;
		this.privateChannelId = -1;
		this.autoPokeChannelid = -1;
		this.autoGroupBoyAssignGroupId = -1;
		this.autoGroupBoyAssignGroupId = -1;
		this.privateChannelsSpacerId = -1;
		//--
		this.welcomeMessage = Arrays.asList("Witaj uzytkowniku %name"," ", "Aktualna wersja bota %version", "Twoje UID %useruid");
		this.blockedUsernames = Arrays.asList("kurwa", "nowe ip", "japierdole", "suki");
		this.welcomeMessagePoke = Arrays.asList("Witaj uzytkowniku %name", "Serwer dziala od: %uptime", "Aktualnie uzytkonikow: %online", "Wersja bota: %version");
	}
	
	//------ AND HERE FINALLY ARE THE FUCKING METHODS! ------
	public String getNewUserChannelName() {
		return newUserChannelName;
	}

	public int getPrivateChannelsSpacerId() {
		return privateChannelsSpacerId;
	}

	public int getAdministratorGroupId() {
		return administratorGroupId;
	}
	public int getAutoGroupBoyAssignGroupId() {
		return autoGroupBoyAssignGroupId;
	}
	public int getAutoGroupGirlAssignGroupId() {
		return autoGroupGirlAssignGroupId;
	}
	public String getSomeoneNeedHelp() {
		return someoneNeedHelp;
	}
	public String getCantBeMuted() {
		return cantBeMuted;
	}
	public String getAwayMovedMessage() {
		return awayMovedMessage;
	}
	public int getAwayChannelId() {
		return awayChannelId;
	}
	public int getAwayIgnoreGroupId() {
		return awayIgnoreGroupId;
	}
	public String getNewUserChannelMessage() {
		return newUserChannelMessage;
	}
	public int getNewUserChannelId() {
		return newUserChannelId;
	}
	public int getAutoGroupBoyAssignChannelId() {
		return autoGroupBoyAssignChannelId;
	}
	public int getAutoGroupGirlAssignChannelId() {
		return autoGroupGirlAssignChannelId;
	}
	public String getBannedNicknameMessage() {
		return bannedNicknameMessage;
	}
	public List<String> getWelcomeMessage() {
		return welcomeMessage;
	}
	public String getChannelInfoOnline() {
		return channelInfoOnline;
	}
	public int getAutoPokeChannelId() {
		return autoPokeChannelid;
	}
	public String getVirutalServerName() {
		return virutalservername;
	}
	public boolean canJoin(String s) {
		if (!blockedUsernames.contains(s)) {
			return true;
		}
		return false;
	}
	public List<String> getBlockedUsernames() {
		return blockedUsernames;
	}
	public String getPrivateChannelName() {
		return privateChannelName;
	}
	public String getChannelSubChannelName() {
		return privateSubchannelName;
	}
	public int getPrivateChannelId() {
		return privateChannelId;
	}
	public List<String> getWelcomeMessagePoke(){
		return this.welcomeMessagePoke;
	}

}
