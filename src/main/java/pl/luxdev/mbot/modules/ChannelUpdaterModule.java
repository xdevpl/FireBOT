package pl.luxdev.mbot.modules;

import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;
import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.HashmapValues;
import pl.luxdev.mbot.managers.ModuleSettings;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

import java.util.Map;

import static pl.luxdev.mbot.utils.BotUtils.bot;

/**
 * Created by Luxq1 on 13.05.2017.
 * All rights reserved.
 */
public class ChannelUpdaterModule {

    private static ChannelUpdaterModule inst;

    private final HashmapValues hashmapConfig = ConfigManager.getConfig().getHashMapData();
    private final ModuleSettings settings = ConfigManager.getConfig().getSettings();

    public void updatePremiumChannels(){
        for(Map.Entry<Integer, Integer> entry : hashmapConfig.getPremium().entrySet()){
            int online = 0;
            for(ServerGroupClient s : bot.getApi().getServerGroupClients(entry.getKey())){
                System.out.println("Idzie dalej po: sGC: " + s.getNickname());
                DatabasedClient db = DatabaseClientUtil.getDBClient(s.getUniqueIdentifier());
                if(!db.isDisconnected()){
                    online++;
                }
            }
            BotUtils.editChannelName("[cspacer]Dostępnych: " + online + "/" + bot.getApi().getServerGroupClients(entry.getKey()).size(), entry.getValue());
        }
    }
    public void updateEveryInfoChannel(){
        BotUtils.editChannelName("[lspacer][»] " +StringUtils.basicVariables(settings.getChannelInfoOnline()), 28);
        BotUtils.editChannelName("[lspacer][»] Uptime: " + BotUtils.calculateTime(bot.getApi().getHostInfo().getUptime()), 225);
        BotUtils.editChannelName("[lspacer][»] Ilość kanałów: " + bot.getApi().getChannels().size(), 227);
        BotUtils.editChannelName("[lspacer][»] Packet loss: " + (int) bot.getApi().getServerInfo().getTotalPacketloss() + "%", 231);
        BotUtils.editChannelName("[lspacer][»] Ping: " + (int)bot.getApi().getServerInfo().getPing(), 232);
    }
    public void updateVirutalServer(){
        BotUtils.editVirtualServerName(StringUtils.basicVariables(settings.getVirutalServerName()));
        BotUtils.editVirtualServerDescription(settings.getWelcomeMessagePoke());
    }
    public static ChannelUpdaterModule getInst(){
        if(inst == null) inst = new ChannelUpdaterModule();
        return inst;
    }

}
