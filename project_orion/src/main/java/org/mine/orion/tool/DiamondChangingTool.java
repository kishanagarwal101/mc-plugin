package org.mine.orion.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiamondChangingTool extends Tool {

	@Getter
	private static final Tool instance = new DiamondChangingTool();

	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.DIAMOND_AXE,
				"&bThe Magical Axe",
				"",
				"Click to turn any block",
				"into a diamond block!")
				.glow(true).build().make();
	}

	@Override
	protected void onBlockClick(final PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			event.getClickedBlock().setType(CompMaterial.DIAMOND_BLOCK.getMaterial());

			Common.tell(event.getPlayer(), "&aThe clicked block has been transformed!");
		}
	}
}
