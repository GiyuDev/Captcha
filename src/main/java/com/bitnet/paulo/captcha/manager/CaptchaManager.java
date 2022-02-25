package com.bitnet.paulo.captcha.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;

@NoArgsConstructor
public class CaptchaManager {

    @Getter
    private final HashMap<Player, Boolean> verifiedCacheMap = new HashMap<>();

    public boolean containsPlayer(Player p) {
        return this.getVerifiedCacheMap().containsKey(p);
    }

    public void createPlayer(Player p) {
        if (!this.containsPlayer(p)) this.getVerifiedCacheMap().put(p, false);
    }

    public boolean getVerifiedStatus(Player p) {
        return this.containsPlayer(p) ? this.getVerifiedCacheMap().get(p) : false;
    }

    public void updateVerifiedStatus(Player p, Boolean bool) {
        if (this.containsPlayer(p)) this.getVerifiedCacheMap().put(p, bool);
        else {
            this.getVerifiedCacheMap().put(p, bool);
        }
    }
}
