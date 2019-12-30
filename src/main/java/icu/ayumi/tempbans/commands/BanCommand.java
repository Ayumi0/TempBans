package icu.ayumi.tempbans.commands;

import icu.ayumi.tempbans.Ban;
import icu.ayumi.tempbans.BanImpl;
import icu.ayumi.tempbans.TempBanPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BanCommand implements CommandExecutor {
    private TempBanPlugin plugin;

    public BanCommand(TempBanPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p;
        if(sender instanceof ConsoleCommandSender){
            p =  plugin.getServer().getPlayer(args[0]);
        } else {
            p = (Player) sender;
        }
        if(!sender.hasPermission("tempbans.ban")){
            sender.sendMessage("You don't have Permission!");
            return true;
        }
        // /ban [user] [length in hours] [reason]
        if(args.length >=2){
            String reason = "";
            for(int i = 1; i <= args.length - 1; i++){
                StringBuilder sb = new StringBuilder();
                sb.append(args[i]);
                reason = sb.toString();
            }
            if(!args[1].matches("-?(0|[1-9]\\d*)")){
                sender.sendMessage("Time must be an integer!");
                return true;
            }
            if(args[2] == null){
                reason = "No reason";
            }
            UUID u = plugin.getServer().getOfflinePlayer(args[0]).getUniqueId();
            Ban b = new BanImpl(System.currentTimeMillis(),
                    TimeUnit.HOURS.toMillis(Integer.valueOf(args[1])),
                    u,
                    p.getUniqueId() == null ? UUID.randomUUID() : p.getUniqueId(),
                    reason);
            plugin.getServer().getPlayer(args[0]).kickPlayer("You have been banned!");
            try {
                plugin.createBan(u, b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
