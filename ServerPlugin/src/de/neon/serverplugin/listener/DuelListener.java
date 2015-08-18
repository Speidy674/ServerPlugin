package de.neon.serverplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.Util;

public class DuelListener implements Listener {

	@EventHandler
	public void onPlayerDamageByPlayer(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p1 = (Player) e.getDamager();
			Player p2 = (Player) e.getEntity();
			if(!Util.isInDuelWithEachOther(p1, p2)) {
				e.setCancelled(true);
			} else {
				if(p2.getHealth() - e.getDamage() < 1) {
					e.setCancelled(true);
					Util.duelList.remove(p1);
			    	Util.duelList.remove(p2);
			    	Util.displayTitleBar(p1, "§aGewonnen!", "", 20, 20, 20);
			    	Util.displayTitleBar(p2, "§4Verloren!", "", 20, 20, 20);
			    	DataUtil.increasei(p1, "wins", 1);
			    	DataUtil.increasei(p2, "loses", 1);
			    	p2.setHealth(1);
				}
			}
		}
	}
}