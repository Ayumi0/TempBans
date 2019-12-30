package icu.ayumi.tempbans;

import java.util.UUID;

public class BanImpl implements Ban{
    private long banneddate, duration;
    private UUID user, admin;
    private String reason;

    public BanImpl(long banneddate, long duration, UUID user, UUID admin, String reason) {
        this.banneddate = banneddate;
        this.duration = duration;
        this.user = user;
        this.admin = admin;
        this.reason = reason;
    }

    @Override
    public UUID getUserUUID() {
        return user;
    }

    @Override
    public long getBannedDate() {
        return banneddate;
    }

    @Override
    public long getBanDuration() {
        return duration;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public UUID getAdmin() {
        return admin;
    }
}
