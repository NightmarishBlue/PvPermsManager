Add-on plugin for [PvPManager](https://github.com/ChanceSD/PvPManager) that adds or removes a [LuckPerms](https://luckperms.net/) group when players enable or disable PvP, respectively. This allows for easy permission changes, display name changes, etc.

By default, that group is named `pvp`, but it can be changed in the config or set with...

## Commands
### `/reload`
Reads the plugin's configuration file and updates the group that players with PvP on have. If there isn't a group with the specified name, this will report the error.
### `/pvpgroup` `<group name>`
Set the plugin's PvP group to the one with the given group name, and updates the config. If the given group name doesn't match a LuckPerms group, reports an error and keeps the old setting.
