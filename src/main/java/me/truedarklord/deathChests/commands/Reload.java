package me.truedarklord.deathChests.commands;

import me.truedarklord.deathChests.DeathChests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {

    private final DeathChests plugin;

    public Reload(DeathChests plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();

        plugin.getCommand("reload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        sender.sendMessage("Reloaded The Config Files.");

        return true;
    }
}