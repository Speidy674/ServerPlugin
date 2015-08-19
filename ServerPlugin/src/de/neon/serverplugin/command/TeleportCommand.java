package de.neon.serverplugin.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TeleportCommand implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("teleport")) {
				if(args.length == 0) {
					int amount = (Bukkit.getOnlinePlayers().size()-1)/9;
					int size = 1;
					List<Player> players = new ArrayList<Player>();
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(!player.getName().equals(p.getName())) {
							players.add(player);
						}
					}
					size += amount;
					Inventory inv = Bukkit.createInventory(p, size, "§6Teleport-Menü");
					for(Player player : players) {
						ItemStack item = new ItemStack(Material.SKULL_ITEM);
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setDisplayName("§6"+player.getName());
						item.setItemMeta(meta);
						inv.addItem(item);
					}
					p.openInventory(inv);
					return true;
				}
				if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						p.sendMessage("§4Der Spieler "+args[0]+" ist nicht online.");
						return true;
					}
					p.teleport(target);
					p.sendMessage("§aDu hast dich zu §b"+target.getName()+" §ateleportiert.");
					target.sendMessage("§b"+p.getName()+" §6hat sich zu dir teleportiert.");
					return true;
				}
			}
		}
		return false;
	}
}