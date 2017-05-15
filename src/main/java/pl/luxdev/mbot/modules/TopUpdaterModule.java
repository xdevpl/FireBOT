package pl.luxdev.mbot.modules;

import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.utils.BotUtils;
import pl.luxdev.mbot.utils.StringUtils;
import pl.luxdev.mbot.utils.ValueComparator;

import java.util.*;

import static pl.luxdev.mbot.utils.BotUtils.bot;

/**
 * Created by Luxq1 on 13.05.2017.
 * All rights reserved.
 */
public class TopUpdaterModule {

    private static TopUpdaterModule inst;

    public void updateTopSpentTime(){
        HashMap<String, Long> map = new HashMap<>();
        List<String> whatToWrite = new ArrayList<>();
        for(DatabasedClient dbClient : DatabaseClientUtil.getDatabasedClientAsList()){
            map.put(dbClient.getNick(), dbClient.getTimeSpent());
        }
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String,Long> sorted_map = new TreeMap<String,Long>(bvc);
        sorted_map.putAll(map);
        int i = 0;
        whatToWrite.add(" [center][size=12][b]Top 10 Spedzonego czasu na naszym Serwerze[/b]");
        whatToWrite.add("[size=10]");
        for(Map.Entry<String, Long> entry : sorted_map.entrySet()){
            if(i == 10) continue;
            i++;
            whatToWrite.add("[b]" + i + ". [/b] " +entry.getKey() + " [b]spedzony czas: [/b]" + StringUtils.parseTime(entry.getValue()));
            whatToWrite.add(" ");
        }
        whatToWrite.add("[hr]");
        whatToWrite.add("[size=8]Wygenerowane przez FireBOT" + BotUtils.getVersion());
        BotUtils.editChannelDescription(whatToWrite, 252);
    }
    public void updateTopConnections() {
        HashMap<String, Integer> map = new HashMap<>();
        List<String> whatToWrite = new ArrayList<>();
        for (DatabaseClient c : bot.getApi().getDatabaseClients()) {
            map.put(c.getNickname(), c.getTotalConnections());
        }
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        sorted_map.putAll(map);
        int i = 0;
        whatToWrite.add(" [center][size=12][b]Top 10 Połączeń na nasz Serwer[/b]");
        whatToWrite.add("[size=10]");
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            if (i == 10)continue;
            i++;
            whatToWrite.add("[b]" + i + ". [/b] " + entry.getKey() + " [b]ilość Połączeń z Serwerem: [/b]" + entry.getValue());
            whatToWrite.add(" ");
        }
        whatToWrite.add("[hr]");
        whatToWrite.add("[size=8]Wygenerowane przez FireBOT" + BotUtils.getVersion());
        BotUtils.editChannelDescription(whatToWrite, 243);
    }
    public static TopUpdaterModule getInst(){
        if(inst == null) inst = new TopUpdaterModule();
        return inst;
    }
}
