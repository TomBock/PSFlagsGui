package com.bocktom.flags.inv;

import com.bocktom.flags.config.FlagConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class StateItem extends AbstractItem {

	private static final ItemBuilder defaultEnabled = new ItemBuilder(Material.LIME_DYE).setDisplayName("§aAktiviert");
	private static final ItemBuilder defaultDisabled = new ItemBuilder(Material.RED_DYE).setDisplayName("§cDeaktiviert");

	protected ItemBuilder enabledItem;
	protected ItemBuilder disabledItem;

	public boolean state;

	private String enabledCmd;
	private String disabledCmd;

	public StateItem(boolean state, @Nullable FlagConfig.ValueConfig enabledCfg, @Nullable FlagConfig.ValueConfig disabledCfg) {
		this.state = state;
		this.enabledItem = defaultEnabled;
		this.disabledItem = defaultDisabled;

		if(enabledCfg != null && disabledCfg != null) {
			enabledItem = new ItemBuilder(enabledItem.get()).setDisplayName(enabledCfg.displayname);
			disabledItem = new ItemBuilder(disabledItem.get()).setDisplayName(disabledCfg.displayname);
			enabledCmd = enabledCfg.cmd;
			disabledCmd = disabledCfg.cmd;
		} else {
			enabledCmd = "true";
			disabledCmd = "false";
		}
	}

	public String getCmdValue() {
		return state ? enabledCmd : disabledCmd;
	}

	@Override
	public ItemProvider getItemProvider() {
		return state ? enabledItem : disabledItem;
	}

	@Override
	public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
		// ignored
	}

}
