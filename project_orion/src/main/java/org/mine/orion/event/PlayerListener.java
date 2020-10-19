package org.mine.orion.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mine.orion.playercache;

public class PlayerListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChatFirst(final AsyncPlayerChatEvent event) {
		//Common.log("Lowest priority - " + event.getPlayer().getName() + ": " + event.getMessage());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		//Common.log("Normal priority (cancelling) - " + event.getPlayer().getName() + ": " + event.getMessage());

		//event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerChatLate(final AsyncPlayerChatEvent event) {
		//Common.log("High priority (cancelled=" + event.isCancelled() + ") - " + event.getPlayer().getName() + ": " + event.getMessage());

		//event.setCancelled(false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChatLast(final AsyncPlayerChatEvent event) {
		//Common.log("Monitor priority (cancelled=" + event.isCancelled() + ") - " + event.getPlayer().getName() + ": " + event.getMessage());
	}
	@EventHandler
	public void onPlayerChat2(final AsyncPlayerChatEvent event) {
		final playercache cache = playercache.getCache(event.getPlayer());
		final ChatColor color = cache.getColor();

		if (color != null)
			event.setMessage(color + event.getMessage());
	}
}
