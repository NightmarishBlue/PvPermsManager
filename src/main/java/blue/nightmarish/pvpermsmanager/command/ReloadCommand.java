package blue.nightmarish.pvpermsmanager.command;

import blue.nightmarish.pvpermsmanager.PvPermsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final PvPermsManager plugin;
    public ReloadCommand(PvPermsManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.loadConfig())
            sender.sendMessage("SUCCESS: Ready to add the '" + plugin.getPvPGroupName() + "' group to and from members");
        else
            sender.sendMessage("ERROR: The group '" + plugin.getPvPGroup() + "' was not found!");
        return true;
    }
}
