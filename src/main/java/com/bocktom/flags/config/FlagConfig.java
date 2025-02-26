package com.bocktom.flags.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlagConfig {
	public String flag;
	public String cmd;
	public ItemStack item;

	public ValueConfig enabledCfg;
	public ValueConfig disabledCfg;

	public FlagConfig(Map<?, ?> map) {
		flag = (String) map.get("flag");
		cmd = (String) map.get("cmd");
		item = getItemStack(map);

		if(map.containsKey("enabled"))
			enabledCfg = new ValueConfig(map.get("enabled"));

		if(map.containsKey("disabled"))
			disabledCfg = new ValueConfig(map.get("disabled"));
	}

	private ItemStack getItemStack(Map<?, ?> map) {
		return new ItemBuilder(Material.valueOf((String) map.get("material")))
				.setDisplayName((String) map.get("displayname"))
				.setLegacyLore((List<String>) map.get("lore"))
				.get();
	}

	public static class ValueConfig {
		public String cmd;
		public String displayname;

		public ValueConfig(Object raw) {
			if(raw instanceof Map<?, ?> map) {
				cmd = (String) map.get("changecmd");
				displayname = (String) map.get("displayname");
			}
		}
	}

	public static List<FlagConfig> createList(List<Map<?, ?>> list) {
		List<FlagConfig> flags = new ArrayList<>();
		for (Map<?, ?> map : list) {
			FlagConfig cfg = new FlagConfig(map);
			flags.add(cfg);
		}
		return flags;
	}
}
