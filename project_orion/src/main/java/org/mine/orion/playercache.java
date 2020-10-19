package org.mine.orion;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter @Setter
public class playercache {

	private static Map<UUID, playercache> cacheMap = new HashMap<>();

	private ChatColor color;
	private int level = 1;

	public static playercache getCache(final Player player) {
		 playercache cache = cacheMap.get(player.getUniqueId());

		if (cache == null) {
			cache = new playercache();

			cacheMap.put(player.getUniqueId(), cache);
		}

		return cache;
	}
}
