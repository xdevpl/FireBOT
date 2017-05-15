package pl.luxdev.mbot.modules;

import com.github.theholywaffle.teamspeak3.api.CommandFuture;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import pl.luxdev.mbot.basic.TeamSpeakUser;
import pl.luxdev.mbot.basic.util.TeamSpeakUserUtil;
import pl.luxdev.mbot.utils.BotUtils;

import java.util.Arrays;
import java.util.List;

import static pl.luxdev.mbot.utils.BotUtils.bot;

/**
 * Created by Luxq1 on 13.05.2017.
 * All rights reserved.
 */
public class TeamSpeakUserUpdaterModule {

    private static TeamSpeakUserUpdaterModule inst;

    public void checkForServerGroups() {
        for(TeamSpeakUser ts : TeamSpeakUserUtil.getTeamSpeakUsers()){
            Client c = ts.getAsClient();
            if(c.isInServerGroup(51) || c.isInServerGroup(52) || c.isInServerGroup(54) || c.isInServerGroup(112)){
                return;
            }
            if(c.getServerGroups().length > 5){
                bot.getApi().removeClientFromServerGroup(Arrays.stream(c.getServerGroups()).findAny().getAsInt(), c.getDatabaseId());
                bot.getApi().sendPrivateMessage(c.getId(), "Posiadales za duzo rang, jedna zostala zdejeta aby zachowac normy na naszym teamspeaku.");
            }
        }
    }
    public static TeamSpeakUserUpdaterModule getInst(){
        if(inst == null) inst = new TeamSpeakUserUpdaterModule();
        return inst;
    }

}
