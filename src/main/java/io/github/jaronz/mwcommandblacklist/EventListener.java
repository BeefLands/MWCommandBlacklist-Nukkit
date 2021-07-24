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
        Player player = event.getPlayer();

        if(player.hasPermission("mwcommandblacklist.ignore")) return;

        Config config = MWCommandBlacklist.instance.getConfig();
        List<Map> blockedCommands = config.getMapList("commands");

        for(Map blockedCommand : blockedCommands){
            String command = blockedCommand.get("command").toString().toLowerCase();
            if(!command.startsWith("/")) command = "/" + command;
            if(event.getMessage().toLowerCase().startsWith(command)){
                boolean hasWorlds = blockedCommand.containsKey("worlds");
                if(hasWorlds && !((List<String>) blockedCommand.get("worlds")).contains(player.getLevel().getName()))
                    continue;
                event.setCancelled();
                if((boolean) blockedCommand.getOrDefault("silent", false)) continue;
                player.sendMessage(config.getString(hasWorlds ? "blockedMessage" : "globalBlockedMessage"));
            }
        }
    }
}
