package com.bitnet.paulo.captcha.listeners;

import com.bitnet.paulo.captcha.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final Main plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getManager().createPlayer(e.getPlayer());
        Bukkit.getScheduler().runTaskLater(plugin, ()->{
            plugin.getMenu().openMenu(e.getPlayer());
        }, 5L);
    }
}
