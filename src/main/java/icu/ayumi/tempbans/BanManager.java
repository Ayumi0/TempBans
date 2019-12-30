package icu.ayumi.tempbans;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BanManager {
    private HashMap<UUID, Ban> bans;

    public BanManager() {
        bans = new HashMap<>();
    }
    public void addBan(UUID uuid, Ban ban){
        bans.putIfAbsent(uuid, ban);
    }
    public void removeBan(UUID uuid){
        bans.remove(uuid);
    }
    public Ban getBan(UUID uuid){
        return bans.getOrDefault(uuid, null);
    }
}
