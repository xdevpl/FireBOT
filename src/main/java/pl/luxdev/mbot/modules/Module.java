package pl.luxdev.mbot.modules;

/**
 * Created by Luxq1 on 13.05.2017.
 * All rights reserved.
 */
public class Module {

    private static final AdminUpdaterModule adminUpdater = AdminUpdaterModule.getInst();
    private static final TopUpdaterModule topUpdater = TopUpdaterModule.getInst();
    private static final ChannelUpdaterModule channelUpdater = ChannelUpdaterModule.getInst();
    private static final TeamSpeakUserUpdaterModule teamspeakUserUpdater = TeamSpeakUserUpdaterModule.getInst();

    public AdminUpdaterModule getAdminUpdaterModule() {
        return adminUpdater;
    }
    public TopUpdaterModule getTopUpdaterModule() {
        return topUpdater;
    }
    public ChannelUpdaterModule getChannelUpdaterModule(){
        return channelUpdater;
    }
    public TeamSpeakUserUpdaterModule getTeamspeakUserUpdaterModule(){
        return teamspeakUserUpdater;
    }
}
