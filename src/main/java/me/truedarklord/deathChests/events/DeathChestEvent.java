package me.truedarklord.deathChests.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DeathChestEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;

    private final Player player;
    private final List<ItemStack> drops;
    private final Location location;

    public DeathChestEvent(Player player, Location location, List<ItemStack> drops) {
        this.player = player;
        this.drops = drops;
        this.location = location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        cancel = cancelled;
    }

    public boolean callEvent() {
        Bukkit.getServer().getPluginManager().callEvent(this);
        return this.cancel;
    }

}
