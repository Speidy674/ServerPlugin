package de.neon.serverplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neon.serverplugin.ConfigUtil;

public class BroadcastCommand implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("broadcast")) {
				if(p.hasPermission("serverolugin.broadcast")){
					String msg = ConfigUtil.gets("broadcast")+"";
					for (String arg : args){
						msg = msg+" "+arg;
					}
					msg = ChatColor.translateAlternateColorCodes('&', msg);
					Bukkit.broadcastMessage(msg);
					return true;
				}else{
					p.sendMessage("§4Keine Rechte");
					return true;
				}
			
			}
		return false;
	}
}