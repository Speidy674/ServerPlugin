package de.neon.serverplugin.listener;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.json.JSONChatClickEventType;
import de.neon.serverplugin.json.JSONChatColor;
import de.neon.serverplugin.json.JSONChatExtra;
import de.neon.serverplugin.json.JSONChatFormat;
import de.neon.serverplugin.json.JSONChatHoverEventType;
import de.neon.serverplugin.json.JSONChatMessage;
import de.neon.serverplugin.town.Town;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = (Player) e.getPlayer();
		if(ServerPlugin.create.contains(p)) {
			e.setCancelled(true);
			if(TownUtil.exists(e.getMessage())) {
				p.sendMessage("§4Der Stadtname "+e.getMessage()+" ist schon vergeben. Bitte versuche es erneut.");
			} else {
				double minX = p.getLocation().getX() - (Town.SIZE_X / 2);
				double minZ = p.getLocation().getZ() - (Town.SIZE_Z / 2);
				double maxX = p.getLocation().getX() + (Town.SIZE_X / 2);
				double maxZ = p.getLocation().getZ() + (Town.SIZE_Z / 2);
				TownUtil.createTown(p.getName(), e.getMessage(), minX, minZ, maxX, maxZ);
				p.sendMessage("§aDie Stadt §b"+e.getMessage()+" §awurde erfolgreich erstellt!");
				DataUtil.set(p, "ownsTown", true);
				DataUtil.set(p, "town", e.getMessage());
				ServerPlugin.create.remove(p);
			}
		}
		if(ServerPlugin.invite.contains(p)) {
			e.setCancelled(true);
			Player target = Bukkit.getPlayer(e.getMessage());
			Town town = TownUtil.getTown(p);
			if(target == null) {
				p.sendMessage("§4Der Spieler "+e.getMessage()+" ist nicht online. Bitte versuche es erneut.");
			} else {
				if(Util.townInviteList.containsKey(target)) {
					p.sendMessage("§4Der Spieler "+e.getMessage()+" hat schon eine laufende Anfage. Bitte versuche es erneut.");
					return;
				}
				if(TownUtil.isInTown(target)) {
					p.sendMessage("§4Der Spieler "+e.getMessage()+" ist bereits in einer Stadt. Bitte versuche es erneut.");
					return;
				}
				target.sendMessage("§b"+p.getName()+" §4möchte, dass du ein Bewohner seiner Stadt §b"+town.getName()+" §awirst.");
				JSONChatMessage messageA = new JSONChatMessage("Um die Anfrage anzunehmen, klicke ", JSONChatColor.GOLD, null);
				JSONChatExtra extraA = new JSONChatExtra("[AKZEPTIEREN]", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				extraA.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Werde ein Bewohner der Stadt "+town.getName()+".");
				extraA.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/town accept");
				messageA.addExtra(extraA);
				JSONChatMessage messageD = new JSONChatMessage("Um die Anfrage ablehnen, klicke ", JSONChatColor.GOLD, null);
				JSONChatExtra extraD = new JSONChatExtra("[ABLEHNEN]", JSONChatColor.RED, Arrays.asList(JSONChatFormat.BOLD));
				extraD.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Werde kein Bewohner der Stadt "+town.getName()+".");
				extraD.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/town decline");
				messageD.addExtra(extraD);
				messageA.sendToPlayer(target);
				messageD.sendToPlayer(target);
				Util.townInviteList.put(target, town);
				ServerPlugin.invite.remove(p);
			}
		}
	}
}