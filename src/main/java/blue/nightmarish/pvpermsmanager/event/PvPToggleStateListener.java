package blue.nightmarish.pvpermsmanager.event;

import blue.nightmarish.pvpermsmanager.PvPermsManager;
import lombok.Getter;
import me.NoChance.PvPManager.Events.PlayerTogglePvPEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static blue.nightmarish.pvpermsmanager.PvPermsManager.LOGGER;
import static blue.nightmarish.pvpermsmanager.PvPermsManager.PVP_GROUP;

public class PvPToggleStateListener implements Listener {
    private final PvPermsManager plugin;
    private final LuckPerms luckPerms;

    public PvPToggleStateListener(PvPermsManager plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;

        // whenever this object is spawned, let's see if we have the group actually in it
        Group group = this.luckPerms.getGroupManager().getGroup(PVP_GROUP);
        if (group == null)
            LOGGER.info("The group '" + PVP_GROUP + "' was not found");
        else
            LOGGER.info("Ready to add the '" + PVP_GROUP + "' group to and from members");
    }

    @EventHandler
    public void onPlayerTogglePvPEvent(PlayerTogglePvPEvent event) {
        Player player = event.getPlayer();
        boolean isPvPOnNow = event.getPvPState();
        boolean playerHasPvPGroup = player.hasPermission("group." + PVP_GROUP);

        if (isPvPOnNow) {
            if (!playerHasPvPGroup) {
                DataMutateResult output = addGroup(player, PVP_GROUP);
            }
        } else {
            if (playerHasPvPGroup) removeGroup(player, PVP_GROUP);
        }
    }

    public DataMutateResult addGroup(Player player, String groupName) {
        final DataMutateResult[] result = {DataMutateResult.FAIL}; // fuck Java lambdas

        Group group = this.luckPerms.getGroupManager().getGroup(groupName);
        if (group == null) return result[0]; // return generic fail if the group doesn't exist

        luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            Node newNode = InheritanceNode.builder(group).build();
            result[0] = user.data().add(newNode);
        });
        return result[0];
    }

    public DataMutateResult removeGroup(Player player, String groupName) {
        final DataMutateResult[] result = {DataMutateResult.FAIL}; // fuck Java lambdas

        Group group = this.luckPerms.getGroupManager().getGroup(groupName);
        if (group == null) return result[0]; // return generic fail if the group doesn't exist

        luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            Node prevNode = InheritanceNode.builder(group).build();
            result[0] = user.data().remove(prevNode);
        });
        return result[0];
    }
}
