package blue.nightmarish.pvpermsmanager;

import blue.nightmarish.pvpermsmanager.command.ReloadCommand;
import blue.nightmarish.pvpermsmanager.command.SetGroupCommand;
import blue.nightmarish.pvpermsmanager.event.PvPToggleStateListener;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PvPermsManager extends JavaPlugin {
    private static String PVP_GROUP;
    public static Logger LOGGER;
    //@Getter private PvPermsManager plugin;
    public LuckPerms luckPerms;

    @Override
    public void onEnable() {
        LOGGER = this.getLogger();
        this.luckPerms = LuckPermsProvider.get();

        this.saveDefaultConfig();
        setupConfig();

        if (loadConfig())
            LOGGER.info("Ready to add the '" + PVP_GROUP + "' group to and from members");
        else
            LOGGER.info("ERROR: The group '" + PVP_GROUP + "' was not found!");


        getServer().getPluginManager().registerEvents(new PvPToggleStateListener(this), this);

        this.getCommand("reload").setExecutor(new ReloadCommand(this));
        this.getCommand("setgroup").setExecutor(new SetGroupCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupConfig() {
        FileConfiguration config = this.getConfig();
        config.addDefault("pvp-group", "pvp");
    }

    // load the group and return the existence of said group
    public boolean loadConfig() {
        PVP_GROUP = this.getConfig().getString("pvp-group");
        Group group = getPvPGroup();
        return group != null;
    }

    public String getPvPGroupName() {
        return PVP_GROUP;
    }

    public Group getPvPGroup() {
        return this.luckPerms.getGroupManager().getGroup(PVP_GROUP);
    }
}
