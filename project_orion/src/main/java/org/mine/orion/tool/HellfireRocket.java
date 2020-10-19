package org.mine.orion.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Rocket;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompParticle;


public class HellfireRocket extends Rocket {

	@Getter
	private static final Tool instance = new HellfireRocket();

	private HellfireRocket() {
		super(DragonFireball.class, 2F, 10F);
	}

	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.FIRE_CHARGE,
				"&cHellfire",
				"",
				"Deliver some cookies")
				.build().make();
	}

	// Override to prevent launching rockets from a protected location
	// Returns true in super
	@Override
	protected boolean canLaunch(final Player shooter, final Location location) {
		return super.canLaunch(shooter, location);
	}

	// Override to prevent exploding rockets in a protected location
	// Returns true in super
	@Override
	protected boolean canExplode(final Projectile projectile, final Player shooter) {
		return super.canExplode(projectile, shooter);
	}

	@Override
	protected void onLaunch(final Projectile projectile, final Player shooter) {
		Common.tell(shooter, "&8[&aRocket&8] &7Hey grandma, here are some cookies for ya!");
	}

	@Override
	protected void onExplode(final Projectile projectile, final Player shooter) {
		Common.tell(shooter, "&8[&aRocket&8] &7Cookies delivered, grandma not responding yet...");
	}

	// Called each 1 tick during the flight of this rocket
	@Override
	protected void onFlyTick(final Projectile projectile, final Player shooter) {
		CompParticle.FLAME.spawn(projectile.getLocation(), 1D);
	}
}
