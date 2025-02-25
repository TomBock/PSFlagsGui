package com.bocktom.flags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlagsCommand implements CommandExecutor {

	private final PSFlagsGui plugin;

	public FlagsCommand(PSFlagsGui plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player player))
			return true;

		if(!player.hasPermission("psflags.use")) {
			player.sendMessage("§cYou do not have permission to use this command.");
			return true;
		}

		plugin.openGui(player);

		return true;
	}
}
