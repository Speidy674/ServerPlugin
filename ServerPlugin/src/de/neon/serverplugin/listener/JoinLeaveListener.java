package de.neon.serverplugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		String msg = "§7[§a+§7] §6"+e.getPlayer().getName();
		e.setJoinMessage(msg);
		if(!e.getPlayer().hasPlayedBefore()) {
			e.getPlayer().performCommand("skill");
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		String msg = "§7[§c-§7] §6"+e.getPlayer().getName();
		e.setQuitMessage(msg);
		
	}
}