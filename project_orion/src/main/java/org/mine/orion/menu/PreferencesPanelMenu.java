package org.mine.orion.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mine.orion.playercache;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.MenuQuantitable;
import org.mineacademy.fo.menu.MenuTools;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.MenuQuantity;
import org.mineacademy.fo.remain.CompMaterial;
import org.mine.orion.tool.DiamondChangingTool;
import org.mine.orion.tool.HellfireRocket;
import org.mine.orion.tool.m14ammo;
import org.mine.orion.tool.m14gun;


import java.util.Arrays;
import java.util.stream.Collectors;

public class PreferencesPanelMenu extends Menu {

	private final Button timeButton;
	private final Button mobEggButton;

	private final Button levelButton;
	private final Button toolsButton;

	public PreferencesPanelMenu() {

		setTitle("&4User preferences");
		setSize(9*6);

		setSlotNumbersVisible();

		timeButton = new Button() {
			@Override
			public void onClickedInMenu(final Player player, final Menu menu, final ClickType clickType) {
					final boolean isDay = isDay();

					player.getWorld().setFullTime(isDay ? 13000 : 1000);
					restartMenu("&2Set the time to " + (isDay ? "night" : "day"));
			}

			@Override
			public ItemStack getItem() {
				final boolean isDay = isDay();

				return ItemCreator.of(isDay ? CompMaterial.SUNFLOWER : CompMaterial.RED_BED,
						"Change the time",
						"",
						"Currently: " + (isDay ? "day" : "night"),
						"",
						"Click to switch between",
						"the day and the night"
				).build().make();
			}

			private boolean isDay() {
				return getViewer().getWorld().getFullTime() < 12500;
			}
		};

		mobEggButton = new ButtonMenu(new EggSelectionMenu(), CompMaterial.ENDERMAN_SPAWN_EGG,
				"Monster egg menu",
				"",
				"Click to open monster",
				"egg selection menu.");


		levelButton = new ButtonMenu(new LevelMenu(), CompMaterial.ELYTRA,
				"Level menu",
				"",
				"Click to open level",
				"menu to change your level.");

		toolsButton = new ButtonMenu(new OrionTools(), CompMaterial.BLAZE_ROD,
				"Tools menu",
				"",
				"Click to open tools",
				"menu to get admin tools.");
	}

	@Override
	public ItemStack getItemAt(final int slot) {

		if (slot == 9*1 + 2)
			return timeButton.getItem();

		if (slot == 9*1 + 6)
			return mobEggButton.getItem();

		if (slot == 9*3 + 2)
			return levelButton.getItem();

		if (slot == 9*3 + 6)
			return toolsButton.getItem();

		return null;
	}

	@Override
	protected String[] getInfo() {
		return new String[] {
				"This menu contains simple features",
				"for players or administrators to",
				"enhance their gameplay experience."
		};
	}

	private final class EggSelectionMenu extends MenuPagged<EntityType> {

		private EggSelectionMenu() {
			super(9*4, PreferencesPanelMenu.this, Arrays.asList(EntityType.values())
					.stream()
					.filter((entityType) -> entityType.isSpawnable() && entityType.isAlive() && (entityType == EntityType.SHEEP || CompMaterial.makeMonsterEgg(entityType) != CompMaterial.SHEEP_SPAWN_EGG))
					.collect(Collectors.toList()));

			setTitle("Select a mob egg");
		}

		@Override
		protected ItemStack convertToItemStack(final EntityType entityType) {
			return ItemCreator.of(CompMaterial.makeMonsterEgg(entityType), "Spawn " + ItemUtil.bountifyCapitalized(entityType)).build().make();
		}

		@Override
		protected void onPageClick(final Player player, final EntityType entityType, final ClickType clickType) {
			player.getInventory().addItem(ItemCreator.of(CompMaterial.makeMonsterEgg(entityType)).build().make());
			animateTitle("Egg added into your inventory!");
		}

		@Override
		protected String[] getInfo() {
			return new String[] {
					"Click an egg to get it",
					"into your inventory."
			};
		}
	}


	private final class LevelMenu extends Menu implements MenuQuantitable {

		@Setter @Getter
		private MenuQuantity quantity = MenuQuantity.ONE;

		private final Button quantityButton;
		private final Button levelButton;

		private LevelMenu() {
			super(PreferencesPanelMenu.this);

			setTitle("Change your level");

			quantityButton = getEditQuantityButton(this);

			levelButton = new Button() {
				@Override
				public void onClickedInMenu(final Player player, final Menu menu, final ClickType clickType) {
					final playercache cache = playercache.getCache(getViewer());
					final int nextLevel = MathUtil.range(cache.getLevel() + getNextQuantity(clickType), 1, 64);

					cache.setLevel(nextLevel);
					restartMenu("Changed level to " + nextLevel);
				}

				@Override
				public ItemStack getItem() {
					final playercache cache = playercache.getCache(getViewer());

					return ItemCreator.of(CompMaterial.ELYTRA,
							"Change your level",
							"",
							"Current: " + cache.getLevel(),
							"",
							"&8(&7Mouse click&8)",
							"< -{q} +{q} >".replace("{q}", quantity.getAmount() + ""))
							.amount(cache.getLevel() == 0 ? 1 : cache.getLevel())
							.build().make();
				}
			};
		}

		@Override
		public ItemStack getItemAt(final int slot) {

			if (slot == getCenterSlot())
				return levelButton.getItem();

			if (slot == getSize() - 5)
				return quantityButton.getItem();

			return null;
		}

		@Override
		protected String[] getInfo() {
			return new String[] {
				"Click the elytra to",
				"level up/down yourself."
			};
		}
	}

	private final class OrionTools extends MenuTools {

		// Return the items you want in this tools menu
		// positioned with the array. Use 0 to place air and increase their position
		@Override
		protected Object[] compileTools() {
			return new Object[] {
					0, HellfireRocket.getInstance(), DiamondChangingTool.getInstance(),0, m14gun.getInstance(),
					 m14ammo.getInstance()
			};
		}

		@Override
		protected String[] getInfo() {
			return new String[] {
					"Select your tools",
					"for some fun!"
			};
		}
	}
}
