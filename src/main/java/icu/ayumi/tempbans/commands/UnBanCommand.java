package icu.ayumi.tempbans.commands;

import icu.ayumi.tempbans.TempBanPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnBanCommand implements CommandExecutor {
    private TempBanPlugin plugin;

    public UnBanCommand(TempBanPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("tempban.unban")){
            sender.sendMessage("You don't have permission!");
            return true;
        }
        if(args.length != 1){
            sender.sendMessage("Wrong usage!");
            return true;
        }
        OfflinePlayer p = plugin.getServer().getOfflinePlayer(args[0]);
        if(!p.hasPlayedBefore()){
            sender.sendMessage("Unknown player!");
            return true;
        }
        if(plugin.getBanManager().getBan(p.getUniqueId()) == null){
            sender.sendMessage("This player isn't banned");
            return true;
        } else {
            plugin.removeBan(p.getUniqueId());
            sender.sendMessage("Unbanned Player!");
        }

        return false;
    }
}
