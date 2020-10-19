package org.mine.orion.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.EntityUtil;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMetadata;
import org.mineacademy.fo.remain.CompParticle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class m14gun extends Tool implements Listener {
	@Getter
	private static final Tool instance = new m14gun();

	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.CARROT_ON_A_STICK, "M14 GUN", "RIGHT CLICK TO FIRE")
				.glow(true)
				.build().make();
	}
   Player p;
	@Override
	protected void onBlockClick(PlayerInteractEvent event) {
		if(event.getAction()!= Action.RIGHT_CLICK_AIR)
			return;
		Player player= event.getPlayer();
	 p=player;
		ItemStack ammo= PlayerUtil.getFirstItem(player, m14ammo.getInstance().getItem());
		if(ammo==null)
		{
			Common.tell(player,"GET AMMO FIRST!");
			event.setCancelled(true);
			return;
		}
		if(player.getGameMode()== GameMode.SURVIVAL) {
			PlayerUtil.takeOnePiece(player, ammo);
		}
		Arrow ar=player.getWorld().spawn(player.getLocation().clone().add(0d, 1.0d, 0d),Arrow.class);
		ar.setVelocity(player.getEyeLocation().getDirection().clone().rotateAroundX(0.1).multiply(5.0D));
		ar.setGravity(false);
		CompMetadata.setMetadata(ar, "m14bullet");
		EntityUtil.trackFlying(ar,()->{
			CompParticle.FLAME.spawn(ar.getLocation());
		});
		Location l=player.getLocation();
		float pitch=player.getEyeLocation().getPitch()-0.9f;
		float yaw=player.getEyeLocation().getYaw()+0.7f;
		double x= l.getX();
		double y= l.getY();
		double z= l.getZ();
		Location l1= new Location(player.getWorld(), x, y, z, yaw , pitch);
		player.teleport(l1);
		//	EntityUtil.trackFalling(am,()->{
		//	am.remove();
		//});
	}
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		if (!CompMetadata.hasMetadata(event.getEntity(), "m14bullet"))
		return;
		final Projectile projectile = event.getEntity();
		//ItemStack ammo= PlayerUtil.getFirstItem(p, m14ammo.getInstance().getItem());
	    Entity ent= event.getHitEntity();
	    if(ent instanceof LivingEntity)
		{
		  LivingEntity 	lent=(LivingEntity) ent;
			projectile.remove();
		  lent.damage(10D);
			return;
		}

		projectile.remove();
		projectile.getWorld().createExplosion(projectile.getLocation(), 1F);

	}
	@Override
	protected boolean ignoreCancelled() {
		return false;
	}
}
