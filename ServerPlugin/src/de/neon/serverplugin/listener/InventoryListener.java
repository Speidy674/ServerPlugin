package de.neon.serverplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;

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
		if(e.getInventory().getTitle().equalsIgnoreCase("Towns")) {
			if(e.getCurrentItem().getType() == Material.COMPASS) {
				Player p = (Player) e.getWhoClicked();
				p.sendMessage("§6Schreibe in den Chat, wie deine Stadt heißen soll. Wenn du doch keine Stadt erstellen willst, nutze /town.");
				ServerPlugin.create.add(p);
				p.closeInventory();
			}
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				Player p = (Player) e.getWhoClicked();
				p.closeInventory();
				int size = ((TownUtil.getTown(p).getMembers().size()) / 9) * 9 + 9;
				Inventory inv = Bukkit.createInventory(p, size, "Teleport-Menü");
				for(String player : TownUtil.getTown(p).getMembers()) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setDisplayName("§6"+player);
					item.setItemMeta(meta);
					inv.addItem(item);
				}
				p.openInventory(inv);
			}
			if(e.getCurrentItem().getType() == Material.WATCH) {
				Player p = (Player) e.getWhoClicked();
				p.sendMessage("§6Schreibe in den Chat, wen du eine Anfrage stellen willst. Wenn du doch niemanden einladen willst, nutze /town.");
				ServerPlugin.invite.add(p);
				p.closeInventory();
			}
			e.setCancelled(true);
		}
	}
}