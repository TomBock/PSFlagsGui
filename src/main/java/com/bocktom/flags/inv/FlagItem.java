package com.bocktom.flags.inv;

import com.bocktom.flags.PSFlagsGui;
import com.bocktom.flags.util.MSG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class FlagItem extends AbstractItem {

	private final StateItem stateItem;
	private final ItemStack item;
	private final String cmd;

	public FlagItem(String cmd, ItemStack item, StateItem stateItem) {
		this.stateItem = stateItem;
		this.item = item;
		this.cmd = cmd;
	}

	@Override
	public ItemProvider getItemProvider() {
		return s -> item;
	}

	@Override
	public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
		boolean result = player.performCommand(cmd.replace("%value%", String.valueOf(!stateItem.state)));
		if(!result) {
			player.sendMessage(MSG.get("cmderror", "%cmd%", cmd));
		}
		stateItem.state = !stateItem.state;
		stateItem.notifyWindows();
	}
}
