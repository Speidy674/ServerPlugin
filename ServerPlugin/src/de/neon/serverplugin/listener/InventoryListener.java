package de.neon.serverplugin.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase("Teleport-Menü")) {
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				Player p = (Player) e.getWhoClicked();
				p.performCommand("tp "+e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
				p.closeInventory();
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("Teleport Menü")) {
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				Player p = (Player) e.getWhoClicked();
				p.performCommand("tph "+e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
				p.closeInventory();
			}
			e.setCancelled(true);
		}
	}
}