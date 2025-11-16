package me.truedarklord.deathChests.listeners;

import me.truedarklord.deathChests.DeathChests;
import me.truedarklord.deathChests.events.DeathChestEvent;
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

import java.util.List;

public class Death implements Listener {

    public Death(DeathChests plugin) {
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
        return contents.remove(0);
    }

}
