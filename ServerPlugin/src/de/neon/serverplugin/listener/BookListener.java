package de.neon.serverplugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BookListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
			e.getPlayer().performCommand("skill");
		}
	}
	
	@EventHandler
	public void onBookThrow(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("§6Skill-book")) {
			e.setCancelled(true);
		}
	}
}