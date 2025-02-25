package com.bocktom.flags.inv;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.function.Supplier;

public class StateItem extends AbstractItem {

	private static final ItemBuilder enabled = new ItemBuilder(Material.LIME_DYE).setDisplayName("§aAktiviert");
	private static final ItemBuilder disabled = new ItemBuilder(Material.RED_DYE).setDisplayName("§cDeaktiviert");

	public boolean state;

	public StateItem(boolean state) {
		this.state = state;
	}

	@Override
	public ItemProvider getItemProvider() {
		return state ? enabled : disabled;
	}

	@Override
	public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
		// ignored
	}

}
