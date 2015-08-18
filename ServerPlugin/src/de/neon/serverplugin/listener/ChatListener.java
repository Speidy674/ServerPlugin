package de.neon.serverplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		if(e.getMessage().startsWith("@")) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(message.contains("@"+p.getName())) {
					int atIndex = message.indexOf('@');
					String msg = message.substring(0, p.getName().length()+2);
					String prefix = message.substring(atIndex, atIndex+p.getName().length());
					p.sendMessage("§c"+prefix+"§f "+msg);
					e.setCancelled(true);
				}
			}
		}
	}
}