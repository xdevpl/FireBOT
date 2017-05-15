package pl.luxdev.mbot.listeners;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;
import pl.luxdev.mbot.events.EventHandler;
import pl.luxdev.mbot.events.EventType;
import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.utils.BotUtils;

/**
 * Created by Luxq1 on 13.05.2017.
 * All rights reserved.
 */
public class TextMessageList implements EventHandler<TextMessageEvent> {

    @Override
    public void handle(TextMessageEvent event, FirstInstance bot) {
        try{
            if(event.getMessage().startsWith("move")) {
                String[] split = event.getMessage().replace("move ", "").split(":");
                int gId = Integer.parseInt(split[0]);
                int cId = Integer.parseInt(split[1]);
                for (ServerGroupClient serverGroupClient : bot.getApi().getServerGroupClients(gId)) {
                    Client c = bot.getApi().getClientByUId(serverGroupClient.getUniqueIdentifier());
                    if(c!=null)
                    bot.getApi().moveClient(c.getId(), cId);
                }
                BotUtils.sendChannelMessage(event.getInvokerName() + " wykonal polecenie przeniesienia wszystkich uzytkownikow jakiejs grupy na inny kanal. ");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public EventType getEventType(){
        return EventType.TEXT_MESSAGE_EVENT;
    }
}
