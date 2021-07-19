package io.github.jaronz.mwcommandblacklist;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.utils.Config;

import java.util.List;
import java.util.Map;

public class EventListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        Config config = MWCommandBlacklist.instance.getConfig();
        Player player = event.getPlayer();
        List<Map> blockedCommands = config.getMapList("commands");

        for(Map blockedCommand : blockedCommands){
            String command = blockedCommand.get("command").toString().toLowerCase();
            if(!command.startsWith("/")) command = "/" + command;
            if(event.getMessage().toLowerCase().startsWith(command)){
                boolean hasWorlds = blockedCommand.containsKey("worlds");
                if(hasWorlds && !((List<String>) blockedCommand.get("worlds")).contains(player.getLevel().getName()))
                    continue;
                event.setCancelled();
                player.sendMessage(config.getString(hasWorlds ? "blockedMessage" : "globalBlockedMessage"));
            }
        }
    }
}