package me.truedarklord.deathChests.listeners;

import me.truedarklord.deathChests.DeathChests;
import me.truedarklord.deathChests.events.DeathChestEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeathMessage implements Listener {

    private final DeathChests plugin;

    public DeathMessage(DeathChests plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDeathChest(DeathChestEvent event) {
        sendDeathMessage(event.getPlayer(), event.getLocation());
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

}
