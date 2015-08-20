package de.neon.serverplugin.listener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
				for(double x = minX; x < maxX; x++) {
					for(double z = minZ; z < maxZ; z++) {
						if(TownUtil.isInTown(x, z) != null) {
							p.sendMessage("§4Hier ist schon eine Stadt erstellt worden. Bitte begebe dich zu einer anderen Position.");
							return;
						}
					}
				}
				TownUtil.createTown(p.getName(), e.getMessage(), minX, minZ, maxX, maxZ);
				p.sendMessage("§aDie Stadt §b"+e.getMessage()+" §awurde erfolgreich erstellt! Du kannst jetzt wieder normal schreiben.");
				DataUtil.set(p, "ownsTown", true);
				DataUtil.set(p, "town", e.getMessage());
				ServerPlugin.create.remove(p);
			}
		}
		if(ServerPlugin.welcome.contains(p)) {
			e.setCancelled(true);
			Town town = TownUtil.getTown(p);
			town.setWelcome(e.getMessage());
			p.sendMessage("§aDie Willkommens Nachricht ist jetzt §b"+e.getMessage()+"§a!");
			ServerPlugin.welcome.remove(p);
			File file = new File(ServerPlugin.town + "/"+town.getName()+".yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("welcome", e.getMessage());
			try {
				config.save(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(ServerPlugin.adoption.contains(p)) {
			e.setCancelled(true);
			Town town = TownUtil.getTown(p);
			town.setAdoption(e.getMessage());
			p.sendMessage("§aDie Verabschiedungs Nachricht ist jetzt §b"+e.getMessage()+"§a!");
			ServerPlugin.adoption.remove(p);
			File file = new File(ServerPlugin.town + "/"+town.getName()+".yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("adoption", e.getMessage());
			try {
				config.save(file);
			} catch (IOException e1) {
				e1.printStackTrace();
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
				target.sendMessage("§b"+p.getName()+" §6möchte, dass du ein Bewohner seiner Stadt §b"+town.getName()+" §6wirst.");
				p.sendMessage("§aDu hast §b"+target.getName()+" §aeine Anfrage geschickt. Du kannst jetzt wieder normal schreiben.");
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