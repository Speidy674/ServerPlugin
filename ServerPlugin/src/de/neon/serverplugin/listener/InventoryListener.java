package de.neon.serverplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;
import de.neon.serverplugin.town.Town;

public class InventoryListener implements Listener {

	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) {
			return;
		}
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
		if(e.getInventory().getTitle().equalsIgnoreCase("Bewohner")) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if(e.getCurrentItem().getType() == Material.TNT) {
				p.closeInventory();
				p.performCommand("town");
			}
		}
		if(e.getInventory().getTitle().equalsIgnoreCase("Towns")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.COMPASS) {
				Player p = (Player) e.getWhoClicked();
				p.sendMessage("§6Schreibe in den Chat, wie deine Stadt heißen soll. Wenn du doch keine Stadt erstellen willst, nutze /town.");
				ServerPlugin.create.add(p);
				p.closeInventory();
			}
			if(e.getCurrentItem().getType() == Material.YELLOW_FLOWER) {
				Player p = (Player) e.getWhoClicked();
				p.closeInventory();
				ServerPlugin.welcome.add(p);
				p.sendMessage("§6Schreibe in den Chat, wie deine Willkommens Nachricht lauten soll. Wenn du doch die Nachricht verändern willst, nutze /town.");
				p.sendMessage("§6%player% = Spielername");
				p.sendMessage("§6%time% = Uhrzeit");
				p.sendMessage("§6%town% = Stadtname");
			}
			if(e.getCurrentItem().getType() == Material.RED_ROSE) {
				Player p = (Player) e.getWhoClicked();
				p.closeInventory();
				ServerPlugin.adoption.add(p);
				p.sendMessage("§6Schreibe in den Chat, wie deine Verabschiedungs Nachricht lauten soll. Wenn du doch die Nachricht verändern willst, nutze /town.");
				p.sendMessage("§6%player% = Spielername");
				p.sendMessage("§6%time% = Uhrzeit");
				p.sendMessage("§6%town% = Stadtname");
			}
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() != 3) {
				Player p = (Player) e.getWhoClicked();
				p.closeInventory();
				int size = ((TownUtil.getTown(p).getMembers().size()+1) / 9) * 9 + 9;
				Inventory inv = Bukkit.createInventory(p, size, "Bewohner");
				for(String player : TownUtil.getTown(p).getMembers()) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					if(Bukkit.getPlayer(player) == null) {
						meta.setDisplayName("§c"+player);
					} else {
						meta.setDisplayName("§a"+player);
					}
					item.setItemMeta(meta);
					inv.addItem(item);
				}
				ItemStack back = new ItemStack(Material.TNT);
				ItemMeta meta = back.getItemMeta();
				meta.setDisplayName("§cZurück");
				back.setItemMeta(meta);
				inv.setItem(size-1, back);
				p.openInventory(inv);
			}
			if(e.getCurrentItem().getType() == Material.BARRIER) {
				Player p = (Player) e.getWhoClicked();
				Town town = TownUtil.getTown(p);
				town.removeMember(p.getName());
				p.sendMessage("§aDu hast die Stadt §b"+town.getName()+" §averlassen.");
				TownUtil.sendMessageToTown(town, "§4"+p.getName()+" hat die Stadt verlassen.");
				p.closeInventory();
				DataUtil.set(p, "town", "null");
			}
			if(e.getCurrentItem().getType() == Material.TNT) {
				Player p = (Player) e.getWhoClicked();
				Town town = TownUtil.getTown(p);
				p.sendMessage("§aDu hast die Stadt §b"+town.getName()+" §agelöscht.");
				TownUtil.sendMessageToTown(town, "§4Deine Stadt wurde gelöscht.");
				p.closeInventory();
				town.delete();
			}
			if(e.getCurrentItem().getType() == Material.WATCH) {
				Player p = (Player) e.getWhoClicked();
				p.sendMessage("§6Schreibe in den Chat, wen du eine Anfrage stellen willst. Wenn du doch niemanden einladen willst, nutze /town.");
				ServerPlugin.invite.add(p);
				p.closeInventory();
			}
		}
	}
}