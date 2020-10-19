package org.mine.orion;

import com.gmail.nossr50.listeners.PlayerListener;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.mine.orion.COMMAND.firework;
import org.mine.orion.COMMAND.preferences;
import org.mine.orion.event.projectilelistener;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

public class OrionPlugin extends SimplePlugin {

	@Override
	protected void onPluginStart() {
		Common.log("namaste");

		registerCommand(new firework());
		registerCommand(new preferences());
		
		//registerEvents(new PlayerListener());
		registerEvents(new projectilelistener());
	}
	@EventHandler
	public  void  onjoin (PlayerJoinEvent event)
	{
		Player player= event.getPlayer();
		Common.tell(player,"HELLO FROM THE OTHER SIDE");
		///player.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE,3));

	}

	@EventHandler
	public void onEntityDamage(final EntityDamageByEntityEvent event) {
		final Entity victim = event.getEntity();

		// If the damager is a player and the hit entity (victim) is a cow,
		// create explosion with the size of 2 at the cows location
		if (event.getDamager() instanceof Player && victim instanceof Cow)
			victim.getWorld().createExplosion(victim.getLocation(), 2);
	}
}
