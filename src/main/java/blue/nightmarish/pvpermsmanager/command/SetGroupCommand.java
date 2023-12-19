package blue.nightmarish.pvpermsmanager.command;

import blue.nightmarish.pvpermsmanager.PvPermsManager;
import net.luckperms.api.model.group.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetGroupCommand implements CommandExecutor {
    private final PvPermsManager plugin;
    public SetGroupCommand(PvPermsManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return false;

        String groupName = args[0];
        Group group = plugin.luckPerms.getGroupManager().getGroup(groupName);

        if (group != null) {
            plugin.getConfig().set("pvp-group", groupName);
            plugin.reloadConfig();
            sender.sendMessage("SUCCESS: Ready to add the '" + groupName + "' group to and from members");
        } else {
            sender.sendMessage("ERROR: The group '" + groupName + "' was not found!");
            sender.sendMessage("Group has not been changed (is set to '" + plugin.getPvPGroupName() + "')");
        }

        return true;
    }
}
