package de.neon.serverplugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.neon.serverplugin.actionbar.Actionbar;
import de.neon.serverplugin.command.DuelCommand;
import de.neon.serverplugin.command.StatsCommand;
import de.neon.serverplugin.listener.XPListener;
import de.neon.serverplugin.listener.PlayerListener;

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
		Bukkit.getPluginCommand("duel").setExecutor(new DuelCommand(this));
		Bukkit.getPluginCommand("stats").setExecutor(new StatsCommand());
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new XPListener(), this);
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
}