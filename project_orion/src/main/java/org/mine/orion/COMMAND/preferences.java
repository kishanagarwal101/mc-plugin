package org.mine.orion.COMMAND;
import org.mine.orion.menu.PreferencesPanelMenu;
import org.mineacademy.fo.command.SimpleCommand;

public class preferences extends SimpleCommand {
	public preferences() {
		super("pref|prefernces");
	}

	@Override
	protected void onCommand() {

  new PreferencesPanelMenu().displayTo(getPlayer());
		/*
		InventoryDrawer drawer= InventoryDrawer.of(9*3,"&4User preferences");
		drawer.setItem(0,ItemCreator.of(CompMaterial.CARROT_ON_A_STICK, "&1M14", "gun").
				glow(true).
				build().make());
		drawer.display(getPlayer());
	*/}
}
