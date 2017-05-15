package pl.luxdev.mbot.modules;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.managers.HashmapValues;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.luxdev.mbot.utils.BotUtils.bot;

/**
 * Created by Luxq1 on 12.05.2017.
 * All rights reserved.
 */
public class AdminUpdaterModule {

    private static AdminUpdaterModule inst;
    private final HashmapValues hashmapConfig = ConfigManager.getConfig().getHashMapData();

    public void updateAdminList(){
        List<String> list = new ArrayList<>();
        list.add(" [center][size=12][b]Status naszej Administracji[/b]");
        list.add("[size=10]");
        for(Map.Entry<String, Integer> entry : ConfigManager.getConfig().getHashMapData().getAdmins().entrySet()){
            try{
                DatabasedClient dbClient = DatabaseClientUtil.getDBClient(entry.getKey());
                if(!dbClient.isDisconnected()){
                    list.add("• Administrator: [b]" + dbClient.getNick() + "[/b]");
                    list.add("  Status: [b][color=green]Online.[/b]");
                    list.add("  Jest na kanale: [b]" + " [/b]NOT SUPPORTED YET. " + "[/b]");
                    list.add("  Aktywny(a) od: [b]" + StringUtils.parseTime(System.currentTimeMillis() - dbClient.getLastConnect()) + "[/b]");
                    list.add(" ");
                }else{
                    list.add("• Administrator: [b]" + bot.getApi().getDatabaseClientByUId(entry.getKey()).getNickname() + "[/b]");
                    list.add("  Status: [b][color=red]Offline.[/b]");
                    list.add("  Jest na kanale: [b]Brak kanalu.[/b]");
                    list.add("  Nieaktywny(a) od: [b]" + StringUtils.parseTime(System.currentTimeMillis() - dbClient.getLastDisconnect()) + "[/b]");
                    list.add(" ");
                }
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }
        list.add("[hr]");
        list.add("[size=8]Wygenerowane przez FireBOT" + BotUtils.getVersion());
        BotUtils.editChannelDescription(list, 235);
    }
    public void updateAdminStatus(){
        for(Map.Entry<String, Integer> entry : hashmapConfig.getAdmins().entrySet()){
            try{
                Client c = BotUtils.getClient(entry.getKey());
                if(c != null)
                    if(!c.isOutputMuted()){
                        BotUtils.editChannelName("Status: Dostępny.", entry.getValue());
                    }
                if(c.isOutputMuted()){
                    BotUtils.editChannelName("Status: Zaraz wracam.", entry.getValue());
                }
            }catch(Exception e){
                BotUtils.editChannelName("Status: Niedostępny.", entry.getValue());
            }
        }
    }
    public static AdminUpdaterModule getInst(){
        if(inst==null) inst = new AdminUpdaterModule();
        return inst;
    }
}
