package org.mine.orion.COMMAND;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

public class firework extends SimpleCommand {

	public firework() {
		super("firework"); // /firework --> run this command
	}

	@Override
	protected void onCommand() {
		// Available fields:
		// sender
		// args

		checkConsole();

		final Player player = getPlayer();

		player.getWorld().spawn(player.getLocation(), Firework.class);
		tell("&6A firework has been spawned!");
	}
}
