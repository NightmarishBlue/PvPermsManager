package blue.nightmarish.pvpermsmanager;

import blue.nightmarish.pvpermsmanager.event.PvPToggleStateListener;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PvPermsManager extends JavaPlugin {
    public static final String PVP_GROUP = "pvpon";

    public static Logger LOGGER;

    //@Getter private PvPermsManager plugin;
    private LuckPerms luckPermsAPI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        LOGGER = this.getLogger();
        this.luckPermsAPI = LuckPermsProvider.get();

        getServer().getPluginManager().registerEvents(new PvPToggleStateListener(this, luckPermsAPI), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
