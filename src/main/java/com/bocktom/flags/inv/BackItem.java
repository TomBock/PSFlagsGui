package com.bocktom.flags.inv;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class BackItem extends PageItem {

	public BackItem() {
		super(false);
	}

	@Override
	public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
		if(pagedGui.hasPreviousPage())
			return new ItemBuilder(Material.ARROW).setDisplayName("§eVorherige Seite");
		else
			return new ItemBuilder(Material.AIR);
	}
}
