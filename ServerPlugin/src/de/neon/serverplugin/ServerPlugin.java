package de.neon.serverplugin;

import java.io.File;

import me.clip.deluxechat.placeholders.DeluxePlaceholderHook;
import me.clip.deluxechat.placeholders.PlaceholderHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.neon.serverplugin.actionbar.Actionbar;
import de.neon.serverplugin.command.DuelCommand;
import de.neon.serverplugin.command.StatsCommand;
import de.neon.serverplugin.listener.DuelListener;
import de.neon.serverplugin.listener.JoinLeaveListener;
import de.neon.serverplugin.listener.XPListener;

public class ServerPlugin extends JavaPlugin {
	
	public static File dataFolder = null;
	public static File players = null;
	public static Actionbar bar = new Actionbar();
	public static int pluginTickRate = 10;
	
	public void onLoad() {
		dataFolder = this.getDataFolder();
		players = new File(dataFolder+"/players/");
		if(!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		if(!players.exists()) {
			players.mkdirs();
		}
		DataUtil.init();
	}
	
	public void onEnable() {
		System.out.println("[ServerPlugin] Enabled");
		
		DeluxChatHook();
		
		Bukkit.getPluginCommand("duel").setExecutor(new DuelCommand(this));
		Bukkit.getPluginCommand("stats").setExecutor(new StatsCommand());
		Bukkit.getPluginManager().registerEvents(new DuelListener(), this);
		Bukkit.getPluginManager().registerEvents(new XPListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(!DataUtil.hasAccount(p)) {
						DataUtil.createNewData(p);
					} else {
						if(!DataUtil.hasNewestAccount(p)) {
							DataUtil.correctAccount(p);
						}
					}
					
					Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
					Objective obj = board.getObjective("Stats");
					if(obj == null) {
						obj = board.registerNewObjective("Stats", "dummy");
					}
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					obj.setDisplayName("§6Stats");
					obj.getScore("§bStärke").setScore(DataUtil.geti(p, "strength"));
					obj.getScore("§bVerteidigung").setScore(DataUtil.geti(p, "defensive"));
					obj.getScore("§bVitalität").setScore(DataUtil.geti(p, "vitality"));
					obj.getScore("§bGeschicklichkeit").setScore(DataUtil.geti(p, "dexterity"));
					p.setScoreboard(board);
					
					float per1 = Util.getXPPercentage(p);
					String per = Util.formatFloat(per1, 1);
					String msg1 = "§2Level "+DataUtil.geti(p, "level")+" ";
					String msg = "";
					if(per1 >= 0) {
						msg = msg1+"§4[§c    §6"+per+"%§c     §4]";
					}
					if(per1 >= 10) {
						msg = msg1+"§4[§c|    §6"+per+"%§c     §4]";
					}
					if(per1 >= 20) {
						msg = msg1+"§4[§c||   §6"+per+"%§c     §4]";
					}
					if(per1 >= 30) {
						msg = msg1+"§4[§c|||  §6"+per+"%§c     §4]";
					}
					if(per1 >= 40) {
						msg = msg1+"§4[§c|||| §6"+per+"%§c     §4]";
					}
					if(per1 >= 50) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c     §4]";
					}
					if(per1 >= 60) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c|    §4]";
					}
					if(per1 >= 70) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c||   §4]";
					}
					if(per1 >= 80) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c|||  §4]";
					}
					if(per1 >= 90) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c|||| §4]";
					}
					if(per1 >= 99) {
						msg = msg1+"§4[§c|||||§6"+per+"%§c|||||§4]";
					}
					bar.sendMessage(p, msg);
					
					
				}
			}
		}, 0, pluginTickRate);
	}
	
	public void DeluxChatHook(){
		
		if (Bukkit.getPluginManager().isPluginEnabled("DeluxeChat")) {

			boolean hooked = PlaceholderHandler.registerPlaceholderHook(this,
					new DeluxePlaceholderHook() {

						/*
						 * this method will be called any time a placeholder
						 * %<yourplugin>_<identifier>% is found
						 */
						@Override
						public String onPlaceholderRequest(Player p, String identifier) {

							if (identifier.equalsIgnoreCase("lvl")) {
								return String.valueOf(DataUtil.geti(p, "level"));
							}
							if (identifier.equalsIgnoreCase("str")) {
								return String.valueOf(DataUtil.geti(p, "strength"));
							}
							if (identifier.equalsIgnoreCase("def")) {
								return String.valueOf(DataUtil.geti(p, "defensive"));
							}
							if (identifier.equalsIgnoreCase("vit")) {
								return String.valueOf(DataUtil.geti(p, "vitality"));
							}
							if (identifier.equalsIgnoreCase("dex")) {
								return String.valueOf(DataUtil.geti(p, "dexterity"));
							}
							if (identifier.equalsIgnoreCase("xp")) {
								return Util.formatFloat(DataUtil.getf(p, "xp"), 1);
							}
							if (identifier.equalsIgnoreCase("xpnl")) {
								return Util.formatFloat(Util.experienceRequied(DataUtil.geti(p, "level")+1), 1);
							}
							//was not a correct identifier
							return null;
						}
					});
			
			if (hooked) {
				getLogger().info("DeluxeChat placeholder hook was successfully registered!");
			}
		}
		
	}
	
	public void onDisable() {

		PlaceholderHandler.unregisterPlaceholderHook(this);
	}
}