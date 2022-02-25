package com.bitnet.paulo.captcha.listeners;

import com.bitnet.paulo.captcha.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MenuListener implements Listener {

    private final Main plugin;

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cCaptcha"))) {
            Player p = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            assert item != null;
            if (item.getType().equals(Material.AIR) || item == null || !item.hasItemMeta()) return;
            e.setCancelled(true);
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&e&lCLICK TO VERIFY"))) {
                if (plugin.getManager().containsPlayer(p)) {
                    if (!plugin.getManager().getVerifiedStatus(p)) {
                        plugin.getManager().updateVerifiedStatus(p, true);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCorrectly verified"));
                        if (plugin.getMenu().getViewMap().containsKey(p)) {
                            if (plugin.getMenu().getViewMap().get(p) != null) {
                                plugin.getMenu().getViewMap().put(p, null);
                            }
                        }
                    }
                }
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cCaptcha"))) {
            Player p = (Player) e.getPlayer();
            if (plugin.getManager().containsPlayer(p)) {
                while (!plugin.getManager().getVerifiedStatus(p)) {
                    if (plugin.getMenu().getViewMap().containsKey(p)) {
                        if (plugin.getMenu().getViewMap().get(p) != null) {
                            InventoryView view = plugin.getMenu().getViewMap().get(p);
                            p.openInventory(view);
                        }
                    }
                }
            }
        }
    }
}
