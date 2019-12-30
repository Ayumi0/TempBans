package icu.ayumi.tempbans;

import java.util.UUID;

public interface Ban {
    UUID getUserUUID();
    long getBannedDate();
    long getBanDuration();
    String getReason();
    UUID getAdmin();
}
