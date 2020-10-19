package org.mine.orion.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class m14ammo extends Tool {
	@Getter
	private  static final Tool instance = new m14ammo();

	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.IRON_INGOT, "M14 AMMMO", "Use with m14 gun")
				.glow(true)
				.build().make();
	}

	@Override
	protected void onBlockClick(PlayerInteractEvent event) {
    event.setCancelled(true);
	}
}
