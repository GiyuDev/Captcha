package com.bitnet.paulo.captcha;

import com.bitnet.paulo.captcha.listeners.JoinListener;
import com.bitnet.paulo.captcha.listeners.MenuListener;
import com.bitnet.paulo.captcha.manager.CaptchaManager;
import com.bitnet.paulo.captcha.menu.CaptchaMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Map;

public class Main extends JavaPlugin {

    @Getter
    private CaptchaManager manager;

    @Getter
    private CaptchaMenu menu;

    @Override
    public void onEnable() {
        this.manager = new CaptchaManager();
        this.menu = new CaptchaMenu(this);
        registerListeners(new JoinListener(this), new MenuListener(this));
        this.getLogger().info("Plugin correctly loaded");
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!this.getMenu().getViewMap().isEmpty()) {
                if (this.getMenu().getViewMap().containsKey(p)) {
                    for (Map.Entry<Player, Inventory> viewEntry : this.getMenu().getViewMap().entrySet()) {
                        if (viewEntry.getValue() != null) {
                            Inventory inv = viewEntry.getValue();
                            for (HumanEntity player : inv.getViewers()) {
                                Player toClose = (Player) player;
                                toClose.closeInventory();
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> {
            Bukkit.getPluginManager().registerEvents(l, this);
        });
    }
}
