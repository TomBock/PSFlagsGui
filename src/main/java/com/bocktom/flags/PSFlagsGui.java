package com.bocktom.flags;

import com.bocktom.flags.util.MSG;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Reader;

public final class PSFlagsGui extends JavaPlugin {

	public static PSFlagsGui plugin;

	@Override
	public void onEnable() {
		plugin = this;

		if(Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
			getLogger().severe("PlotSquared not found! Disabling plugin...");
			getServer().getPluginManager().disablePlugin(this);
		}

		setupDefaultConfig();

		getCommand("flags").setExecutor(new FlagsCommand(this));
	}

	private void setupDefaultConfig() {
		FileConfiguration config = getConfig();
		Reader defaultConfig = getTextResource("config.yml");

		if(defaultConfig != null) {
			config.setDefaults(YamlConfiguration.loadConfiguration(defaultConfig));
			config.options().copyDefaults(true);
			saveConfig();
		}
	}

	public void openGui(Player player) {
		org.bukkit.Location bukkitLoc = player.getLocation();
		Location location = Location.at(bukkitLoc.getWorld().getName(), bukkitLoc.getBlockX(), bukkitLoc.getBlockY(), bukkitLoc.getBlockZ());
		Plot plot = location.getPlot();

		if(plot == null) {
			player.sendMessage(MSG.get("noplot"));
			return;
		}
		new FlagsInventory(player, plot);
	}
}
