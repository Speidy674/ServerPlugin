package de.neon.serverplugin.listener;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;
import de.neon.serverplugin.town.Town;

public class MoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Town town = TownUtil.isInTown(e.getTo().getX(), e.getTo().getZ());
		Player p = e.getPlayer();
		if(town != null && !ServerPlugin.inTown.contains(p)) {
			ServerPlugin.inTown.add(p);
			String msg = town.getWelcome();
			Calendar cal = Calendar.getInstance();
			msg = msg.replaceAll("%player%", p.getName());
			msg = msg.replaceAll("%town%", town.getName());
			msg = msg.replaceAll("%time%", cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			p.sendMessage(msg);
		}
		Town town_ = TownUtil.isInTown(e.getFrom().getX(), e.getFrom().getZ());
		if(town_ != null && town == null && ServerPlugin.inTown.contains(p)) {
			ServerPlugin.inTown.remove(p);
			String msg = town_.getAdoption();
			Calendar cal = Calendar.getInstance();
			msg = msg.replaceAll("%player%", p.getName());
			msg = msg.replaceAll("%town%", town_.getName());
			msg = msg.replaceAll("%time%", cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			p.sendMessage(msg);
		}
	}
}