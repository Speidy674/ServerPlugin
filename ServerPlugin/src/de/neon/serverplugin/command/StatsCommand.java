package de.neon.serverplugin.command;


import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.json.JSONChatClickEventType;
import de.neon.serverplugin.json.JSONChatColor;
import de.neon.serverplugin.json.JSONChatExtra;
import de.neon.serverplugin.json.JSONChatFormat;
import de.neon.serverplugin.json.JSONChatMessage;

public class StatsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("stats")) {
				if(args.length == 0) {
					if(DataUtil.geti(p, "skillpoints") <= 0) {
						int level = DataUtil.geti(p, "level");
						float xp = DataUtil.getf(p, "xp");
						float xpfnl = Util.experienceRequied(level+1);
						p.sendMessage("§c--------Stats--------");
						p.sendMessage("§bLevel: §6"+level);
						p.sendMessage("§bXP: §6"+Util.formatFloat(xp, 1)+"§6/§6"+Util.formatFloat(xpfnl, 1));
						p.sendMessage("§bStrength: §6"+DataUtil.geti(p, "strength"));
						p.sendMessage("§bDefensive: §6"+DataUtil.geti(p, "defensive"));
						p.sendMessage("§bVitality: §6"+DataUtil.geti(p, "vitality"));
						p.sendMessage("§bDexterity: §6"+DataUtil.geti(p, "dexterity"));
						p.sendMessage("§bDuel Record: §a"+DataUtil.geti(p, "wins")+"§f-§4"+DataUtil.geti(p, "loses")+" §b(Win/Loses)");
						p.sendMessage("§bSkillpoints: §6"+DataUtil.geti(p, "skillpoints"));
						p.sendMessage("§c--------------------");
					} else {
						int level = DataUtil.geti(p, "level");
						float xp = DataUtil.getf(p, "xp");
						float xpfnl = Util.experienceRequied(level+1);
						p.sendMessage("§c--------Stats--------");
						p.sendMessage("§bLevel: §6"+level);
						p.sendMessage("§bXP: §5"+Util.formatFloat(xp, 1)+"§6/§6"+Util.formatFloat(xpfnl, 1));
						JSONChatMessage messageS = new JSONChatMessage("§bStrength: §6"+DataUtil.geti(p, "strength"), JSONChatColor.DARK_RED, null);
						JSONChatExtra extraS = new JSONChatExtra(" [+]", JSONChatColor.GRAY, Arrays.asList(JSONChatFormat.BOLD));
						extraS.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/stats strength");
						messageS.addExtra(extraS);
						JSONChatMessage messageD = new JSONChatMessage("§bDefensive: §6"+DataUtil.geti(p, "defensive"), JSONChatColor.DARK_GRAY, null);
						JSONChatExtra extraD = new JSONChatExtra(" [+]", JSONChatColor.GRAY, Arrays.asList(JSONChatFormat.BOLD));
						extraD.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/stats defensive");
						messageD.addExtra(extraD);
						JSONChatMessage messageV = new JSONChatMessage("§bVitality: §6"+DataUtil.geti(p, "vitality"), JSONChatColor.LIGHT_PURPLE, null);
						JSONChatExtra extraV = new JSONChatExtra(" [+]", JSONChatColor.GRAY, Arrays.asList(JSONChatFormat.BOLD));
						extraV.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/stats vitality");
						messageV.addExtra(extraV);
						JSONChatMessage messageX = new JSONChatMessage("§bDexterity: §6"+DataUtil.geti(p, "dexterity"), JSONChatColor.DARK_BLUE, null);
						JSONChatExtra extraX = new JSONChatExtra(" [+]", JSONChatColor.GRAY, Arrays.asList(JSONChatFormat.BOLD));
						extraX.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/stats dexterity");
						messageX.addExtra(extraX);
						messageS.sendToPlayer(p);
						messageD.sendToPlayer(p);
						messageV.sendToPlayer(p);
						messageX.sendToPlayer(p);
						p.sendMessage("§bDuel Record: §a"+DataUtil.geti(p, "wins")+"§f-§4"+DataUtil.geti(p, "loses")+" §b(Win/Loses)");
						p.sendMessage("§bSkillpoints: §6"+DataUtil.geti(p, "skillpoints"));
						p.sendMessage("§c--------------------");
					}
					return true;
				} else {
					if(DataUtil.geti(p, "skillpoints") > 0) {
						if(args[0].equalsIgnoreCase("strength")) {
							DataUtil.increasei(p, "strength", 1);
							DataUtil.decreasei(p, "skillpoints", 1);
						}
						if(args[0].equalsIgnoreCase("defensive")) {
							DataUtil.increasei(p, "defensive", 1);
							DataUtil.decreasei(p, "skillpoints", 1);
						}
						if(args[0].equalsIgnoreCase("dexterity")) {
							DataUtil.increasei(p, "dexterity", 1);
							DataUtil.decreasei(p, "skillpoints", 1);
						}
						if(args[0].equalsIgnoreCase("vitality")) {
							DataUtil.increasei(p, "vitality", 1);
							DataUtil.decreasei(p, "skillpoints", 1);
						}
						p.performCommand("stats");
						return true;
					} else {
						p.sendMessage("§4Du hast keine Skillpoints.");
						return true;
					}
				}
			}
		}
		return false;
	}
}