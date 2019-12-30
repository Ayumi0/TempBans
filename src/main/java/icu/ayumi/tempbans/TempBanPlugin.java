package icu.ayumi.tempbans;

import icu.ayumi.tempbans.commands.BanCommand;
import icu.ayumi.tempbans.commands.UnBanCommand;
import icu.ayumi.tempbans.listeners.PlayerJoin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TempBanPlugin extends JavaPlugin {

    private BanManager banManager;
    @Override
    public void onEnable() {
        banManager = new BanManager();
        System.out.println("Enabled TempBans Plugin!");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        if (getDataFolder().listFiles().length > 0) {
            for (File file : this.getDataFolder().listFiles()) {
                FileConfiguration c = YamlConfiguration.loadConfiguration(file);
                Ban b = new BanImpl(
                        c.getLong("banned_date"),
                        c.getLong("duration"),
                        UUID.fromString(c.getString("uuid")),
                        UUID.fromString(c.getString("admin")),
                        c.getString("reason"));
                if (b.getBanDuration() + b.getBannedDate()< System.currentTimeMillis()) {
                    System.out.println(b.getBanDuration() + " duration");
                    System.out.println(System.currentTimeMillis() + " bdate");
                    System.out.println("test");
                    file.delete();

                } else {
                    banManager.addBan(b.getUserUUID(), b);
                }
            }



        }
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        this.getCommand("tempban").setExecutor(new BanCommand(this));
        this.getCommand("unban").setExecutor(new UnBanCommand(this));
    }
    public void createBan(UUID uuid, Ban ban) throws IOException {
        File f = new File(this.getDataFolder(), uuid.toString());
        if(!f.exists()){
            f.createNewFile();
        }
        FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
        fc.set("uuid", uuid.toString());
        fc.set("admin", ban.getAdmin().toString());
        fc.set("reason", ban.getReason());
        fc.set("banned_date", ban.getBannedDate());
        fc.set("duration", ban.getBanDuration());
        fc.save(f);
        System.out.println(ban.getBannedDate() + " band");
        System.out.println(ban.getBanDuration() + " duration");

        banManager.addBan(uuid, ban);
    }
    public void removeBan(UUID uuid){
        banManager.removeBan(uuid);
        File f = new File(this.getDataFolder(), uuid.toString());
        if(f.exists()){
            f.delete();
        }
    }

    public BanManager getBanManager() {
        return banManager;
    }
}
