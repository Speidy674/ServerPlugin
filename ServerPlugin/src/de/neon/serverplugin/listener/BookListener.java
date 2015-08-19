package de.neon.serverplugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class BookListener implements Listener {

	@EventHandler
	public void onItemClick(InventoryClickEvent e) {
		if(e.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase("§6Skill-Buch")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBookThrow(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("§6Skill-Buch")) {
			e.setCancelled(true);
		}
	}
}