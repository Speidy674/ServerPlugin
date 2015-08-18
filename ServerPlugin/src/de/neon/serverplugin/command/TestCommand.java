package de.neon.serverplugin.command;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.hologram.Hologram;

public class TestCommand implements CommandExecutor {


	public static ServerPlugin plugin;
	
	public TestCommand(ServerPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("test")) {
				
				List<String> lines = new ArrayList<String>();
				int level = DataUtil.geti(p, "level");
				float xp = DataUtil.getf(p, "xp");
				float xpfnl = Util.experienceRequied(level+1);
				lines.add("§c--------Statistics--------");
				lines.add("§bLevel: §6"+level);
				lines.add("§bXP: §6"+Util.formatFloat(xp, 1)+"§f/§6"+Util.formatFloat(xpfnl, 1));
				lines.add("§bStrength: §6"+DataUtil.geti(p, "strength"));
				lines.add("§bDefensive: §6"+DataUtil.geti(p, "defensive"));
				lines.add("§bVitality: §6"+DataUtil.geti(p, "vitality"));
				lines.add("§bDexterity: §6"+DataUtil.geti(p, "dexterity"));
				lines.add("§bDuel Record: §a"+DataUtil.geti(p, "wins")+"§f - §4"+DataUtil.geti(p, "loses")+" §b(Win/Loses)");
				lines.add("§bSkillpoints: §6"+DataUtil.geti(p, "skillpoints"));
				lines.add("§c--------------------");
               
                Hologram hologram = new Hologram();
                hologram.setLines(lines);
                hologram.setLocation(p.getLocation());
                hologram.setHeight(0.5D);
               
                hologram.spawntemp(60);
			}
		}
		return false;
	}
}