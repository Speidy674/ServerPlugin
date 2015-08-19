package de.neon.serverplugin.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.hologram.Hologram;
import de.neon.serverplugin.json.JSONChatClickEventType;
import de.neon.serverplugin.json.JSONChatColor;
import de.neon.serverplugin.json.JSONChatExtra;
import de.neon.serverplugin.json.JSONChatFormat;
import de.neon.serverplugin.json.JSONChatHoverEventType;
import de.neon.serverplugin.json.JSONChatMessage;

public class DuelCommand implements CommandExecutor {

	public static ServerPlugin plugin;
	private int taskID = -1;
	
	public DuelCommand(ServerPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(!(s instanceof Player)) {
			Util.sendConsole(s);
			return true;
		}
		final Player p = (Player) s;
		if(cmd.getName().equalsIgnoreCase("duel")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
					sendHelp(p);
					return true;
				}
				if(args[0].equalsIgnoreCase("accept")) {
					if(!Util.inviteList.containsKey(p)) {
						p.sendMessage("§4Du hast keine Duellanfragen.");
						return true;
					}
					final Player target = Util.inviteList.get(p);
					if(!target.isOnline()) {
						p.sendMessage("§4"+target.getName()+" ist nicht mehr online.");
						p.performCommand("duel decline");
						return true;
					}
					Util.inviteList.remove(p);
				
					
					Util.displayTitleBar(p, "§65..", "", 2, 16 , 2);
					Util.displayTitleBar(target, "§65..", "", 2, 16 , 2);
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
					target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							Util.displayTitleBar(p, "§64..", "", 2, 16 , 2);
							Util.displayTitleBar(target, "§64..", "", 2, 16 , 2);
							p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
						}
					}, 20);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							Util.displayTitleBar(p, "§63..", "", 2, 16 , 2);
							Util.displayTitleBar(target, "§63..", "", 2, 16 , 2);
							p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
						}
					}, 40);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							Util.displayTitleBar(p, "§62..", "", 2, 16 , 2);
							Util.displayTitleBar(target, "§62..", "", 2, 16 , 2);
							p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
						}
					}, 60);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							Util.displayTitleBar(p, "§61..", "", 2, 16 , 2);
							Util.displayTitleBar(target, "§61..", "", 2, 16 , 2);
							p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.5f, 1.0f);
						}
					}, 80);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							Util.displayTitleBar(p, "§6FIGHT!", "", 2, 16 , 2);
							Util.displayTitleBar(target, "§6FIGHT!", "", 2, 16 , 2);
							Util.duelList.put(p, target);
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.5f, 1.0f);
						}
					}, 100);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.5f, 1.0f);
							target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.5f, 1.0f);
						}
					}, 103);
					taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
						public void run() {
							
							final Location hololoc1 = p.getLocation();
							hololoc1.setY(hololoc1.getY()+2);
							
							final Location hololoc2 = target.getLocation();
							hololoc2.setY(hololoc2.getY()+2);
							
							List<String> lines = new ArrayList<String>();
	                        lines.add("§4[§cDuel§4]");
	                        lines.add("§b"+target.getName() +" §4VS §b"+p.getName());
	                        
	                        Hologram hologram1 = new Hologram();
	                        hologram1.setLines(lines);
	                        hologram1.setLocation(hololoc1);
	                        hologram1.setHeight(1.0D);
	                        hologram1.spawntemp(10);
	                        
	                        Hologram hologram2 = new Hologram();
	                        hologram2.setLines(lines);
	                        hologram2.setLocation(hololoc2);
	                        hologram2.setHeight(1.0D);
	                        hologram2.spawntemp(10);
	                        
	                        if(!Util.duelList.containsKey(p)){
	                        	cancelTask();
	                        }
						}
						
					}, 100, 10);
					return true;
				}
				if(args[0].equalsIgnoreCase("decline")) {
					if(!Util.inviteList.containsKey(p)) {
						p.sendMessage("§4Du hast keine Duellanfragen.");
						return true;
					}
					Player target = Util.inviteList.get(p);
					p.sendMessage("§6Du hast die Duellanfrage von §b"+target.getName()+" §6abgelehnt.");
					target.sendMessage("§b"+p.getName()+" §6hat deine Duellanfrage abgelehnt.");
					Util.inviteList.remove(p);
					return true;
				}
			}
			if(args.length >= 2) {
				if(args[0].equalsIgnoreCase("invite")) {
					Player target = Bukkit.getPlayer(args[1]);
					if(p == target) {
						p.sendMessage("§4Du kannst nicht ein Duell gegen dich selbst machen.");
						return true;
					}
					if(target == null) {
						p.sendMessage("§4Der Spieler "+args[1]+" ist nicht online.");
						return true;
					}
					if(Util.inviteList.containsKey(p) || Util.inviteList.containsValue(p)) {
						p.sendMessage("§4Du kannst nicht in 2 Duellen gleichzeigt sein.");
						return true;
					}
					if(Util.inviteList.containsKey(target) || Util.inviteList.containsValue(target)) {
						p.sendMessage("§4"+target.getName()+" ist bereits in einem Duell.");
						return true;
					}
					if(Util.duelList.containsKey(p) || Util.duelList.containsValue(p)) {
						p.sendMessage("§4Du kannst nicht in 2 Duellen gleichzeigt sein.");
						return true;
					}
					if(Util.duelList.containsKey(target) || Util.duelList.containsValue(target)) {
						p.sendMessage("§4"+target.getName()+" ist bereits in einem Duell.");
						return true;
					}
					Util.inviteList.put(target, p);
					p.sendMessage("§6Du hast §b"+target.getName()+" §6zu einem Duell herrausgefordert.");
					target.sendMessage("§b"+p.getName()+" §6hat dich zu einem Duell herrausgefordert.");
					JSONChatMessage messageA = new JSONChatMessage("Um die Anfrage anzunehmen, klicke ", JSONChatColor.GOLD, null);
					JSONChatExtra extraA = new JSONChatExtra("[AKZEPTIEREN]", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
					extraA.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Duelliere dich gegen "+p.getName()+".");
					extraA.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/duel accept");
					messageA.addExtra(extraA);
					JSONChatMessage messageD = new JSONChatMessage("Um die Anfrage ablehnen, klicke ", JSONChatColor.GOLD, null);
					JSONChatExtra extraD = new JSONChatExtra("[ABLEHNEN]", JSONChatColor.RED, Arrays.asList(JSONChatFormat.BOLD));
					extraD.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Duelliere dich nicht gegen "+p.getName()+".");
					extraD.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/duel decline");
					messageD.addExtra(extraD);
					messageA.sendToPlayer(target);
					messageD.sendToPlayer(target);
					return true;
				}
			}
			sendHelp(p);
			return true;
		}
		return false;
	}
	
	
	public void sendHelp(Player p) {
		p.sendMessage("§6/duel §ahelp §b- §fGucke dir die Hilfeseite an.");
		p.sendMessage("§6/duel §ainvite §b- §fStelle jemanden eine Duellanfrage.");
	}
	
	private void cancelTask() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
}