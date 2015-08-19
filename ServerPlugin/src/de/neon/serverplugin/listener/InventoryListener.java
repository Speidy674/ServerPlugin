package de.neon.serverplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase("§6Teleport-Menü")) {
			Player p = (Player) e.getWhoClicked();
			p.performCommand("tp "+e.getCursor().getItemMeta().getDisplayName().substring(2));
			p.closeInventory();
			e.setCancelled(true);
		}
	}
}