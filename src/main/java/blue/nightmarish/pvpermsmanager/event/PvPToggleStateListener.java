package blue.nightmarish.pvpermsmanager.event;

import blue.nightmarish.pvpermsmanager.PvPermsManager;
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

public class PvPToggleStateListener implements Listener {
    private final PvPermsManager plugin;

    public PvPToggleStateListener(PvPermsManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTogglePvPEvent(PlayerTogglePvPEvent event) {
        Player player = event.getPlayer();
        String groupName = plugin.getPvPGroupName();

        boolean isPvPOnNow = event.getPvPState();
        boolean playerHasPvPGroup = player.hasPermission("group." + groupName);

        if (isPvPOnNow) {
            if (!playerHasPvPGroup) addGroup(player, groupName);
        } else {
            if (playerHasPvPGroup) removeGroup(player, groupName);
        }
    }

    public DataMutateResult addGroup(Player player, String groupName) {
        final DataMutateResult[] result = {DataMutateResult.FAIL}; // fuck Java lambdas

        Group group = plugin.luckPerms.getGroupManager().getGroup(groupName);
        if (group == null) return result[0]; // return generic fail if the group doesn't exist

        plugin.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            Node newNode = InheritanceNode.builder(group).build();
            result[0] = user.data().add(newNode);
        });
        return result[0];
    }

    public DataMutateResult removeGroup(Player player, String groupName) {
        final DataMutateResult[] result = {DataMutateResult.FAIL}; // fuck Java lambdas

        Group group = plugin.luckPerms.getGroupManager().getGroup(groupName);
        if (group == null) return result[0]; // return generic fail if the group doesn't exist

        plugin.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            Node prevNode = InheritanceNode.builder(group).build();
            result[0] = user.data().remove(prevNode);
        });
        return result[0];
    }
}
