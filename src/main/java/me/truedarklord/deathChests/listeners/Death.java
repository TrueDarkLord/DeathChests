package me.truedarklord.deathChests.listeners;

import me.truedarklord.deathChests.DeathChests;
import me.truedarklord.deathChests.events.DeathChestEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Death implements Listener {

    private final Plugin plugin;

    public Death(DeathChests plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location loc = event.getEntity().getLocation().clone();
        List<ItemStack> drops = event.getDrops();

        DeathChestEvent chestEvent = new DeathChestEvent(player, loc, drops);
        if (chestEvent.callEvent()) return;

        while (!drops.isEmpty()) {
            setInvDrops(findEmptyBlock(loc), drops);
        }

        sendDeathMessage(event.getEntity(), loc);

    }

    private void sendDeathMessage(Player player, Location loc) {
        String msg = plugin.getConfig().getString("Death_Message", "&2You Died at &6%world% X:%x% Y:%y% Z:%z%&2.");
        msg = msg.replaceAll("%world%", loc.getWorld().getName())
              .replaceAll("%x%", String.valueOf(loc.getBlockX()))
             .replaceAll("%y%", String.valueOf(loc.getBlockY()))
             .replaceAll("%z%", String.valueOf(loc.getBlockZ()));
        
        player.sendMessage(colour(msg));
    }

    private static String colour(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F\\d]{6}").matcher(message);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(sb, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(sb).toString());
    }

    private Block findEmptyBlock(Location loc) {
        Block block = loc.getBlock();

        while (block.getType() != Material.AIR) {
            block = loc.add(0, 1, 0).getBlock();
        }

        return block;
    }

    private void setInvDrops(Block block, List<ItemStack> drops) {
        int maxItems = Math.min(drops.size(), 27);

        block.setType(Material.CHEST);
        Inventory chestInventory2 = ((Container) block.getState()).getInventory();

        for (int i = 0; i < maxItems; i++) {
            chestInventory2.addItem(removeFirst(drops));
        }
    }

    private ItemStack removeFirst(List<ItemStack> contents) {
        ItemStack first = contents.get(0);

        contents.remove(0);

        return first;
    }

}
