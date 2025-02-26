package com.bocktom.flags;

import com.bocktom.flags.inv.*;
import com.bocktom.flags.util.MSG;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.PlotFlag;
import com.plotsquared.core.plot.flag.implementations.FlyFlag;
import com.plotsquared.core.plot.flag.types.BooleanFlag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlagsInventory {

	public FlagsInventory(Player player, Plot plot) {

		List<FlagConfig> flags = getConfig();
		List<Item> items = getItems(plot, flags);

		Gui gui = PagedGui.items()
				.setStructure(
						". . . . . . . . .",
						". x . x . x . x .",
						". x . x . x . x .",
						". . . . . . . . .",
						"p . . . c . . . n")
				.addIngredient('p', new BackItem())
				.addIngredient('n', new ForwardItem())
				.addIngredient('c', new CloseItem())
				.addIngredient('x', Markers.CONTENT_LIST_SLOT_VERTICAL)
				.setContent(items)
				.build();

		Window window = Window.single()
				.setViewer(player)
				.setTitle(MSG.get("gui.title"))
				.setGui(gui)
				.build();

		window.open();
	}

	private List<FlagConfig> getConfig() {
		List<FlagConfig> flags = new ArrayList<>();
		List<Map<?, ?>> list = PSFlagsGui.plugin.getConfig().getMapList("flags");
		for (Map<?, ?> map : list) {
			FlagConfig cfg = new FlagConfig();
			cfg.flag = (String) map.get("flag");
			cfg.cmd = (String) map.get("cmd");
			cfg.item = (ItemStack) map.get("item");
			flags.add(cfg);
		}
		return flags;
	}

	private static List<Item> getItems(Plot plot, List<FlagConfig> flags) {
		List<Item> items = new ArrayList<>();

		for (FlagConfig cfg : flags) {

			PlotFlag<?, ? extends PlotFlag<?, ?>> cfgFlag = GlobalFlagContainer.getInstance().getFlagFromString(cfg.flag);

			if (cfgFlag == null) {
				PSFlagsGui.plugin.getLogger().warning("Flag not found: " + cfg.flag);
				continue;
			}

			boolean couldRead = false;
			boolean state = false;

			if(cfgFlag instanceof BooleanFlag<?> booleanFlag) {
				couldRead = true;
				state = plot.getFlag(booleanFlag);
			} else if(cfgFlag instanceof FlyFlag flyFlag) {
				couldRead = true;
				state = plot.getFlag(flyFlag) == FlyFlag.FlyStatus.ENABLED;
			}

			if(!couldRead) {
				PSFlagsGui.plugin.getLogger().warning("Flag not supported: " + cfg.flag);
				continue;
			}

			// Green or Red Dye
			StateItem stateItem = new StateItem(state);

			// Flag
			FlagItem item = new FlagItem(cfg.cmd, cfg.item, stateItem);

			items.add(item);
			items.add(stateItem);
		}
		return items;
	}

	public static class FlagConfig {
		public String flag;
		public String cmd;
		public ItemStack item;
	}
}
