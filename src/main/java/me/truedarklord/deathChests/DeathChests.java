package me.truedarklord.deathChests;

import me.truedarklord.deathChests.commands.Reload;
import me.truedarklord.deathChests.listeners.Death;
import me.truedarklord.deathChests.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathChests extends JavaPlugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 26017);

        saveDefaultConfig();
        advertise();

        new Death(this);
        new Reload(this);

    }

    private void advertise() {
        this.getServer().getConsoleSender().sendMessage(
                """
  
 §#00AA00================================§#ee2222
 _____             _   _       \s
|  __ \\           | | | |      \s
| |  | | ___  __ _| |_| |__    \s
| |  | |/ _ \\/ _` | __| '_ \\   \s
| |__| |  __/ (_| | |_| | | |  \s
|_____/ \\___|\\__,_|\\__|_| |_|  \s
 / ____| |             | |     \s
| |    | |__   ___  ___| |_ ___\s
| |    | '_ \\ / _ \\/ __| __/ __|
| |____| | | |  __/\\__ \\ |_\\__ \\
 \\_____|_| |_|\\___||___/\\__|___/

§#f5da2aBy TrueDarkLord.
§#00AA00================================
§#f5da2aFeel free to buy me a coffee:  §#00AA00|
§bhttps://ko-fi.com/truedarklord §#00AA00|
§#00AA00================================
                        """
        );
    }

}
