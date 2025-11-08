package me.truedarklord.deathChests.listeners;

import me.truedarklord.deathChests.DeathChests;
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

public class Death implements Listener {

    private final Plugin plugin;

    public Death(DeathChests plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        Location loc = event.getEntity().getLocation().clone();
        List<ItemStack> drops = event.getDrops();

        while (!drops.isEmpty()) {
            setInvDrops(findEmptyBlock(loc), drops);
        }

        sendDeathMessage(event.getEntity(), loc);

    }

    private void sendDeathMessage(Player player, Location loc) {
        String msg = plugin.getConfig().getString("Death_Message", "You Died at %world% X:%x% Y:%y% Z:%z%.");
        msg = msg.replaceAll("%x%", String.valueOf(loc.getX()))
             .replaceAll("%y%", String.valueOf(loc.getX()))
             .replaceAll("%z%", String.valueOf(loc.getZ()));
        
        player.sendMessage(msg);
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
