package net.vanillaplus.redzonerestore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RedZoneRestore.getInstance().reloadConfig();
        sender.sendMessage(ChatColor.GREEN+"RedZoneRestore config reloaded.");

        return true;
    }
}
