package com.bitnet.paulo.captcha.menu;

import com.bitnet.paulo.captcha.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CaptchaMenu {

    private final Inventory inventory;

    private final Main plugin;

    @Getter
    private final HashMap<Player, InventoryView> viewMap;

    public CaptchaMenu(Main plugin) {
        this.plugin = plugin;
        this.viewMap = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, 9 * 6, ChatColor.translateAlternateColorCodes('&', "&cCaptcha"));
    }

    @SuppressWarnings("deprecation")
    private void buildItems() {
        if (this.getInventory() != null) {
            ItemStack verify_item;
            if (Bukkit.getBukkitVersion().contains("1.8")) {
                verify_item = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 5);
            } else {
                verify_item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            }
            ItemMeta verify_meta = verify_item.getItemMeta();

            assert verify_meta != null;
            verify_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lCLICK TO VERIFY"));
            verify_meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&fVerify that you're not a robot")));
            verify_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            verify_item.setItemMeta(verify_meta);

            Integer[] slot_array = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,
                                    46,47,48,49,50,51,52,53};
            List<Integer> slot_list = new ArrayList<>(Arrays.asList(slot_array));
            Random random = new Random();
            int slot = slot_list.get(random.nextInt(slot_list.size()));

            this.getInventory().setItem(slot, verify_item);
        }
    }

    public void openMenu(Player p) {
        if (this.getPlugin().getManager().containsPlayer(p)) {
            if(!this.getPlugin().getManager().getVerifiedStatus(p)) {
                buildItems();
                p.openInventory(this.getInventory());
                if (!this.viewMap.containsKey(p)) {
                    Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                        InventoryView view = p.getOpenInventory();
                        this.viewMap.put(p, view);
                    }, 5L);
                } else {
                    Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                        InventoryView view = p.getOpenInventory();
                        this.viewMap.put(p, view);
                    }, 5L);
                }
            }
        }
    }

    private Inventory getInventory() {
        return inventory;
    }

    private Main getPlugin() {
        return plugin;
    }
}