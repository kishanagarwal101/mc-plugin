package org.mine.orion.event;
import org.bukkit.entity.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mine.orion.OrionPlugin;
import org.mineacademy.fo.remain.CompParticle;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class projectilelistener implements Listener {

	private final Set<UUID> shotEggs = new HashSet<>();
	@EventHandler
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		final Projectile shot = event.getEntity();

		// If the shot projectile is an egg and the shooter is a player,
		// register the flying egg for later
		if (shot.getShooter() instanceof Player)
			if (shot instanceof Egg)
				shotEggs.add(shot.getUniqueId());

			else if (shot instanceof Arrow) {
				// Run an infinite timed task that is going to execute the code in run()
				// rapidly - each tick - until the arrow has been removed or hits the ground
				new BukkitRunnable() {
					@Override
					public void run() {
						// Stop this task when arrow is removed or hits the ground
						if (!shot.isValid() || shot.isOnGround()) {
							cancel();
							return;
						}

						// Spawn a flame particle at the current updated arrow location
						CompParticle.FLAME.spawn(shot.getLocation());
					}
				}.runTaskTimer(OrionPlugin.getInstance(), 0, 1);
			}
	}

	// Listen to when a projectile hits the ground
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		final Projectile shot = event.getEntity();

		// If the projectile is an egg and flying eggs set contains it,
		// run the following
		if (shot instanceof Egg && shotEggs.contains(shot.getUniqueId())) {

			// Clean the set
			shotEggs.remove(shot.getUniqueId());

			// Spawn a creeper at the location of the egg - sometimes a bit unprecise due to small egg calculations
			shot.getWorld().spawn(shot.getLocation(), Creeper.class);
		}
	}

	// Here is how to unregister events. This is not recommended and the Bukkit API is not
	// made well for this. I suggest you use a boolean field in the class to disable code
	// execution for those events instead
	//
	//ProjectileLaunchEvent.getHandlerList().unregister((Plugin) OrionPlugin.getInstance());
	//ProjectileHitEvent.getHandlerList().unregister((Plugin) OrionPlugin.getInstance());
	//HandlerList.unregisterAll((Plugin) OrionPlugin.getInstance());
}
