package com.bitnet.paulo.captcha.listeners;

import com.bitnet.paulo.captcha.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        plugin.getManager().createPlayer(e.getPlayer());
        Bukkit.getScheduler().runTaskLater(plugin, ()->{
            plugin.getMenu().openMenu(e.getPlayer());
        }, 5L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (plugin.getManager().containsPlayer(p)) {
            plugin.getManager().getVerifiedCacheMap().remove(p);
        }
        plugin.getMenu().getViewMap().remove(p);
    }
}
