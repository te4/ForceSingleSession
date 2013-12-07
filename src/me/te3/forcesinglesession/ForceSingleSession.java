package me.te3.forcesinglesession;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;



/**
 *
 * @author te3
 */
public class ForceSingleSession extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != Result.ALLOWED || event.getPlayer() == null) {
            return;
        }

        Player player = event.getPlayer();
        String name = player.getName().toLowerCase();

        int min = 3;
        int max = 20;
        String regex = "[a-zA-Z0-9_?]+";

        if (name.length() > max || name.length() < min) {
            event.disallow(Result.KICK_OTHER, "Your nickname has the wrong length. MaxLen: " + max + ", MinLen: " + min);
            return;
        }
        if (!player.getName().matches(regex) || name.equals("Player")) {
            event.disallow(Result.KICK_OTHER, "Your nickname contains illegal characters. Allowed chars: " + regex);
            return;
        }

        if(player.isOnline()) {
               player.kickPlayer("Same nick is already playing");
               event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Same nick is already playing");  
               return;
        } 
    }

}
