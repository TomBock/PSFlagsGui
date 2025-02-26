package com.bocktom.flags;

import com.bocktom.flags.config.FlagConfig;
import com.bocktom.flags.inv.*;
import com.bocktom.flags.util.MSG;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotWeather;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.PlotFlag;
import com.plotsquared.core.plot.flag.implementations.FlyFlag;
import com.plotsquared.core.plot.flag.implementations.TimeFlag;
import com.plotsquared.core.plot.flag.implementations.WeatherFlag;
import com.plotsquared.core.plot.flag.types.BooleanFlag;
import com.plotsquared.core.plot.flag.types.LongFlag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
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
		List<Map<?, ?>> list = PSFlagsGui.plugin.getConfig().getMapList("flags");
		return FlagConfig.createList(list);
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

			} else if(cfgFlag instanceof TimeFlag timeFlag) {
				couldRead = true;
				long current = plot.getFlag(timeFlag);
				state = current < 13000 || 23000 < current;

			} else if(cfgFlag instanceof FlyFlag flyFlag) {
				couldRead = true;
				state = plot.getFlag(flyFlag) == FlyFlag.FlyStatus.ENABLED;

			} else if (cfgFlag instanceof WeatherFlag weatherFlag) {
				couldRead = true;
				state = plot.getFlag(weatherFlag) == PlotWeather.CLEAR;
			}

			if(!couldRead) {
				PSFlagsGui.plugin.getLogger().warning("Flag not supported: " + cfg.flag);
				continue;
			}

			StateItem stateItem = new StateItem(state, cfg.enabledCfg, cfg.disabledCfg);

			// Flag
			FlagItem item = new FlagItem(cfg.cmd, cfg.item, stateItem);

			items.add(item);
			items.add(stateItem);
		}
		return items;
	}
}
