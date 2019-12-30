package icu.ayumi.tempbans.listeners;

import icu.ayumi.tempbans.Ban;
import icu.ayumi.tempbans.BanImpl;
import icu.ayumi.tempbans.TempBanPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerJoin implements Listener {
    private TempBanPlugin plugin;

    public PlayerJoin(TempBanPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event) throws IOException {
        if(plugin.getBanManager().getBan(event.getPlayer().getUniqueId()) != null){
            Ban b = plugin.getBanManager().getBan(event.getPlayer().getUniqueId());
            Date d = new Date(b.getBannedDate() + b.getBanDuration());
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned bro \n Reason: " + b.getReason() + "\nUntil: " + d + "\nBy:" + b.getAdmin());
        }
    }
}
