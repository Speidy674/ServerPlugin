package de.neon.serverplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;
import de.neon.serverplugin.town.Town;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = (Player) e.getPlayer();
		if(ServerPlugin.create.contains(p)) {
			e.setCancelled(true);
			if(TownUtil.exists(e.getMessage())) {
				p.sendMessage("§4Der Stadtname "+e.getMessage()+" ist schon vergeben.");
			} else {
				double minX = p.getLocation().getX() - (Town.SIZE_X / 2);
				double minZ = p.getLocation().getZ() - (Town.SIZE_Z / 2);
				double maxX = p.getLocation().getX() + (Town.SIZE_X / 2);
				double maxZ = p.getLocation().getZ() + (Town.SIZE_Z / 2);
				TownUtil.createTown(p.getName(), e.getMessage(), minX, minZ, maxX, maxZ);
				p.sendMessage("§aDie Stadt §b"+e.getMessage()+" §bwurde erfolgreich erstellt!");
				ServerPlugin.create.remove(p);
			}
		}
	}
}