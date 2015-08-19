package de.neon.serverplugin.command;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.neon.serverplugin.TownUtil;

public class TownCommand implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("town")) {
				if(args.length == 0) {
					System.out.println(TownUtil.isInTown(p));
					if(!TownUtil.isInTown(p)) {
						sendCreateNewTownGUI(p);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void sendCreateNewTownGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 6, "Towns");
		ItemStack stack = new ItemStack(Material.COMPASS);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§2Erstelle eine neue Stadt");
		stack.setItemMeta(meta);
		inv.addItem(stack);
		p.openInventory(inv);
	}
}