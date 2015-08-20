package de.neon.serverplugin.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.neon.serverplugin.ServerPlugin;

public class InventoryListener implements Listener {

	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase("Teleport-Men�")) {
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				Player p = (Player) e.getWhoClicked();
				p.performCommand("tp "+e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
				p.closeInventory();
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("Teleport Men�")) {
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				Player p = (Player) e.getWhoClicked();
				p.performCommand("tph "+e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
				p.closeInventory();
			}
			e.setCancelled(true);
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("Towns")) {
			if(e.getCurrentItem().getType() == Material.COMPASS) {
				Player p = (Player) e.getWhoClicked();
				p.sendMessage("�6Schreibe in den Chat, wie deine Stadt hei�en soll.");
				ServerPlugin.create.add(p);
				p.closeInventory();
			}
			e.setCancelled(true);
		}
	}
}